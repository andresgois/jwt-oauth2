package br.com.andresgois.FeignApplication.infrastructure.representation;

public record LoginRequest(
        String email,
        String password
) {
}
