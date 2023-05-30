package pl.nanocoder.testserver.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.nanocoder.testserver.persistence.model.Schedule;

import java.util.Date;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule,Long> {
    Schedule findById(long id);
    List<Schedule> findByQuizCreatorId(long id);
    List<Schedule> findByQuizId(long id);
    List<Schedule> findByAvailableStudentIdAndAvailableInstanceIsNullAndStartsAtBeforeAndEndsAtAfter(long userId, Date dateStarts, Date dateEnds);//todo replace with @Query
}
