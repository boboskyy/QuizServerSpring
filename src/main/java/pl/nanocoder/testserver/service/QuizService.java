package pl.nanocoder.testserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.nanocoder.testserver.persistence.dao.*;
import pl.nanocoder.testserver.persistence.model.*;
import pl.nanocoder.testserver.persistence.pojos.PersonType;
import pl.nanocoder.testserver.web.dto.AddQuestionDto;
import pl.nanocoder.testserver.web.dto.CreateQuizDto;
import pl.nanocoder.testserver.web.dto.CreateScheduleDto;
import pl.nanocoder.testserver.web.dto.FinishQuizDto;
import pl.nanocoder.testserver.web.dto.sub_dto.AddAnswerDto;
import pl.nanocoder.testserver.web.dto.sub_dto.AnsweredQuestionDto;
import pl.nanocoder.testserver.web.dto.sub_dto.QModuleDto;
import pl.nanocoder.testserver.web.error.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class QuizService implements IQuizService {

    @Autowired
    private QuestionGroupRepository questionGroupRepository;

    @Autowired
    private IPeopleService peopleService;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private AvailableQuizRepository availableQuizRepository;

    @Autowired
    private GeneratedQuestionRepository generatedQuestionRepository;

    @Autowired
    private QuizInstanceRepository quizInstanceRepository;

    @Autowired
    private QuizResultRepository quizResultRepository;

    private static Random random = new Random();

    //region Teacher access

    @Override
    public List<QuestionGroup> getQuestionGroups(long userId) throws UserNotFoundException{
        Person person = peopleService.findById(userId);
        if(person == null)
            throw new UserNotFoundException();
        return questionGroupRepository.findByCreatorIdAndDeletedAtIsNull(person.getId());
    }

    @Override
    public void createQuestionGroup(Person creator, String groupName) throws GroupAlreadyExistException {
        if(questionGroupRepository.findByName(groupName) != null)
            throw new GroupAlreadyExistException();

        QuestionGroup questionGroup = new QuestionGroup();
        questionGroup.setName(groupName);
        questionGroup.setCreator(creator);

        questionGroupRepository.save(questionGroup);
    }

    @Override
    public void deleteQuestionGroup(long groupId) throws GroupNotFoundException {
        QuestionGroup questionGroup = questionGroupRepository.findById(groupId);
        if(questionGroup == null)
            throw new GroupNotFoundException();

        questionGroup.setDeletedAt(new Date());
        questionGroupRepository.save(questionGroup);
    }

    @Override
    public void addQuestionToGroup(long groupid, AddQuestionDto dto) throws GroupNotFoundException {
        QuestionGroup group = questionGroupRepository.findById(groupid);
        if(group == null)
            throw new GroupNotFoundException();

        Question question = new Question();

        question.setValue(dto.getValue());
        question.setAnswers(answersDtoToAnswer(dto));
        group.addQuestion(question);
        questionGroupRepository.save(group);
    }

    @Override
    public Question getQuestionDetails(long questionId) throws QuestionNotFoundException {
        Question question = questionRepository.findById(questionId);
        if(question == null)
            throw new QuestionNotFoundException();

        return question;
    }

    @Override
    public void updateQuestion(long questionId, AddQuestionDto dto) throws QuestionNotFoundException {
        Question question = questionRepository.findById(questionId);
        if(question == null)
            throw new QuestionNotFoundException();

        answerRepository.deleteAll(question.getAnswers());

        question.setValue(dto.getValue());
        question.setAnswers(answersDtoToAnswer(dto));

        questionRepository.save(question);
    }

    private List<Answer> answersDtoToAnswer(AddQuestionDto dto)
    {
        List<Answer> answers = new ArrayList<>();

        for(AddAnswerDto addAnswerDto : dto.getAnswers())
        {
            Answer answer = new Answer();
            answer.setValue(addAnswerDto.getValue());
            answer.setCorrect(addAnswerDto.isCorrect());
            answers.add(answer);
        }
        return answers;
    }

    @Override
    public void deleteQuestion(long questionId) throws QuestionNotFoundException {
        Question question = questionRepository.findById(questionId);
        if(question == null)
            throw new QuestionNotFoundException();

        answerRepository.deleteAll(question.getAnswers());
        questionRepository.delete(question);
    }

    @Override
    public List<Quiz> getQuizes(long userId) throws UserNotFoundException {
        Person person = peopleService.findById(userId);
        if(person == null)
            throw new UserNotFoundException();
        return quizRepository.findByCreatorId(userId);
    }

    @Override
    public void createQuiz(Person creator, CreateQuizDto dto) throws GroupNotFoundException, QGInvalidQuestionCountException , QGInvalidModulesCountException{
        Quiz quiz = new Quiz();

        quiz.setTitle(dto.getTitle());
        quiz.setDescription(dto.getDescription());
        quiz.setQuizTime(dto.getQuizTime());
        quiz.setCreator(creator);

        List<QGModule> modules = new ArrayList<>();

        for(QModuleDto qModuleDto : dto.getModules())
        {
            QuestionGroup qg = questionGroupRepository.findById((long)qModuleDto.getGroupId());
            if(qg == null)
                throw new GroupNotFoundException();
            if(qModuleDto.getCount() > qg.getQuestions().size())
                throw new QGInvalidQuestionCountException();
            if(qModuleDto.getCount() == 0)
                continue;

            QGModule module = new QGModule();
            module.setQuestionGroup(qg);
            module.setQuestionsToGenerate(qModuleDto.getCount());
            module.setQuiz(quiz);

            modules.add(module);
        }
        if(modules.size() == 0)
            throw new QGInvalidModulesCountException();

        quiz.setQuestionModules(modules);

        quizRepository.save(quiz);
    }

    @Override
    public void deleteQuiz(long quizId) throws QuizNotFoundException {
        Quiz quiz = quizRepository.findById(quizId);
        if(quiz == null)
            throw new QuizNotFoundException();
        scheduleRepository.deleteAll(scheduleRepository.findByQuizId(quizId));
        quizInstanceRepository.deleteAll(quizInstanceRepository.findByQuizId(quizId));
        quizRepository.delete(quiz);
    }

    @Override
    public void createSchedule(CreateScheduleDto dto) throws QuizNotFoundException, UserNotFoundException {
        Quiz quiz = quizRepository.findById((long)dto.getQuizId());
        if(quiz == null)
            throw new QuizNotFoundException();

       List<AvailableQuiz> available = new ArrayList<>();
        for(Integer id : dto.getStudents())
        {
            Person person = peopleService.findById(id);
            if(person == null || person.getType() != PersonType.STUDENT)
                throw new UserNotFoundException();

            AvailableQuiz availableQuiz = new AvailableQuiz();
            availableQuiz.setStudent(person);

            available.add(availableQuiz);
        }

        Schedule schedule = new Schedule();
        schedule.setQuiz(quiz);
        schedule.setStartsAt(dto.getStartsAt());
        schedule.setEndsAt(dto.getEndsAt());
        schedule.setAvailable(available);

        scheduleRepository.save(schedule);
    }

    @Override
    public void deleteSchedule(long scheduleId) throws ScheduleNotFoundException {
        Schedule schedule = scheduleRepository.findById(scheduleId);
        if(schedule == null)
            throw new ScheduleNotFoundException();
        scheduleRepository.delete(schedule);
    }

    @Override
    public List<Schedule> getSchedules(long userId) throws UserNotFoundException {
        return scheduleRepository.findByQuizCreatorId(userId);
    }

    //endregion

    //region Student access

    @Override
    public List<Schedule> getAvailableQuizzes(long studentId) throws UserNotFoundException{
        Person student = peopleService.findById(studentId);
        if(student == null)
            throw new UserNotFoundException();

        List<Schedule> schedules = scheduleRepository.findByAvailableStudentIdAndAvailableInstanceIsNullAndStartsAtBeforeAndEndsAtAfter(studentId,new Date(), new Date());

        return schedules;
    }

    @Override
    public QuizInstance startQuiz(Person student, long scheduleId) throws QuizNotFoundException, QuizExpiredException, QuizAlreadyRunningException {
        Schedule schedule = scheduleRepository.findById(scheduleId);
        if(schedule == null || !schedule.getAvailableFor().contains(student))
            throw new QuizNotFoundException();

        AvailableQuiz access = availableQuizRepository.findByStudentIdAndScheduleId(student.getId(),scheduleId);

        if(quizNotAvailable(access))
            throw new QuizExpiredException();

        if(access.getInstance() != null) {
            return access.getInstance();
        }

        QuizInstance quizInstance = new QuizInstance();

        quizInstance.setStudent(student);
        quizInstance.setQuiz(schedule.getQuiz());
        quizInstance.setStartedAt(new Date());

        List<Question> allQuestions = new ArrayList<>();

        int temp = 0;
        for(QGModule module : schedule.getQuiz().getQuestionModules()) {
            List<Question> questionsToPickFrom = module.getQuestionGroup().getQuestions();

            while (temp < module.getQuestionsToGenerate()) {
                Question question = questionsToPickFrom.get(random.nextInt(questionsToPickFrom.size()));
                if(allQuestions.contains(question))
                    continue;
                allQuestions.add(question);
                temp++;
            }
            temp = 0;
        }


        List<GeneratedQuestion> result = allQuestions.stream().map(question ->
        {
            Collections.shuffle(question.getAnswers());

            GeneratedQuestion generatedQuestion = new GeneratedQuestion();
            generatedQuestion.setQuestion(question);
            generatedQuestion.setQuizInstance(quizInstance);

            return generatedQuestion;
        }).collect(Collectors.toList());
        quizInstance.setGeneratedQuestions(result);

        List<GeneratedQuestion> copy = new ArrayList<>(result);
        generatedQuestionRepository.saveAll(copy);

        access.setInstance(quizInstance);
        return quizInstance;
    }

    @Override
    public QuizResult finishQuiz(Person student, FinishQuizDto dto) throws QuizNotFoundException, QuizExpiredException{
        QuizInstance quizInstance = quizInstanceRepository.findById((long)dto.getQuizInstanceId());
        if(quizInstance == null || quizInstance.getStudent() != student)
            throw new QuizNotFoundException();

        if(quizExpired(quizInstance))
            throw new QuizExpiredException();

        if(quizInstance.getAvailable() == null)
            throw new QuizNotFoundException();
        //handle 0 point'ing

        Map<Long, AnsweredQuestionDto> answeredQuestions = validateFixQuizInstanceAnswers(quizInstance.getGeneratedQuestions(),dto.getData());

        int score = 0;
        
        for(GeneratedQuestion genQuestion : quizInstance.getGeneratedQuestions())
        {
            AnsweredQuestionDto answeredDto = answeredQuestions.get(genQuestion.getId());

            List<Long> correctAnswersIds = answerRepository.findByQuestionIdAndCorrectIsTrue(genQuestion.getQuestion().getId())
                    .stream().map(Answer::getId).collect(Collectors.toList());
            if(checkAnswers(correctAnswersIds,answeredDto.getAnswersIds()))
                score++;
        }

        availableQuizRepository.delete(quizInstance.getAvailable());
        generatedQuestionRepository.deleteAll(quizInstance.getGeneratedQuestions());
        quizInstanceRepository.delete(quizInstance);

        QuizResult result = new QuizResult();

        result.setPerson(student);
        result.setScore(score);
        result.setQuestionsCount(quizInstance.getGeneratedQuestions().size());
        result.setQuizTitle(quizInstance.getQuiz().getTitle());
        result.setQuizCreator(quizInstance.getQuiz().getCreator().getFirstName() + " " +
                quizInstance.getQuiz().getCreator().getLastName());

        quizResultRepository.save(result);

        return result;
    }

    @Override
    public List<QuizResult> getResults(Person student) {
        return quizResultRepository.findByPersonId(student.getId());
    }

    private boolean quizNotAvailable(AvailableQuiz availableQuiz)
    {
        Date now = new Date();
        if(!now.after(availableQuiz.getSchedule().getStartsAt())
                || !now.before(availableQuiz.getSchedule().getEndsAt()))
            return true;
        return false;
    }

    private boolean quizExpired(QuizInstance instance)
    {
        Date now = new Date();
        if(now.getTime() - instance.getStartedAt().getTime()
                    > instance.getQuiz().getQuizTime() * 60 * 1000)
            return true;
        return false;
    }

    private Map<Long, AnsweredQuestionDto> validateFixQuizInstanceAnswers(List<GeneratedQuestion> generatedQuestions, List<AnsweredQuestionDto> dtos)
    {
        Map<Long, AnsweredQuestionDto> dtoMap =
                dtos.stream().collect(Collectors.toMap(AnsweredQuestionDto::getQuestionId, question -> question));

        generatedQuestions.forEach(question -> {
            if(!dtoMap.containsKey(question.getId()))
                dtoMap.put(question.getId(), new AnsweredQuestionDto());
        });

        return dtoMap;
    }

    private boolean checkAnswers(List<Long> correctAnswers, List<Long> fromUser)
    {
        if(fromUser.size() != correctAnswers.size())
            return false;

        for(Long correct : correctAnswers)
            if(!fromUser.contains(correct))
                return false;
        return true;
    }


    //endregion
}
