package red.tetracube.slurpanize.foodieAccountProvider.data.repositories;

import io.smallrye.mutiny.Uni;
import org.hibernate.reactive.mutiny.Mutiny;
import red.tetracube.slurpanize.foodieAccountProvider.data.entities.Account;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class AccountRepository {

    @Inject
    Mutiny.Session rxSession;

    public Uni<Account> getAccountByUsername(String username) {
        return this.rxSession.createQuery("""
                                from Account account
                                inner join fetch account.role role
                                inner join fetch role.roleCapabilities capabilties
                                inner join fetch capabilties.capability
                                where account.username = :username
                                """,
                        Account.class)
                .setParameter("username", username)
                .setMaxResults(1)
                .getSingleResultOrNull()
                .call(ignored -> this.rxSession.flush());
    }

}
