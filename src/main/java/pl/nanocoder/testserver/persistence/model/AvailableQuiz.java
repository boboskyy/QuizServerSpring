package pl.nanocoder.testserver.persistence.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(schema = "test", name = "available_quizzes")
public class AvailableQuiz {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "available_quizzes_seq_gen")
    @SequenceGenerator(name = "available_quizzes_seq_gen", sequenceName = "test.available_quizzes_seq_id", allocationSize = 1)
    private Long id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "scheduleId")
    private Schedule schedule;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "personId")
    private Person student;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "quizInstanceId")
    private QuizInstance instance;
}
