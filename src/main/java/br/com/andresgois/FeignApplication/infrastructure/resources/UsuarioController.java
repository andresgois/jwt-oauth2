package br.com.andresgois.FeignApplication.infrastructure.resources;

import br.com.andresgois.FeignApplication.domain.entities.Role;
import br.com.andresgois.FeignApplication.domain.entities.User;
import br.com.andresgois.FeignApplication.infrastructure.exceptions.UserNotFoundException;
import br.com.andresgois.FeignApplication.infrastructure.persistence.RoleRepository;
import br.com.andresgois.FeignApplication.infrastructure.persistence.UserRepository;
import br.com.andresgois.FeignApplication.infrastructure.representation.UsuarioRequest;
import br.com.andresgois.FeignApplication.infrastructure.representation.UsuarioResponse;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UsuarioController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioController(UserRepository repository, RoleRepository roleRepository,
                             PasswordEncoder passwordEncoder) {
        this.userRepository = repository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(value = "/teste/{teste}")
    public ResponseEntity<String> teste(@PathVariable(value = "teste") String teste){
        return ResponseEntity.status(HttpStatus.OK).body("Funcionou");
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> createUser(@RequestBody UsuarioRequest request){
        Optional<Role> basicRole = roleRepository.findByName(Role.Values.BASIC.name());
        Optional<User> userFromDb = userRepository.findByEmail(request.email());
        if(userFromDb.isPresent()){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        User user = new User();
        user.setEmail(request.email());
        user.setName(request.name());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRoles(Set.of(basicRole.get()));

        userRepository.save(user);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UsuarioResponse> userById(@PathVariable(value = "id") UUID id){
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()){
            throw new UserNotFoundException();
        }
        return ResponseEntity.ok(new UsuarioResponse(
                user.get().getId(),
                user.get().getName(),
                user.get().getEmail()
        ));
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<UsuarioResponse>> listAllUsersRequest(){
        List<User> user = userRepository.findAll();
        List<UsuarioResponse> users = user.stream()
                .map(u -> new UsuarioResponse(u.getId(), u.getName(), u.getEmail()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<User>> listAllUsers(){
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }
    
    @PostMapping("/dados-user")
    public ResponseEntity<?> dadosUser(JwtAuthenticationToken token){
        Optional<User> user = userRepository.findById(UUID.fromString(token.getName()));
        if(!user.isPresent()){
            throw new UserNotFoundException();
        }
        return ResponseEntity.ok().build();
    }
}
