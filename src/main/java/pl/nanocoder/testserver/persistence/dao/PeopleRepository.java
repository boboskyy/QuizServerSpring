package pl.nanocoder.testserver.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.nanocoder.testserver.persistence.model.Person;
import pl.nanocoder.testserver.persistence.pojos.PersonType;

import java.util.List;

public interface PeopleRepository extends JpaRepository<Person, Long> {
    Person findById(long id);
    Person findByPesel(String pesel);
    Person findByLoginLogin(String login);
    List<Person> findByType(PersonType type);
    List<Person> findByTypeAndDeletedAtIsNullOrderByFirstName(PersonType type);
    List<Person> findByTypeAndDeletedAtIsNullAndGroups_id(PersonType type, long id);
}
