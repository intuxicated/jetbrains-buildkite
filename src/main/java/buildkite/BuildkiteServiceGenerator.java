package buildkite;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import org.jetbrains.annotations.Nullable;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import settings.BuildkiteSettings;

public class BuildkiteServiceGenerator {
    final static String BUILDKITE_API_URL = "https://api.buildkite.com";

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(BUILDKITE_API_URL)
            .addConverterFactory(JacksonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static BuildkiteSettings buildkiteSettings = BuildkiteSettings.getInstance();

    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC);

    public static <S> S createService(Class<S> serviceClass, @Nullable String token) {
        if (token == null) {
            token = buildkiteSettings.getAccessTokenAPI();
        }
        String finalToken = token;
        httpClient.interceptors().clear();
        httpClient.addInterceptor(logging);
        if (!finalToken.equals("")) {
            httpClient.addInterceptor(chain -> {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("Authorization", "Bearer " + finalToken)
                        .build();
                return chain.proceed(request);
            });
        }
        builder.client(httpClient.build());
        retrofit = builder.build();
        return retrofit.create(serviceClass);
    }
}
