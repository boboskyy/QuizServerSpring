package pl.nanocoder.testserver.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(schema = "test", name = "quiz_results")
public class QuizResult {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quiz_results_seq_id_gen")
    @SequenceGenerator(name = "quiz_results_seq_id_gen", sequenceName = "test.quiz_results_seq_id", allocationSize = 1)
    private Long id;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "personId")
    private Person person;

    private int score;

    private int questionsCount;

    @Column(nullable = false)
    private String quizTitle;

    @Column(nullable = false)
    private String quizCreator;
}
