package tetracubered.slurpanize.identityProviderApi.account

import tetracubered.slurpanize.identityProviderApi.account.payloads.CreateSuperAdminRequest
import javax.validation.Valid
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.core.Response

@Path("/account")
class AccountResource {

    @POST
    @Path("/create/super-admin")
    suspend fun createSuperAdmin(@Valid createSuperAdminRequest: CreateSuperAdminRequest): Response {
        return Response.ok().build()
    }
}