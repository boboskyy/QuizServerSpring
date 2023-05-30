package pl.nanocoder.testserver.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.nanocoder.testserver.web.util.Views;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@JsonView(Views.QuizStudent.class)
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(schema = "test", name = "quiz_instances")
public class QuizInstance {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quiz_instances_seq_id_gen")
    @SequenceGenerator(name = "quiz_instances_seq_id_gen", sequenceName = "test.quiz_instances_seq_id", allocationSize = 1)
    private Long id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "quizId")
    private Quiz quiz;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "studentId")
    private Person student;

    @OneToMany(mappedBy = "quizInstance", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GeneratedQuestion> generatedQuestions;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date startedAt;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST},
            mappedBy = "instance")
    private AvailableQuiz available;
}
