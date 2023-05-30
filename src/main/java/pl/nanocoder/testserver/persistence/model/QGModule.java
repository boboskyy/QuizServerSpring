package pl.nanocoder.testserver.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.nanocoder.testserver.web.util.Views;

import javax.persistence.*;

@JsonView(Views.QuizTeacherPublic.class)
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(schema = "test", name = "qgmodules")
public class QGModule {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "questions_seq_gen")
    @SequenceGenerator(name = "questions_seq_gen", sequenceName = "test.questions_seq_id", allocationSize = 1)
    private Long id;

    @JsonIgnore
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "quizId")
    private Quiz quiz;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "questionGroupId")
    private QuestionGroup questionGroup;

    @Column(name = "genQuestionsCount",nullable = false)
    private int questionsToGenerate;

}
