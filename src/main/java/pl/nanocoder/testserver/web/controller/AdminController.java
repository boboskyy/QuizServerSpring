package pl.nanocoder.testserver.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.nanocoder.testserver.persistence.model.Person;
import pl.nanocoder.testserver.persistence.pojos.GroupedPeople;
import pl.nanocoder.testserver.persistence.pojos.PersonType;
import pl.nanocoder.testserver.service.IGroupService;
import pl.nanocoder.testserver.service.ILoginService;
import pl.nanocoder.testserver.service.IPeopleService;
import pl.nanocoder.testserver.web.dto.CreatePersonDto;
import pl.nanocoder.testserver.web.dto.UpdateUserDetailsDto;
import pl.nanocoder.testserver.web.util.GenericResponse;
import pl.nanocoder.testserver.web.util.Views;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private IPeopleService peopleService;

    @Autowired
    private IGroupService groupService;

    @Autowired
    private ILoginService loginService;

    //region GET's

    @JsonView(Views.PeopleAll.class)
    @PreAuthorize("hasAuthority('permission.admin.get_teachers')")
    @GetMapping("/teachers")
    public List<Person> getTeachers()
    {
        return peopleService.getPeople(PersonType.TEACHER);
    }

    @JsonView(Views.PersonDetails.class)
    @PreAuthorize("hasAuthority('permission.admin.get_teacher_details')")
    @GetMapping("/teachers")
    public Map<String,Object> getTeacherDetails(@RequestParam("id") long id)
    {
        return peopleService.getPersonDetails(id,PersonType.TEACHER);
    }

    @PreAuthorize("hasAuthority('permission.admin.get_teachers') " +
            "AND hasAuthority('permission.group.get_groups')")
    @GetMapping("/groups/teachers")
    public List<GroupedPeople> getGroupedTeachers()
    {
        List<GroupedPeople> groups = groupService.getGroupedPeopleByType(PersonType.TEACHER);
        return groups;
    }

    //endregion

    //region PUT

    @PreAuthorize("hasAuthority('permission.admin.update_teacher')")
    @PutMapping("/teachers")
    public GenericResponse updateTeacher(@RequestParam("id") long id,
                                         @Valid @RequestBody UpdateUserDetailsDto updateUserDetailsDto)
    {
        peopleService.updatePerson(id,updateUserDetailsDto,PersonType.TEACHER);
        return new GenericResponse("success");
    }

    //endregion

    //region POST

    @PreAuthorize("hasAuthority('permission.admin.add_teacher')")
    @PostMapping("/teachers")
    public Map<String,Object> addTeacher(@Valid @RequestBody CreatePersonDto createPersonDto)
    {
        Map<String,Object> result = new HashMap<>();
        String password = peopleService.createPerson(createPersonDto,PersonType.TEACHER);

        result.put("message","success");
        result.put("password", password);
        return result;
    }

    @PreAuthorize("hasAuthority('permission.admin.reset_teacher')")
    @PostMapping("/teachers/reset")
    public Map<String,String>  restTeacher(@RequestParam("id") long id){
        String password = loginService.resetUser(id,PersonType.TEACHER);
        Map<String, String> result = new HashMap<>();
        result.put("password",password);
        result.put("message","success");
        return result;
    }

    //endregion

    //region DELETE's
    @PreAuthorize("hasAuthority('permission.admin.delete_teacher')")
    @DeleteMapping("/teachers")
    public GenericResponse deleteTeacher(@RequestParam("id") long id)
    {
        peopleService.deletePerson(id,PersonType.TEACHER);
        return new GenericResponse("success");
    }
    //endregion
}
