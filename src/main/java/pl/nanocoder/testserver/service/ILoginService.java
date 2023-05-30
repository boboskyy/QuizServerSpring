package pl.nanocoder.testserver.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import pl.nanocoder.testserver.persistence.model.Login;
import pl.nanocoder.testserver.persistence.pojos.PersonType;
import pl.nanocoder.testserver.web.error.UserNotFoundException;


public interface ILoginService {

    PasswordEncoder passwordEncoder();
    Login findByLogin(String login);
    Login findById(long id);
    void save(Login login, boolean encodePassword);
    String generateNewPassword(Login login);
    String resetUser(long id, PersonType type) throws UserNotFoundException;

}
