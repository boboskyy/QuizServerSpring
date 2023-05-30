package pl.nanocoder.testserver.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.nanocoder.testserver.persistence.model.QuestionGroup;

import java.util.List;

public interface QuestionGroupRepository extends JpaRepository<QuestionGroup, Long> {
    List<QuestionGroup> findByCreatorIdAndDeletedAtIsNull(long id);
    QuestionGroup findByName(String name);
    QuestionGroup findById(long id);
}
