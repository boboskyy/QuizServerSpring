package pl.nanocoder.testserver.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.nanocoder.testserver.persistence.model.Login;
import pl.nanocoder.testserver.persistence.model.QuizInstance;
import pl.nanocoder.testserver.persistence.model.QuizResult;
import pl.nanocoder.testserver.persistence.model.Schedule;
import pl.nanocoder.testserver.service.ILoginService;
import pl.nanocoder.testserver.service.IQuizService;
import pl.nanocoder.testserver.web.dto.FinishQuizDto;
import pl.nanocoder.testserver.web.util.Views;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private IQuizService quizService;

    @Autowired
    private ILoginService loginService;


    //region GET
    @JsonView(Views.QuizStudent.class)
    @PreAuthorize("hasAuthority('permission.student.get_my_tests')")
    @GetMapping("/tests")
    public List<Schedule> getTests()
    {
        UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Login login = loginService.findByLogin(details.getUsername());

        return  quizService.getAvailableQuizzes(login.getPerson().getId());
    }

    @PreAuthorize("hasAuthority('permission.student.start_test')")
    @GetMapping("/tests/start")
    @JsonView(Views.QuizStudent.class)
    public QuizInstance startTest(@RequestParam("id") long scheduleId)
    {
        UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Login login = loginService.findByLogin(details.getUsername());

        return quizService.startQuiz(login.getPerson(), scheduleId);
    }

    @PreAuthorize("hasAuthority('permission.student.get_my_results')")
    @GetMapping("/tests/results")
    public List<QuizResult> getResults()
    {
        UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Login login = loginService.findByLogin(details.getUsername());

        return quizService.getResults(login.getPerson());
    }
    //endregion

    //region POST
    @PreAuthorize("hasAuthority('permission.student.finish_test')")
    @PostMapping("/tests/finish")
    public QuizResult finish(@Valid @RequestBody FinishQuizDto dto)
    {
        UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Login login = loginService.findByLogin(details.getUsername());

        return quizService.finishQuiz(login.getPerson(),dto);

    }
    //endregion

}
