package tetracubered.slurpanize.identityProviderApi.authorization.payloads

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class GetAuthorizationTokenRequest @JsonCreator constructor(
    @field:NotEmpty
    @field:NotNull
    @JsonProperty("username")
    val username: String? = null,

    @field:NotEmpty
    @field:NotNull
    @JsonProperty("password")
    val password: String? = null
)
