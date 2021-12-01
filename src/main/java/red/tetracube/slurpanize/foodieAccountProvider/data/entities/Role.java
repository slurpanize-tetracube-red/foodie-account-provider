package red.tetracube.slurpanize.foodieAccountProvider.data.entities;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Entity
@Table(schema = "identity_provider", name = "roles")
public class Role {

    @Id
    private UUID id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @OneToMany(targetEntity = RoleCapability.class, fetch = FetchType.LAZY, mappedBy = "role")
    private List<RoleCapability> roleCapabilities = Collections.emptyList();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<RoleCapability> getRoleCapabilities() {
        return roleCapabilities;
    }

    public void setRoleCapabilities(List<RoleCapability> roleCapabilities) {
        this.roleCapabilities = roleCapabilities;
    }

}
