package br.com.andresgois.FeignApplication.infrastructure.representation;

import br.com.andresgois.FeignApplication.domain.entities.Usuario;

public record UsuarioRequest (
        String nome,
        String email,
        String password
        ){

        public Usuario toEntity(){
                return new Usuario(
                        nome,
                        email,
                        password
                );
        }
}
