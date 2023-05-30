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
@Table(schema = "login", name = "logins")
public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "logins_seq_gen")
    @SequenceGenerator(name = "logins_seq_gen", sequenceName = "login.logins_seq_id", allocationSize = 1)
    private Long id;

    @JsonIgnore
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "personId")
    private Person person;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "roleId")
    private Role role;

    @Column(nullable = false, length = 15)
    private String login;

    @JsonIgnore
    @Column(nullable = false, length = 65)
    private String password;

    @JsonIgnore
    private boolean resetPassword;

    public Login(Person person, Role role, String login, String password) {
        this.person = person;
        this.role = role;
        this.login = login;
        this.password = password;
    }


}