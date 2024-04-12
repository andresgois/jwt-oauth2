package br.com.andresgois.FeignApplication.infrastructure.representation;

import java.util.UUID;

public record UsuarioResponse (
        String UUID,
        String nome,
        String email
){
}
