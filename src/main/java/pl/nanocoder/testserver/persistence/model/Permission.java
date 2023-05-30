package pl.nanocoder.testserver.persistence.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(schema = "login", name = "permissions")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permissions_seq_gen")
    @SequenceGenerator(name = "permissions_seq_gen", sequenceName = "login.permissions_seq_id", allocationSize = 1)
    private Long id;

    @Column(nullable = false, length = 30)
    private String groupName;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String permission;

    public Permission(String groupName, String name, String permission) {
        this.groupName = groupName;
        this.name = name;
        this.permission = permission;
    }
}
