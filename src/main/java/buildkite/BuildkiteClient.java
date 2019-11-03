package buildkite;

import buildkite.response.HelloWorldResponse;
import com.intellij.openapi.components.ServiceManager;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

public class BuildkiteClient {

    public String getHelloWorld() throws IOException {
        BuildkiteService service = BuildkiteService.getInstance();

        Call<HelloWorldResponse> call = service.getHelloWorld();
        Response<HelloWorldResponse> response = call.execute();

        if (response.isSuccessful()) {
            HelloWorldResponse helloWorld = response.body();
            return helloWorld.getResponse();
        } else {
            return "";
        }
    }

    public static BuildkiteClient getInstance() {
        return ServiceManager.getService(BuildkiteClient.class);
    }
}
