package br.com.andresgois.FeignApplication.infrastructure.resources;

import br.com.andresgois.FeignApplication.domain.entities.Role;
import br.com.andresgois.FeignApplication.domain.entities.User;
import br.com.andresgois.FeignApplication.infrastructure.persistence.UserRepository;
import br.com.andresgois.FeignApplication.infrastructure.representation.LoginRequest;
import br.com.andresgois.FeignApplication.infrastructure.representation.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class TokenController {

    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public TokenController(JwtEncoder jwtEncoder, UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.jwtEncoder = jwtEncoder;
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
        Optional<User> user = userRepository.findByEmail(request.email());
        if(user.isEmpty() || !user.get().isLoginCorrect(request, encoder) ){
            throw new BadCredentialsException("User or password is invalid!");
        }
        Instant now = Instant.now();
        long expiresIn = 300L;

        String scopes = user.get().getRoles()
                .stream().map(Role::getName).collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("sistema")
                .subject(user.get().getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", scopes)
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.ok(new LoginResponse(jwtValue, expiresIn));
    }
}
