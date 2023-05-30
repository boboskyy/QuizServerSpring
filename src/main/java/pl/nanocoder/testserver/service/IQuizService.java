package pl.nanocoder.testserver.service;

import pl.nanocoder.testserver.persistence.model.*;
import pl.nanocoder.testserver.web.dto.AddQuestionDto;
import pl.nanocoder.testserver.web.dto.CreateQuizDto;
import pl.nanocoder.testserver.web.dto.CreateScheduleDto;
import pl.nanocoder.testserver.web.dto.FinishQuizDto;
import pl.nanocoder.testserver.web.error.*;

import java.util.List;

public interface IQuizService {

    //region Teacher access
    List<QuestionGroup> getQuestionGroups(long userId)         throws UserNotFoundException;
    void createQuestionGroup(Person creator, String groupName) throws GroupAlreadyExistException;
    void deleteQuestionGroup(long groupId)                     throws GroupNotFoundException;
    void addQuestionToGroup(long groupid, AddQuestionDto dto)  throws GroupNotFoundException;
    Question getQuestionDetails(long questionId)               throws QuestionNotFoundException;
    void updateQuestion(long questionId, AddQuestionDto dto)   throws QuestionNotFoundException;
    void deleteQuestion(long questionId)                       throws QuestionNotFoundException;
    List<Quiz> getQuizes(long userId)                          throws UserNotFoundException;
    void createQuiz(Person creator, CreateQuizDto dto)         throws GroupNotFoundException, QGInvalidQuestionCountException, QGInvalidModulesCountException;
    void deleteQuiz(long quizId)                               throws QuizNotFoundException;
    void createSchedule(CreateScheduleDto dto)                 throws QuizNotFoundException, UserNotFoundException;
    void deleteSchedule(long scheduleId)                       throws ScheduleNotFoundException;
    List<Schedule> getSchedules(long userId)                   throws UserNotFoundException;
    //endregion

    //region Student access
    List<Schedule> getAvailableQuizzes(long studentId)         throws UserNotFoundException;
    QuizInstance startQuiz(Person student,
                                      long scheduleId)         throws QuizNotFoundException, QuizExpiredException, QuizAlreadyRunningException;
    QuizResult finishQuiz(Person student, FinishQuizDto dto)         throws QuizNotFoundException, QuizExpiredException;
    List<QuizResult> getResults(Person student);
    //endregion
}
