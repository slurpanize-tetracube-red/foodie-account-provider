package red.tetracube.slurpanize.foodieAccountProvider.data.repositories;

import io.smallrye.mutiny.Uni;
import org.hibernate.reactive.mutiny.Mutiny;
import red.tetracube.slurpanize.foodieAccountProvider.data.entities.Role;
import red.tetracube.slurpanize.foodieAccountProvider.enumerations.Roles;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class RoleRepository {

    @Inject
    Mutiny.Session rxSession;

    public Uni<Role> getRoleByName(Roles roleName) {
        return this.rxSession.createQuery("""
                                from Role role
                                where role.name = :roleName
                                """,
                        Role.class)
                .setParameter("roleName", roleName)
                .setMaxResults(1)
                .getSingleResult()
                .call(ignored -> this.rxSession.flush());
    }

    public Uni<Boolean> existsAnyAccountInRole(Roles roleName) {
        return this.rxSession.createQuery("""
                            from Role role
                            join fetch role.accounts accounts
                            where role.name = :roleName
                        """)
                .setParameter("roleName", roleName)
                .getResultList()
                .map(accountsInRole -> !accountsInRole.isEmpty())
                .call(ignored -> this.rxSession.flush());
    }

}
