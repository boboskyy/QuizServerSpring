package pl.nanocoder.testserver.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.nanocoder.testserver.persistence.model.Role;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(String name);
}
