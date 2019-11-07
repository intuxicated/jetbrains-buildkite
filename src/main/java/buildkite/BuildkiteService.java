package buildkite;

import buildkite.response.PipelineResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import java.util.List;

import buildkite.response.AccessTokenResponse;
import buildkite.response.OrganizationResponse;
import retrofit2.http.Path;

public interface BuildkiteService {
    @GET("/v2/access-token")
    Call<AccessTokenResponse> getAccessToken();

    @GET("/v2/organizations")
    Call<List<OrganizationResponse>> getOrganizations();

    @GET("/v2/organizations/{slug}/pipelines")
    Call<List<PipelineResponse>> getPipelines(@Path("slug") String slug);

    static BuildkiteService getInstance(String token) {
        return BuildkiteServiceGenerator.createService(BuildkiteService.class, token);
    }
}
