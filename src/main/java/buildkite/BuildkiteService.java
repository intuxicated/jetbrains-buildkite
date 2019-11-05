package buildkite;

import retrofit2.Call;
import retrofit2.http.GET;
import java.util.List;

import buildkite.response.AccessTokenResponse;
import buildkite.response.HelloWorldResponse;
import buildkite.response.OrganizationResponse;

public interface BuildkiteService {
    @GET("/")
    Call<HelloWorldResponse> getHelloWorld();

    @GET("/v2/access-token")
    Call<AccessTokenResponse> getAccessToken();

    @GET("/v2/organizations")
    Call<List<OrganizationResponse>> getOrganizations();

    public static BuildkiteService getInstance() {
        return BuildkiteServiceGenerator.createService(BuildkiteService.class, null);
    }

    public static BuildkiteService getInstance(String token) {
        return BuildkiteServiceGenerator.createService(BuildkiteService.class, token);
    }
}
