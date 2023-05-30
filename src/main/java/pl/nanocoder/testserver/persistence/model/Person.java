package pl.nanocoder.testserver.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.nanocoder.testserver.persistence.pojos.PersonType;
import pl.nanocoder.testserver.web.util.Views;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@JsonView({Views.PeopleAll.class, Views.QuizStudent.class})
@Entity
@Getter
@Setter
@NoArgsConstructor
@Data
@Table(schema = "people", name  = "people")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "people_seq_gen")
    @SequenceGenerator(name = "people_seq_gen", sequenceName = "people.people_seq_id", allocationSize = 1)
    private Long id;

    @JsonView(Views.PersonDetails.class)
    @Enumerated()
    @Column(nullable = false, columnDefinition = "int2")
    private PersonType type;

    @Column(nullable = false, length = 15)
    private String firstName;

    @Column(nullable = false, length = 20)
    private String lastName;

    @JsonView(Views.PersonDetails.class)
    @Column(nullable = false, length = 11)
    private String pesel;

    @JsonView(Views.PersonDetails.class)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createdAt;

    @JsonView(Views.PersonDetails.class)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date modifiedAt;

    @JsonView(Views.PersonDetails.class)
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(schema = "people", name = "groups_people", joinColumns = @JoinColumn(name = "personId"), inverseJoinColumns = @JoinColumn(name = "groupId"))
    private List<Group> groups;

    @JsonIgnore
    @OneToOne(mappedBy = "person", cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    private Login login;

    public Person(String firstName, String lastName, String pesel, Date createdAt, Date modifiedAt) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    @JsonIgnore
    public boolean isDeleted()
    {
        return deletedAt != null;
    }
}