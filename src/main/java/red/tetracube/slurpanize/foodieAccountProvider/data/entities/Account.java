package red.tetracube.slurpanize.foodieAccountProvider.data.entities;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(schema = "identity_provider", name = "accounts")
public class Account {

    @Id
    private UUID id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @JoinColumn(name = "role_id", nullable = false)
    @ManyToOne(targetEntity = Role.class, fetch = FetchType.LAZY)
    private Role role;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
