package pl.nanocoder.testserver.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.nanocoder.testserver.persistence.model.Login;


public interface LoginRepository extends JpaRepository<Login,Long> {
    Login findByLogin(String login);
    Login findById(long id);
}
