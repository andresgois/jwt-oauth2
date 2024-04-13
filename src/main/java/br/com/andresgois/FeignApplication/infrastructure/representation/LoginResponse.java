package br.com.andresgois.FeignApplication.infrastructure.representation;

public record LoginResponse(
        String accessToken,
        Long expireIn
) {
}
