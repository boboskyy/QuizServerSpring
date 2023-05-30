package pl.nanocoder.testserver.persistence.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.nanocoder.testserver.web.util.Views;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@JsonView(Views.QuizStudent.class)
@Entity
@Getter
@Setter
@NoArgsConstructor
@Data
@Table(schema = "test", name  = "schedules")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "schedules_seq_gen")
    @SequenceGenerator(name = "schedules_seq_gen", sequenceName = "test.schedules_seq_id", allocationSize = 1)
    private Long id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "quizId")
    private Quiz quiz;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startsAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date endsAt;

//    @JsonView(Views.QuizTeacherPublic.class)
//    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
//    @JoinTable(schema = "test", name = "available_quizzes",
//            joinColumns = {@JoinColumn(name = "scheduleId")},
//            inverseJoinColumns = {@JoinColumn(name = "personId")})
//    private List<Person> availableFor;

    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true,
    mappedBy = "schedule")
    private List<AvailableQuiz> available;

    public List<Person> getAvailableFor()
    {
        return available.stream().map(a -> a.getStudent()).collect(Collectors.toList());
    }
    public void setAvailable(List<AvailableQuiz> available) {
        for(AvailableQuiz quiz : available)
            quiz.setSchedule(this);
        this.available = available;
    }
}
