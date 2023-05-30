package pl.nanocoder.testserver.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.nanocoder.testserver.persistence.model.Question;

public interface QuestionRepository extends JpaRepository<Question,Long> {
    Question findById(long id);
}
