package br.com.andresgois.FeignApplication.config;

import br.com.andresgois.FeignApplication.domain.entities.Role;
import br.com.andresgois.FeignApplication.domain.entities.User;
import br.com.andresgois.FeignApplication.infrastructure.persistence.RoleRepository;
import br.com.andresgois.FeignApplication.infrastructure.persistence.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.Set;

@Configuration
public class InitialConfig implements CommandLineRunner {

    @Value("${spring.profiles.active}")
    private String enviroment;

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public InitialConfig(RoleRepository roleRepository, UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        System.out.println("PROFILE: "+ enviroment);

        Optional<Role> roleAdmin = roleRepository.findByName("ADMIN");

        roleAdmin.ifPresentOrElse(
                (r)-> System.out.println("Têm: "+r.getName()),
                () -> System.out.println("Não Têm")
        );

        Optional<User> userAdmin = userRepository.findByName("ADMIN");

        userAdmin.ifPresentOrElse(
                user -> {
                    System.out.println("Admin já existe");
                },
                () -> {
                    User user = new User();
                    user.setName("Admin");
                    user.setPassword(encoder.encode("123"));
                    user.setRoles(Set.of(roleAdmin.get()));
                    user.setEmail("admin@email.com");
                    userRepository.save(user);
                }
        );
    }

}
