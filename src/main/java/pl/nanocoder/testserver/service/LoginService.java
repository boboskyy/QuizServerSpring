package pl.nanocoder.testserver.service;

import org.fluttercode.datafactory.impl.DataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.nanocoder.testserver.persistence.dao.LoginRepository;
import pl.nanocoder.testserver.persistence.model.Login;
import pl.nanocoder.testserver.persistence.pojos.PersonType;
import pl.nanocoder.testserver.web.error.UserNotFoundException;


@Service
@Transactional
public class LoginService implements ILoginService {

    @Autowired
    private LoginRepository loginRepository;

    @Bean
    @Override
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public Login findByLogin(String login) {
        return loginRepository.findByLogin(login);
    }

    @Override
    public Login findById(long id) {
        return loginRepository.findById(id);
    }

    @Override
    public void save(Login login, boolean encodePassword) {
        if(encodePassword)
            login.setPassword(passwordEncoder().encode(login.getPassword()));
        loginRepository.save(login);
    }

    @Override
    public String generateNewPassword(Login login) {
        DataFactory dataFactory = new DataFactory();
        String password = dataFactory.getRandomChars(10);
        login.setPassword(password);
        save(login,true);
        return password;
    }

    @Override
    public String resetUser(long id, PersonType type) throws UserNotFoundException {
        Login login = loginRepository.findById(id);
        if(!isValidUser(login,type))
            throw new UserNotFoundException();

        String password = this.generateNewPassword(login);
        login.setResetPassword(true);

        return password;
    }

    public boolean isValidUser(Login login, PersonType personType)
    {
        return (login != null && login.getPerson() != null && login.getPerson().getType() == personType && !login.getPerson().isDeleted());
    }
}
