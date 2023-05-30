package pl.nanocoder.testserver.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.nanocoder.testserver.persistence.model.GeneratedQuestion;

public interface GeneratedQuestionRepository extends JpaRepository<GeneratedQuestion, Long> {
}
