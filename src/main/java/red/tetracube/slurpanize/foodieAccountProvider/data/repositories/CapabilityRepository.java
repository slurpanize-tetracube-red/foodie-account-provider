package red.tetracube.slurpanize.foodieAccountProvider.data.repositories;

import io.smallrye.mutiny.Uni;
import org.hibernate.reactive.mutiny.Mutiny;
import red.tetracube.slurpanize.foodieAccountProvider.data.entities.Capability;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class CapabilityRepository {

    @Inject
    Mutiny.Session rxSession;

    public Uni<List<Capability>> getRoleCapabilities(UUID roleId) {
        return this.rxSession.createQuery("""
                from Capability capability
                inner join capability.roleCapabilities capabilities
                where capabilities.role.id = :roleId
                """,
                Capability.class)
                .setParameter("roleId", roleId)
                .getResultList()
                .call(ignored -> this.rxSession.flush());
    }

}
