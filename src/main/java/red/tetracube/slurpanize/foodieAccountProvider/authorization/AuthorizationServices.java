package red.tetracube.slurpanize.foodieAccountProvider.authorization;

import io.quarkus.security.UnauthorizedException;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import red.tetracube.slurpanize.foodieAccountProvider.data.entities.Capability;
import red.tetracube.slurpanize.foodieAccountProvider.data.entities.RoleCapability;
import red.tetracube.slurpanize.foodieAccountProvider.data.repositories.AccountRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import java.util.stream.Collectors;

@ApplicationScoped
public class AuthorizationServices {

    @Inject
    AccountRepository accountRepository;

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    String jwtIssuer;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Uni<String> tryFoodieAccountLogin(String username, String password) {
        var accountUni = this.accountRepository.getAccountByUsername(username);
        var checkAccountUni = accountUni.map(Unchecked.function(account -> {
            if (account == null) {
                throw new NotFoundException("Cannot find any account with given username");
            }
            if (!this.passwordEncoder.matches(password, account.getPassword())) {
                throw new UnauthorizedException("Invalid credentials");
            }
            return account;
        }));
        return checkAccountUni
                .map(account -> {
                    var capabilities = account.getRole().getRoleCapabilities().stream()
                            .map(RoleCapability::getCapability)
                            .map(Capability::getName)
                            .collect(Collectors.toSet());
                    return Jwt.issuer(this.jwtIssuer)
                            .upn(account.getUsername())
                            .subject(account.getUsername())
                            .groups(capabilities)
                            .sign();
                });
    }

}
