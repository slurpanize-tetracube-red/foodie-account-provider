package red.tetracube.slurpanize.foodieAccountProvider.data.services;

import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.mutiny.Uni;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import red.tetracube.slurpanize.foodieAccountProvider.data.entities.Account;
import red.tetracube.slurpanize.foodieAccountProvider.data.repositories.AccountRepository;
import red.tetracube.slurpanize.foodieAccountProvider.data.repositories.RoleRepository;
import red.tetracube.slurpanize.foodieAccountProvider.enumerations.Roles;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class FoodieDataServices {

    @Inject
    RoleRepository roleRepository;

    @Inject
    AccountRepository accountRepository;

    private final static Logger LOGGER = LoggerFactory.getLogger(FoodieDataServices.class);

    @ConsumeEvent("account-created")
    public void foodieShouldbeSuperAdmin(Account account) {
        var accountsInRoleUni = this.roleRepository.existsAnyAccountInRole(Roles.ADMINISTRATOR);
        var roleToAssignUni = accountsInRoleUni.flatMap(accountsInRole -> {
            if (accountsInRole) {
                return Uni.createFrom().nullItem();
            }

            return this.roleRepository.getRoleByName(Roles.ADMINISTRATOR);
        });

        var updatedAccountUni = roleToAssignUni
                .onItem()
                .ifNotNull()
                .transformToUni(role -> {
                    account.setRole(role);
                    return this.accountRepository.updateAccount(account);
                });

        updatedAccountUni.subscribe()
                .with(updatedAccount -> {
                    LOGGER.info("Account {} upgraded as super admin", updatedAccount.getUsername());
                });
    }
}
