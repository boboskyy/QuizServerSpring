package pl.nanocoder.testserver.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.nanocoder.testserver.persistence.model.QGModule;

public interface QGModuleRepository extends JpaRepository<QGModule, Long> {
    QGModule findById(long id);
}
