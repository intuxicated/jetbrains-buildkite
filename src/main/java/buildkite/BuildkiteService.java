package buildkite;

import buildkite.response.HelloWorldResponse;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.http.GET;

public interface BuildkiteService {
    final String url = "https://api.buildkite.com";

    @GET("/")
    Call<HelloWorldResponse> getHelloWorld();

    public static BuildkiteService getInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        return retrofit.create(BuildkiteService.class);
    }
}
