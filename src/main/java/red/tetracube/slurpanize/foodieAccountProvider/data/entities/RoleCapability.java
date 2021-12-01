package red.tetracube.slurpanize.foodieAccountProvider.data.entities;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(schema = "identity_provider", name = "roles_capabilities")
public class RoleCapability {

    @Id
    private UUID id;

    @JoinColumn(name = "role_id", nullable = false)
    @ManyToOne(targetEntity = Role.class, fetch = FetchType.LAZY)
    private Role role;

    @JoinColumn(name = "capability_id", nullable = false)
    @ManyToOne(targetEntity = Capability.class, fetch = FetchType.LAZY)
    private Capability capability;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Capability getCapability() {
        return capability;
    }

    public void setCapability(Capability capability) {
        this.capability = capability;
    }

}
