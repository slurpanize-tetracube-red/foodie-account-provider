package red.tetracube.slurpanize.foodieAccountProvider.foodie;

import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import red.tetracube.slurpanize.foodieAccountProvider.authorization.payloads.FoodieLoginResponse;
import red.tetracube.slurpanize.foodieAccountProvider.foodie.payloads.FoodieSignUpRequest;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Tag(
        name = "Foodie operations",
        description = "Foodie account related operations"
)
@Path("/foodie")
public class FoodieController {

    @Inject
    FoodieServices foodieServices;

    @Operation(
            description = "Foodie account creation",
            summary = "Endpoint to create account"
    )
    @APIResponses(
            value = {
                    @APIResponse(
                            description = "Account sign up success",
                            responseCode = "201"
                    ),
                    @APIResponse(
                            description = "Invalid request - missing some parameters",
                            responseCode = "400"
                    ),
                    @APIResponse(
                            description = "Username already taken",
                            responseCode = "409"
                    ),
            }
    )
    @Path("/sign-up")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> foodieSignUp(@Valid @RequestBody FoodieSignUpRequest request) {
        var accountCreatedResultUni = this.foodieServices.foodieSignUp(
                request.username,
                request.password
        );

        return accountCreatedResultUni.map(account ->
                Response.status(Response.Status.CREATED).build()
        );
    }

}
