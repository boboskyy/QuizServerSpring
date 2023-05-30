package pl.nanocoder.testserver.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.nanocoder.testserver.persistence.model.*;
import pl.nanocoder.testserver.persistence.pojos.GroupedPeople;
import pl.nanocoder.testserver.persistence.pojos.PersonType;
import pl.nanocoder.testserver.service.IGroupService;
import pl.nanocoder.testserver.service.ILoginService;
import pl.nanocoder.testserver.service.IPeopleService;
import pl.nanocoder.testserver.service.IQuizService;
import pl.nanocoder.testserver.web.dto.*;
import pl.nanocoder.testserver.web.util.GenericResponse;
import pl.nanocoder.testserver.web.util.Views;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Transactional
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private IPeopleService peopleService;

    @Autowired
    private IGroupService groupService;

    @Autowired
    private ILoginService loginService;

    @Autowired
    private IQuizService quizService;

    //region Students stuff

    //region GET
    @JsonView(Views.PeopleAll.class)
    @PreAuthorize("hasAuthority('permission.teacher.get_students')")
    @GetMapping("/students")
    public List<Person> getStudents()
    {
        return peopleService.getPeople(PersonType.STUDENT);
    }

    @JsonView(Views.PersonDetails.class)
    @PreAuthorize("hasAuthority('permission.teacher.get_student_details')")
    @GetMapping("/students")
    public Map<String,Object> getStudentDetails(@RequestParam("id") long id)
    {
        return peopleService.getPersonDetails(id,PersonType.STUDENT);
    }

    @PreAuthorize("hasAuthority('permission.teacher.get_students') " +
            "AND hasAuthority('permission.group.get_groups')")
    @GetMapping("/groups/students")
    public List<GroupedPeople> getGroupedStudents()
    {
        List<GroupedPeople> groups = groupService.getGroupedPeopleByType(PersonType.STUDENT);
        return groups;
    }
    //endregion

    //region PUT

    @PreAuthorize("hasAuthority('permission.teacher.update_student')")
    @PutMapping("/students")
    public GenericResponse updateStudent(@RequestParam("id") long id,
                                         @Valid @RequestBody UpdateUserDetailsDto updateUserDetailsDto)
    {
        peopleService.updatePerson(id,updateUserDetailsDto,PersonType.STUDENT);
        return new GenericResponse("success");
    }

    //endregion

    //region POST

    @PreAuthorize("hasAuthority('permission.teacher.add_student')")
    @PostMapping("/students")
    public Map<String,Object> addStudent(@Valid @RequestBody CreatePersonDto createPersonDto)
    {
        Map<String,Object> result = new HashMap<>();
        String password = peopleService.createPerson(createPersonDto,PersonType.STUDENT);

        result.put("message","success");
        result.put("password", password);
        return result;
    }

    @PreAuthorize("hasAuthority('permission.teacher.reset_student')")
    @PostMapping("/students/reset")
    public Map<String,String>  restStudent(@RequestParam("id") long id){
        String password = loginService.resetUser(id,PersonType.STUDENT);
        Map<String, String> result = new HashMap<>();
        result.put("password",password);
        result.put("message","success");
        return result;
    }

    //endregion

    //region DELETE
    @PreAuthorize("hasAuthority('permission.teacher.delete_student')")
    @DeleteMapping("/students")
    public GenericResponse deleteStudent(@RequestParam("id") long id)
    {
        peopleService.deletePerson(id,PersonType.STUDENT);
        return new GenericResponse("success");
    }
    //endregion

    //endregion

    //region Quiz stuff

    //region GET

    @PreAuthorize("hasAuthority('permission.teacher.get_grouped_questions')")
    @GetMapping("/questions")
    public List<QuestionGroup> getGroupedQuestions()
    {
        UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Login login = loginService.findByLogin(details.getUsername());

        return quizService.getQuestionGroups(login.getPerson().getId());
    }

    @PreAuthorize("hasAuthority('permission.teacher.get_question_details')")
    @GetMapping("/questions/details")
    public Question getQuestionDetails(@RequestParam("id") long id)
    {
        return quizService.getQuestionDetails(id);
    }

    @PreAuthorize("hasAuthority('permission.teacher.get_quizzes')")
    @GetMapping("/quizzes")
    @JsonView(Views.QuizTeacherPublic.class)
    public List<Quiz> getQuizes()
    {
        UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Login login = loginService.findByLogin(details.getUsername());

        return quizService.getQuizes(login.getPerson().getId());
    }

    @JsonView(Views.QuizTeacherPublic.class)
    @PreAuthorize("hasAuthority('permission.teacher.get_schedules')")
    @GetMapping("/schedules")
    public List<Schedule> getSchedules()
    {
        UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Login login = loginService.findByLogin(details.getUsername());

        return quizService.getSchedules(login.getPerson().getId());
    }

    //endregion

    //region POST

    @PreAuthorize("hasAuthority('permission.teacher.add_question_group')")
    @PostMapping("/questions/groups")
    public GenericResponse addQuestionGroup(@Valid @RequestBody CreateGroupDto createGroupDto)
    {
        UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Login login = loginService.findByLogin(details.getUsername());

        quizService.createQuestionGroup(login.getPerson(),createGroupDto.getName());

        return new GenericResponse("success");
    }

    @PreAuthorize("hasAuthority('permission.teacher.add_question_to_group')")
    @PostMapping("/questions")
    public GenericResponse addQuestionToGroup(@RequestParam("id") long id,
                                              @Valid @RequestBody AddQuestionDto addQuestionDto)
    {
        quizService.addQuestionToGroup(id,addQuestionDto);
        return new GenericResponse("success");
    }

    @PreAuthorize("hasAuthority('permission.teacher.add_quiz')")
    @PostMapping("/quizzes")
    public GenericResponse addQuiz(@Valid @RequestBody CreateQuizDto dto)
    {
        UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Login login = loginService.findByLogin(details.getUsername());

        quizService.createQuiz(login.getPerson(),dto);

        return new GenericResponse("success");
    }

    @PreAuthorize("hasAuthority('permission.teacher.add_schedule')")
    @PostMapping("/schedules")
    public GenericResponse addSchedule(@Valid @RequestBody CreateScheduleDto dto)
    {
        quizService.createSchedule(dto);

        return new GenericResponse("success");
    }
    //endregion

    //region PUT

    @PreAuthorize("hasAuthority('permission.teacher.update_question')")
    @PutMapping("/questions")
    public GenericResponse updateQuestion(@RequestParam("id") long id,
                                         @Valid @RequestBody AddQuestionDto dto)
    {
        quizService.updateQuestion(id,dto);
        return new GenericResponse("success");
    }

    //endregion

    //region DELETE

    @PreAuthorize("hasAuthority('permission.teacher.delete_question_group')")
    @DeleteMapping("/questions/groups")
    public GenericResponse deleteQuestionGroup(@RequestParam("id") long id)
    {
        quizService.deleteQuestionGroup(id);
        return new GenericResponse("success");
    }

    @PreAuthorize("hasAuthority('permission.teacher.delete_question')")
    @DeleteMapping("/questions")
    public GenericResponse deleteQuestion(@RequestParam("id") long id)
    {
        quizService.deleteQuestion(id);
        return new GenericResponse("success");
    }

    @PreAuthorize("hasAuthority('permission.teacher.delete_quiz')")
    @DeleteMapping("/quizzes")
    public GenericResponse deleteQuiz(@RequestParam("id") long id)
    {
        quizService.deleteQuiz(id);
        return new GenericResponse("success");
    }

    @PreAuthorize("hasAuthority('permission.teacher.delete_schedule')")
    @DeleteMapping("/schedules")
    public GenericResponse deleteSchedule(@RequestParam("id") long id)
    {
        quizService.deleteSchedule(id);
        return new GenericResponse("success");
    }
    //endregion

    //endregion
}
