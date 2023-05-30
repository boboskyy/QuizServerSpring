package pl.nanocoder.testserver.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.nanocoder.testserver.web.util.Views;

import javax.persistence.*;

@JsonView(Views.QuizStudent.class)
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(schema = "test", name = "answers")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "answers_seq_gen")
    @SequenceGenerator(name = "answers_seq_gen", sequenceName = "test.answers_seq_id", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String value;

    @JsonView(Views.QuizTeacherPrivate.class)
    @Column(name = "isCorrect", nullable = false)
    private boolean correct;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "questionId")
    private Question question;
}
