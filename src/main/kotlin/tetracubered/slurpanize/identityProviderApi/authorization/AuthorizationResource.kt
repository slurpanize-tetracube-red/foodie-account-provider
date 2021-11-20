package tetracubered.slurpanize.identityProviderApi.authorization

import io.smallrye.jwt.build.Jwt
import org.eclipse.microprofile.jwt.Claims
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.core.Response


@Path("/authorize")
class AuthorizationResource {

    @GET
    @Path("")
    fun getAuthorizationToken(): Response {
        val token: String = Jwt.issuer("https://example.com/issuer")
            .upn("jdoe@quarkus.io")
            .groups(HashSet<String>(listOf("User", "Admin")))
            .claim(Claims.birthdate.name, "2001-07-13")
            .sign()
        return Response.ok(token).build()
    }
}