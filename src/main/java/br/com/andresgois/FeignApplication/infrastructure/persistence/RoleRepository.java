package br.com.andresgois.FeignApplication.infrastructure.persistence;

import br.com.andresgois.FeignApplication.domain.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String admin);
}
