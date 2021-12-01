package red.tetracube.slurpanize.foodieAccountProvider.authorization.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FoodieLoginResponse {

    @JsonProperty("token")
    public String token;

}
