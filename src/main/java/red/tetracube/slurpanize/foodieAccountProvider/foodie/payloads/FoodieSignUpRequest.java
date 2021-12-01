package red.tetracube.slurpanize.foodieAccountProvider.foodie.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class FoodieSignUpRequest {

    @NotEmpty
    @NotNull
    @JsonProperty("username")
    public String username;

    @NotEmpty
    @NotNull
    @JsonProperty("password")
    public String password;

    @NotNull
    @NotEmpty
    @JsonProperty("passwordConfirm")
    public String passwordConfirm;

}
