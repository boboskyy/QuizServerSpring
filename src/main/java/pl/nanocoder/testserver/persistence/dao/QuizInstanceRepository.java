package pl.nanocoder.testserver.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.nanocoder.testserver.persistence.model.QuizInstance;

import java.util.List;

public interface QuizInstanceRepository extends JpaRepository<QuizInstance,Long> {
    QuizInstance findById(long id);
    List<QuizInstance> findByQuizId(long id);
}
