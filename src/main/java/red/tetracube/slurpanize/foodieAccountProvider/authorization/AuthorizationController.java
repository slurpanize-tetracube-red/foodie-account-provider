package red.tetracube.slurpanize.foodieAccountProvider.authorization;

import io.quarkus.security.UnauthorizedException;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import red.tetracube.slurpanize.foodieAccountProvider.authorization.payloads.FoodieLoginRequest;
import red.tetracube.slurpanize.foodieAccountProvider.authorization.payloads.FoodieLoginResponse;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Tag(
        name = "Authorization operations",
        description = "Allow foodies to login and logout with the platform"
)
@Path("/authorization")
public class AuthorizationController {

    @Inject
    AuthorizationServices authorizationServices;

    @Operation(
            description = "Foodie account login",
            summary = "Allow account to login getting access token"
    )
    @APIResponses(
            value = {
                    @APIResponse(
                            description = "Account login success",
                            responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON,
                                            schema = @Schema(implementation = FoodieLoginResponse.class)
                                    )
                            }
                    ),
                    @APIResponse(
                            description = "Invalid request - missing some parameters",
                            responseCode = "400"
                    ),
                    @APIResponse(
                            description = "Invalid credentials",
                            responseCode = "401"
                    ),
            }
    )
    @POST
    @Path("/sign-in")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> foodieSignIn(@Valid @RequestBody FoodieLoginRequest request) {
        var loginResultUni = this.authorizationServices.tryFoodieAccountLogin(request.username, request.password);
        var response = loginResultUni
                .map(loginResult -> {
                    var foodieLoginResponse = new FoodieLoginResponse();
                    foodieLoginResponse.token = loginResult;
                    return Response.ok(foodieLoginResponse).build();
                });

        return response.onFailure()
                .recoverWithItem(exception -> {
                    if (exception instanceof NotFoundException || exception instanceof UnauthorizedException) {
                        return Response.status(Response.Status.FORBIDDEN).build();
                    }
                    return Response.status(Response.Status.NOT_ACCEPTABLE).build();
                });
    }

}
