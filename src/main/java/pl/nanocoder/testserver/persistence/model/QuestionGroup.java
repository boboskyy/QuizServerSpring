package pl.nanocoder.testserver.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(schema = "test", name = "question_groups")
public class QuestionGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_groups_seq_gen")
    @SequenceGenerator(name = "question_groups_seq_gen", sequenceName = "test.question_groups_seq_id", allocationSize = 1)
    private Long id;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "creatorId")
    private Person creator;

    @Column(nullable = false, length = 25)
    private String name;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST},mappedBy = "group")
    private List<Question> questions;

    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;

    public void setQuestions(List<Question> questions) {
        for(Question question : questions)
            question.setGroup(this);
        this.questions = questions;
    }

    public void addQuestion(Question question)
    {
        question.setGroup(this);
        this.questions.add(question);
    }
}
