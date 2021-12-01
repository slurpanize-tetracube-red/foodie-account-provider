package red.tetracube.slurpanize.foodieAccountProvider.authorization.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.smallrye.common.constraint.NotNull;

import javax.validation.constraints.NotEmpty;

public class FoodieLoginRequest {

    @NotNull
    @NotEmpty
    @JsonProperty("username")
    public String username;

    @NotNull
    @NotEmpty
    @JsonProperty("password")
    public String password;

}
