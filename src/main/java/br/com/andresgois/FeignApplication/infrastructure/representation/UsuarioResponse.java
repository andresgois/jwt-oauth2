package br.com.andresgois.FeignApplication.infrastructure.representation;

public record UsuarioResponse (
        java.util.UUID UUID,
        String nome,
        String email
){
}
