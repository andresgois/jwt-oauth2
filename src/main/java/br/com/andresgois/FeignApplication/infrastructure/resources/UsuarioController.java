package br.com.andresgois.FeignApplication.infrastructure.resources;

import br.com.andresgois.FeignApplication.domain.entities.Role;
import br.com.andresgois.FeignApplication.domain.entities.Usuario;
import br.com.andresgois.FeignApplication.infrastructure.exceptions.UserNotFoundException;
import br.com.andresgois.FeignApplication.infrastructure.persistence.RoleRepository;
import br.com.andresgois.FeignApplication.infrastructure.persistence.UsuarioRepository;
import br.com.andresgois.FeignApplication.infrastructure.representation.UsuarioRequest;
import br.com.andresgois.FeignApplication.infrastructure.representation.UsuarioResponse;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioController(UsuarioRepository repository, RoleRepository roleRepository,
                             PasswordEncoder passwordEncoder) {
        this.usuarioRepository = repository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // http://localhost:8080/usuario/teste/ola
    // http://localhost:8080/h2-console
    @GetMapping(value = "/teste/{teste}")
    public ResponseEntity<String> teste(@PathVariable(value = "teste") String teste){
        return ResponseEntity.status(HttpStatus.OK).body("Funcionou");
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> createUser(@RequestBody UsuarioRequest request){
        /*Usuario user = repository.save(request.toEntity());
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.status(HttpStatus.CREATED).build();*/

        Optional<Role> basicRole = roleRepository.findByName(Role.Values.BASIC.name().toLowerCase());
        Optional<Usuario> userFromDb = usuarioRepository.findByEmail(request.email());
        if(userFromDb.isPresent()){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        Usuario user = new Usuario();
        user.setEmail(request.email());
        user.setNome(request.nome());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRoles(Set.of(basicRole.get()));

        usuarioRepository.save(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UsuarioResponse> userById(@PathVariable(value = "id") UUID id){
        Optional<Usuario> user = usuarioRepository.findById(id);
        if(!user.isPresent()){
            throw new UserNotFoundException();
        }
        return ResponseEntity.ok(new UsuarioResponse(
                user.get().getId(),
                user.get().getNome(),
                user.get().getEmail()
        ));
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<UsuarioResponse>> listAllUsersRequest(){
        List<Usuario> user = usuarioRepository.findAll();
        List<UsuarioResponse> users = user.stream()
                .map(u -> new UsuarioResponse(u.getId(), u.getNome(), u.getEmail()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public ResponseEntity<List<Usuario>> listAllUsers(){
        List<Usuario> users = usuarioRepository.findAll();
        return ResponseEntity.ok(users);
    }
}
