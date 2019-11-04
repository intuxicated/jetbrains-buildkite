package buildkite;

import buildkite.response.AccessTokenResponse;
import buildkite.response.HelloWorldResponse;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface BuildkiteService {
    @GET("/")
    Call<HelloWorldResponse> getHelloWorld();

    @GET("/v2/access-token")
    Call<AccessTokenResponse> getAccessToken();

    public static BuildkiteService getInstance() {
        return BuildkiteServiceGenerator.createService(BuildkiteService.class, null);
    }

    public static BuildkiteService getInstance(String token) {
        return BuildkiteServiceGenerator.createService(BuildkiteService.class, token);
    }
}
