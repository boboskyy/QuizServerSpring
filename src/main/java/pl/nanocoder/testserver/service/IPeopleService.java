package pl.nanocoder.testserver.service;

import pl.nanocoder.testserver.persistence.model.Person;
import pl.nanocoder.testserver.persistence.pojos.PersonType;
import pl.nanocoder.testserver.web.dto.CreatePersonDto;
import pl.nanocoder.testserver.web.dto.UpdateUserDetailsDto;
import pl.nanocoder.testserver.web.error.UserAlreadyExistException;
import pl.nanocoder.testserver.web.error.UserNotFoundException;

import java.util.List;
import java.util.Map;

public interface IPeopleService {

    Person findById(long id) throws UserNotFoundException;
    void deletePerson(long id, PersonType type) throws UserNotFoundException;
    void updatePerson(long id, UpdateUserDetailsDto updateUserDetailsDto, PersonType type) throws UserNotFoundException, UserAlreadyExistException;
    Map<String,Object> getPersonDetails(long id, PersonType type) throws UserNotFoundException;
    List<Person> getPeople(PersonType type);
    String createPerson(CreatePersonDto createPersonDto, PersonType type) throws UserAlreadyExistException;

}
