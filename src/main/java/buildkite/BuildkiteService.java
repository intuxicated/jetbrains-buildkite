package buildkite;

import buildkite.request.BuildRequest;
import buildkite.response.BuildResponse;
import buildkite.response.PipelineResponse;
import buildkite.response.AccessTokenResponse;
import buildkite.response.OrganizationResponse;

import retrofit2.Call;
import java.util.List;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BuildkiteService {
    @GET("/v2/access-token")
    Call<AccessTokenResponse> getAccessToken();

    @GET("/v2/organizations")
    Call<List<OrganizationResponse>> getOrganizations();

    @GET("/v2/organizations/{organizationSlug}/pipelines")
    Call<List<PipelineResponse>> getPipelines(@Path("organizationSlug") String organizationSlug);

    @POST("/v2/organizations/{organizationSlug}/pipelines/{pipelineSlug}/builds")
    Call<BuildResponse> createBuild(
            @Path("organizationSlug") String organizationSlug,
            @Path("pipelineSlug") String pipelineSlug,
            @Body BuildRequest buildRequest
    );

    static BuildkiteService getInstance(String token) {
        return BuildkiteServiceGenerator.createService(BuildkiteService.class, token);
    }
}
