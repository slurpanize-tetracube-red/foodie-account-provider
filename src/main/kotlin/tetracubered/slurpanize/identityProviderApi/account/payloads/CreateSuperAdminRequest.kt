package tetracubered.slurpanize.identityProviderApi.account.payloads

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class CreateSuperAdminRequest @JsonCreator constructor(
    @field:NotNull
    @field:NotEmpty
    @JsonProperty("username")
    val username: String? = null,

    @field:NotNull
    @field:NotEmpty
    @JsonProperty("password")
    val password: String? = null
)