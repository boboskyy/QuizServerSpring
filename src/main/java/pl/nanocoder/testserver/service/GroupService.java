package pl.nanocoder.testserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.nanocoder.testserver.persistence.dao.GroupRepository;
import pl.nanocoder.testserver.persistence.dao.PeopleRepository;
import pl.nanocoder.testserver.persistence.model.Group;
import pl.nanocoder.testserver.persistence.model.Person;
import pl.nanocoder.testserver.persistence.pojos.GroupedPeople;
import pl.nanocoder.testserver.persistence.pojos.PersonType;
import pl.nanocoder.testserver.web.error.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class GroupService implements IGroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private PeopleRepository peopleRepository;

    public List<GroupedPeople> getGroupedPeopleByType(PersonType personType)
    {
        List<GroupedPeople> result = new ArrayList<>();
        List<Group> groups = groupRepository.findDistinctByPeople_TypeAndDeletedAtIsNullOrPeopleIsNullAndDeletedAtIsNull(personType);
        for (Group group :
                groups) {
            List<Person> people = peopleRepository.findByTypeAndDeletedAtIsNullAndGroups_id(personType,group.getId());
            result.add(new GroupedPeople(group,people));
        }

        return result;
    }

    @Override
    public void createGroup(String groupName) throws GroupAlreadyExistException {
        if(groupRepository.findByName(groupName) != null)
            throw new GroupAlreadyExistException();

        Group group = new Group();
        group.setName(groupName);
        group.setCreatedAt(new Date());
        group.setModifiedAt(new Date());

        groupRepository.save(group);
    }

    @Override
    public void deleteGroup(long id) throws GroupNotFoundException {
        Group group = groupRepository.findById(id);
        if(group == null)
            throw new GroupNotFoundException();

        group.setDeletedAt(new Date());
        groupRepository.save(group);
    }

    @Override
    public void addPersonToGroup(long groupId, long personId) throws UserNotFoundException, GroupNotFoundException, UserAlreadyExistInGroupException {
        Group group = groupRepository.findById(groupId);
        Person person = peopleRepository.findById(personId);
        if(group == null)
            throw new GroupNotFoundException();
        if(person == null)
            throw new UserNotFoundException();
        if(userAlreadyInGroup(personId,group))
            throw new UserAlreadyExistInGroupException();
        List<Group> groups = person.getGroups();
        groups.add(group);
        person.setGroups(groups);

        peopleRepository.save(person);
    }

    @Override
    public void removePersonFromGroup(long groupId, long personId) throws UserNotFoundException, GroupNotFoundException, UserNotExistInGroupException {
        Group group = groupRepository.findById(groupId);
        Person person = peopleRepository.findById(personId);
        if(group == null)
            throw new GroupNotFoundException();
        if(person == null)
            throw new UserNotFoundException();

        if(!userAlreadyInGroup(personId,group))
            throw new UserNotExistInGroupException();

        List<Group> groups = person.getGroups();
        groups.remove(group);

        person.setGroups(groups);
        peopleRepository.save(person);
    }

    private boolean userAlreadyInGroup(long id, Group group)
    {
        for(Person person : group.getPeople())
            if(person.getId() == id)
                return true;
        return false;
    }
}
