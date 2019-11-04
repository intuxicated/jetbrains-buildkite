package buildkite;

import buildkite.response.AccessTokenResponse;
import buildkite.response.HelloWorldResponse;
import com.intellij.openapi.components.ServiceManager;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

public class BuildkiteClient {
    private BuildkiteService service = BuildkiteService.getInstance();

    public String getHelloWorld() throws IOException {
        Call<HelloWorldResponse> call = service.getHelloWorld();
        Response<HelloWorldResponse> response = call.execute();

        if (response.isSuccessful()) {
            HelloWorldResponse helloWorld = response.body();
            return helloWorld.getResponse();
        } else {
            return "";
        }
    }

    public AccessTokenResponse getAccessToken() throws IOException {
        Call<AccessTokenResponse> call = service.getAccessToken();
        Response<AccessTokenResponse> response = call.execute();
        if (response.isSuccessful()) {
            return response.body();
        }
        return null;
    }

    public static BuildkiteClient getInstance() {
        return ServiceManager.getService(BuildkiteClient.class);
    }
}
