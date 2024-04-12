package br.com.andresgois.FeignApplication.infrastructure.persistence;

import br.com.andresgois.FeignApplication.domain.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
}
