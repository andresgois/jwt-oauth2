package br.com.andresgois.FeignApplication.infrastructure.representation;

public record UsuarioResponse (
        java.util.UUID UUID,
        String name,
        String email
){
}
