package pl.nanocoder.testserver.persistence.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Data
@Table(schema = "login", name  = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_seq_gen")
    @SequenceGenerator(name = "roles_seq_gen", sequenceName = "login.roles_seq_id", allocationSize = 1)
    private Long id;

    @Column(nullable = false, length = 15, unique = true)
    private String name;

    @OneToMany(mappedBy = "role")
    private List<Login> logins;

    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(schema = "login", name = "roles_permissions",
            joinColumns = {@JoinColumn(name = "roleId")},
            inverseJoinColumns = {@JoinColumn(name = "permissionId")})
    private List<Permission> permissions;

    public Role(String name) {
        this.name = name;
    }
}
