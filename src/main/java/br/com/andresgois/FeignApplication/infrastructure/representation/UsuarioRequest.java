package br.com.andresgois.FeignApplication.infrastructure.representation;

import br.com.andresgois.FeignApplication.domain.entities.User;

public record UsuarioRequest (
        String name,
        String email,
        String password
        ){

        public User toEntity(){
                return new User(
                        name,
                        email,
                        password
                );
        }
}
