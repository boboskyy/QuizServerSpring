package pl.nanocoder.testserver.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.nanocoder.testserver.persistence.model.Answer;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer,Long> {
    List<Answer> findByQuestionIdAndCorrectIsTrue(long questionId);
}
