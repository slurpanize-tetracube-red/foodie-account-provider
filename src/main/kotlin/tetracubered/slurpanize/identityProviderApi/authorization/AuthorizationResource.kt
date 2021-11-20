package tetracubered.slurpanize.identityProviderApi.authorization

import io.smallrye.jwt.build.Jwt
import org.eclipse.microprofile.jwt.Claims
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody
import tetracubered.slurpanize.identityProviderApi.authorization.payloads.GetAuthorizationTokenRequest
import javax.validation.Valid
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.core.Response


@Path("/authorize")
class AuthorizationResource {

    @POST
    @Path("")
    fun getAuthorizationToken(@Valid @RequestBody getAuthorizationTokenRequest: GetAuthorizationTokenRequest): Response {
        val token: String = Jwt.issuer("https://example.com/issuer")
            .upn("jdoe@quarkus.io")
            .groups(HashSet<String>(listOf("User", "Admin")))
            .claim(Claims.birthdate.name, "2001-07-13")
            .sign()
        return Response.ok(token).build()
    }
}