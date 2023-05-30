package pl.nanocoder.testserver.persistence.model;

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
@Table(schema = "test", name = "quizzes")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quizzes_seq_id_gen")
    @SequenceGenerator(name = "quizzes_seq_id_gen", sequenceName = "test.quizzes_seq_id", allocationSize = 1)
    private Long id;

    @JsonView(Views.QuizStudent.class)
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "creatorId")
    private Person creator;

    @JsonView(Views.QuizTeacherPublic.class)
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE} , mappedBy = "quiz")
    private List<QGModule> questionModules;

    @Column(nullable = false, length = 25)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private short quizTime;

    public void setQuestionModules(List<QGModule> questionModules) {
        for(QGModule qgModule : questionModules)
            qgModule.setQuiz(this);
        this.questionModules = questionModules;
    }
}
