package pl.nanocoder.testserver.service;

import pl.nanocoder.testserver.persistence.pojos.GroupedPeople;
import pl.nanocoder.testserver.persistence.pojos.PersonType;
import pl.nanocoder.testserver.web.error.*;

import java.util.List;

public interface IGroupService {
    List<GroupedPeople> getGroupedPeopleByType(PersonType personType);
    void createGroup(String groupName) throws GroupAlreadyExistException;
    void deleteGroup(long id) throws GroupNotFoundException;
    void addPersonToGroup(long groupId, long personId) throws UserNotFoundException, GroupNotFoundException, UserAlreadyExistInGroupException;
    void removePersonFromGroup(long groupId, long personId) throws UserNotFoundException, GroupNotFoundException, UserNotExistInGroupException;
}
