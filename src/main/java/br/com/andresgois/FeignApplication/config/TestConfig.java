package br.com.andresgois.FeignApplication.config;

import br.com.andresgois.FeignApplication.domain.entities.Role;
import br.com.andresgois.FeignApplication.domain.entities.Usuario;
import br.com.andresgois.FeignApplication.infrastructure.persistence.RoleRepository;
import br.com.andresgois.FeignApplication.infrastructure.persistence.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.Set;

@Configuration
public class TestConfig implements CommandLineRunner {

    @Value("${spring.profiles.active}")
    private String enviroment;

    private final RoleRepository roleRepository;
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder encoder;

    public TestConfig(RoleRepository roleRepository, UsuarioRepository usuarioRepository, BCryptPasswordEncoder encoder) {
        this.roleRepository = roleRepository;
        this.usuarioRepository = usuarioRepository;
        this.encoder = encoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        System.out.println("PROFILE: "+ enviroment);

        Optional<Role> roleAdmin = roleRepository.findByName("admin");
        Optional<Usuario> userAdmin = usuarioRepository.findByNome("admin");

        userAdmin.ifPresentOrElse(
                user -> {
                    System.out.println("Admin já existe");
                },
                () -> {
                    Usuario user = new Usuario();
                    user.setNome("Admin");
                    user.setPassword(encoder.encode("123"));
                    user.setRoles(Set.of(roleAdmin.get()));
                    user.setEmail("admin@email.com");
                    usuarioRepository.save(user);
                }
        );
    }

}
