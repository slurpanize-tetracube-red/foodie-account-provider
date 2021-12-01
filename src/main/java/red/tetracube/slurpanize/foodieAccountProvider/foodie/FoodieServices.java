package red.tetracube.slurpanize.foodieAccountProvider.foodie;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.eventbus.EventBus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import red.tetracube.slurpanize.foodieAccountProvider.data.entities.Account;
import red.tetracube.slurpanize.foodieAccountProvider.data.repositories.AccountRepository;
import red.tetracube.slurpanize.foodieAccountProvider.data.repositories.RoleRepository;
import red.tetracube.slurpanize.foodieAccountProvider.enumerations.Roles;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.UUID;

@ApplicationScoped
public class FoodieServices {

    @Inject
    AccountRepository accountRepository;

    @Inject
    RoleRepository roleRepository;

    @Inject
    EventBus eventBus;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public Uni<Account> foodieSignUp(String username, String password) {
        var roleUni = this.roleRepository.getRoleByName(Roles.FOODIE);

        var newAccountUni = roleUni.map(role -> {
            var account = new Account();
            account.setId(UUID.randomUUID());
            account.setPassword(bCryptPasswordEncoder.encode(password));
            account.setUsername(username);
            account.setRole(role);
            return account;
        });

        return newAccountUni.call(newAccount -> this.accountRepository.saveAccount(newAccount))
                .invoke(newAccount -> this.eventBus.send("account-created", newAccount));
    }

}
