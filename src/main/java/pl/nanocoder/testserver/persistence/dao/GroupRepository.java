package pl.nanocoder.testserver.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.nanocoder.testserver.persistence.model.Group;
import pl.nanocoder.testserver.persistence.pojos.PersonType;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    //@Query("SELECT e FROM Group e JOIN e.people d WHERE d.type = ?1")
    Group findById(long id);
    List<Group> findDistinctByPeople_TypeAndDeletedAtIsNullOrPeopleIsNullAndDeletedAtIsNull(PersonType type);
    Group findByName(String name);
}
