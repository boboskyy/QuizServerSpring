package pl.nanocoder.testserver.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.nanocoder.testserver.persistence.model.QuizResult;

import java.util.List;

public interface QuizResultRepository extends JpaRepository<QuizResult,Long> {
    List<QuizResult> findByPersonId(long id);
}
