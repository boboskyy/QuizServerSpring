package pl.nanocoder.testserver.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.nanocoder.testserver.service.IGroupService;
import pl.nanocoder.testserver.web.dto.CreateGroupDto;
import pl.nanocoder.testserver.web.util.GenericResponse;

import javax.validation.Valid;

@RestController
@Transactional
@RequestMapping("/groups")
public class GroupController {

    @Autowired
    private IGroupService groupService;

    @PreAuthorize("hasAuthority('permission.group.add_group')")
    @PostMapping
    public GenericResponse addGroup(@Valid @RequestBody CreateGroupDto createGroupDto){
        groupService.createGroup(createGroupDto.getName());

        return new GenericResponse("success");
    }

    @PreAuthorize("hasAuthority('permission.group.delete_group')")
    @DeleteMapping
    public GenericResponse addGroup(@RequestParam("id") long id){
        groupService.deleteGroup(id);

        return new GenericResponse("success");
    }

    @PreAuthorize("hasAuthority('permission.group.add_person_group')")
    @PostMapping("/people")
    public GenericResponse addToGroup(@RequestParam("groupId") long groupId,
                                    @RequestParam("personId") long personId){

        groupService.addPersonToGroup(groupId,personId);
        return new GenericResponse("success");
    }

    @PreAuthorize("hasAuthority('permission.group.remove_person_group')")
    @DeleteMapping("/people")
    public GenericResponse removeFromGroup(@RequestParam("groupId") long groupId,
                                    @RequestParam("personId") long personId){

        groupService.removePersonFromGroup(groupId,personId);
        return new GenericResponse("success");
    }
}
