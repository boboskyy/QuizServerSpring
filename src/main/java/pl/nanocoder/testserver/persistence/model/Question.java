package pl.nanocoder.testserver.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.nanocoder.testserver.web.util.Views;

import javax.persistence.*;
import java.util.List;

@JsonView(Views.QuizStudent.class)
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(schema = "test", name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "questions_seq_gen")
    @SequenceGenerator(name = "questions_seq_gen", sequenceName = "test.questions_seq_id", allocationSize = 1)
    private Long id;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "groupId")
    private QuestionGroup group;

    @Column(nullable = false)
    private String value;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST},mappedBy = "question")
    private List<Answer> answers;

    public void setAnswers(List<Answer> answers) {
        for(Answer answer : answers)
            answer.setQuestion(this);
        this.answers = answers;
    }
}
