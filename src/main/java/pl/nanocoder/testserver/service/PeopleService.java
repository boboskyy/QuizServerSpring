package pl.nanocoder.testserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.nanocoder.testserver.persistence.dao.PeopleRepository;
import pl.nanocoder.testserver.persistence.dao.RoleRepository;
import pl.nanocoder.testserver.persistence.model.Login;
import pl.nanocoder.testserver.persistence.model.Person;
import pl.nanocoder.testserver.persistence.model.Role;
import pl.nanocoder.testserver.persistence.pojos.PersonType;
import pl.nanocoder.testserver.web.dto.CreatePersonDto;
import pl.nanocoder.testserver.web.dto.UpdateUserDetailsDto;
import pl.nanocoder.testserver.web.error.UserAlreadyExistException;
import pl.nanocoder.testserver.web.error.UserNotFoundException;

import java.util.*;

@Service
@Transactional
public class PeopleService implements IPeopleService {

    @Autowired
    private PeopleRepository peopleRepository;

    @Autowired
    private LoginService loginService;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Person findById(long id) throws UserNotFoundException {
        Person person = peopleRepository.findById(id);
        if(person == null)
            throw new UserNotFoundException();
        return person;
    }

    @Override
    public void deletePerson(long id, PersonType type) throws UserNotFoundException {
        Person person = peopleRepository.findById(id);
        if(!isPersonValid(person,type))
            throw new UserNotFoundException();
        person.setDeletedAt(new Date());
        peopleRepository.save(person);
    }

    @Override
    public void updatePerson(long id, UpdateUserDetailsDto updateUserDetailsDto, PersonType type) throws UserNotFoundException, UserAlreadyExistException{
        Person person = peopleRepository.findById(id);
        if(!isPersonValid(person,type))
            throw new UserNotFoundException();

        if(!updateUserDetailsDto.getLogin().equals(person.getLogin().getLogin()) && loginService.findByLogin(updateUserDetailsDto.getLogin()) != null)
            throw new UserAlreadyExistException();

        if(!updateUserDetailsDto.getPesel().equals(person.getPesel()) && peopleRepository.findByPesel(updateUserDetailsDto.getPesel()) != null)
            throw new UserAlreadyExistException();


        person.setModifiedAt(new Date());
        person.setFirstName(updateUserDetailsDto.getFirstName());
        person.setLastName(updateUserDetailsDto.getLastName());
        person.setPesel(updateUserDetailsDto.getPesel());
        person.getLogin().setLogin(updateUserDetailsDto.getLogin());

        peopleRepository.save(person);
    }

    @Override
    public Map<String, Object> getPersonDetails(long id, PersonType type) throws UserNotFoundException {
        Person person = peopleRepository.findById(id);
        if(!isPersonValid(person,type))
            throw new UserNotFoundException();

        Map<String,Object> result = new HashMap<>();
        result.put("login", person.getLogin().getLogin());
        result.put("person",person);

        return result;
    }

    @Override
    public List<Person> getPeople(PersonType type) {
        return peopleRepository.findByTypeAndDeletedAtIsNullOrderByFirstName(type);
    }

    @Override
    public String createPerson(CreatePersonDto createPersonDto, PersonType type) throws UserAlreadyExistException {
        if(loginService.findByLogin(createPersonDto.getLogin()) != null
                || peopleRepository.findByPesel(createPersonDto.getPesel()) != null)
            throw new UserAlreadyExistException();

        Role role = roleRepository.findByName(PersonTypeToRole(type));

        Login login = new Login();
        login.setLogin(createPersonDto.getLogin());
        login.setResetPassword(true);
        login.setRole(role);

        Person person = new Person();
        person.setFirstName(createPersonDto.getFirstName());
        person.setLastName(createPersonDto.getLastName());
        person.setModifiedAt(new Date());
        person.setGroups(Collections.emptyList());
        person.setType(type);
        person.setPesel(createPersonDto.getPesel());
        person.setCreatedAt(new Date());

        login.setPerson(person);

        String password = loginService.generateNewPassword(login);

        return password;
    }

    public boolean isPersonValid(Person person, PersonType type)
    {
        return (person != null && person.getType() == type && !person.isDeleted());
    }

    public String PersonTypeToRole(PersonType personType)
    {
        if(personType == PersonType.ADMIN)
            return "ROLE_ADMIN";
        else if(personType == PersonType.TEACHER)
            return "ROLE_TEACHER";
        else
            return "ROLE_STUDENT";
    }
}
