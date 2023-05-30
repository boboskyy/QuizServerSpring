package pl.nanocoder.testserver.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.nanocoder.testserver.persistence.model.AvailableQuiz;

public interface AvailableQuizRepository extends JpaRepository<AvailableQuiz,Long> {
    AvailableQuiz findByStudentIdAndScheduleId(long studentId, long scheduleId);
}
