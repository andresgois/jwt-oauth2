package br.com.andresgois.FeignApplication.infrastructure.resources;

import br.com.andresgois.FeignApplication.domain.entities.Usuario;
import br.com.andresgois.FeignApplication.infrastructure.exceptions.UserNotFoundException;
import br.com.andresgois.FeignApplication.infrastructure.persistence.UsuarioRepository;
import br.com.andresgois.FeignApplication.infrastructure.representation.UsuarioRequest;
import br.com.andresgois.FeignApplication.infrastructure.representation.UsuarioResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioRepository repository;

    public UsuarioController(UsuarioRepository repository) {
        this.repository = repository;
    }

    // http://localhost:8080/usuario/teste/ola
    // http://localhost:8080/h2-console
    @GetMapping(value = "/teste/{teste}")
    public ResponseEntity<String> teste(@PathVariable(value = "teste") String teste){
        return ResponseEntity.status(HttpStatus.OK).body("Funcionou");
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UsuarioRequest request){
        Usuario user = repository.save(request.toEntity());
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UsuarioResponse> userById(@PathVariable(value = "id") UUID id){
        Optional<Usuario> user = repository.findById(id);
        if(!user.isPresent()){
            throw new UserNotFoundException();
        }
        return ResponseEntity.ok(new UsuarioResponse(
                user.get().getId().toString(),
                user.get().getNome(),
                user.get().getEmail()
        ));
    }
}
