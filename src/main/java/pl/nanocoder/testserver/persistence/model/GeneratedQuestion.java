package pl.nanocoder.testserver.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.nanocoder.testserver.web.util.Views;

import javax.persistence.*;

@JsonView({Views.QuizStudent.class})
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(schema = "test", name = "generated_questions")
public class GeneratedQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generated_questions_seq_id_gen")
    @SequenceGenerator(name = "generated_questions_seq_id_gen", sequenceName = "test.generated_questions_seq_id", allocationSize = 1)
    private Long id;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "quizInstanceId")
    private QuizInstance quizInstance;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "questionId")
    private Question question;
}
