package pl.nanocoder.testserver.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.nanocoder.testserver.persistence.model.Quiz;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz,Long> {
    Quiz findById(long id);
    List<Quiz> findByCreatorId(long id);
}
