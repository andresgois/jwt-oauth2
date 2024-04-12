package br.com.andresgois.FeignApplication.infrastructure.representation;

import br.com.andresgois.FeignApplication.domain.entities.Usuario;

import java.util.Random;
import java.util.UUID;

public record UsuarioRequest (
        String nome,
        String email
        ){

        public Usuario toEntity(){
                return new Usuario(
                        UUID.randomUUID(),
                        nome,
                        email
                );
        }
}
