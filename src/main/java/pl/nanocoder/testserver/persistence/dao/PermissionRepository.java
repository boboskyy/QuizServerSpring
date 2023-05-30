package pl.nanocoder.testserver.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.nanocoder.testserver.persistence.model.Permission;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission,Long> {
    List<Permission> findByGroupName(String groupName);
}
