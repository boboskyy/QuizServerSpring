package pl.nanocoder.testserver.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
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
@Data
@Table(schema = "people", name  = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "groups_seq_gen")
    @SequenceGenerator(name = "groups_seq_gen", sequenceName = "people.groups_seq_id", allocationSize = 1)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date modifiedAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;

    @JsonIgnore
    @ManyToMany(mappedBy = "groups")
    private List<Person> people;

    public Group(String name, Date createdAt, Date modifiedAt) {
        this.name = name;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}