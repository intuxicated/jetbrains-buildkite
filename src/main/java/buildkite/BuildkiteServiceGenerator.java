package buildkite;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import org.jetbrains.annotations.Nullable;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

class BuildkiteServiceGenerator {
    private final static String BUILDKITE_API_URL = "https://api.buildkite.com";

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(BUILDKITE_API_URL)
            .addConverterFactory(JacksonConverterFactory.create());

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC);

    public static <S> S createService(Class<S> serviceClass, @Nullable String token) {
        httpClient.interceptors().clear();
        httpClient.addInterceptor(logging);
        if (!token.equals("")) {
            httpClient.addInterceptor(chain -> {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("Authorization", "Bearer " + token)
                        .build();
                return chain.proceed(request);
            });
        }
        builder.client(httpClient.build());
        Retrofit retrofit = builder.build();
        return retrofit.create(serviceClass);
    }
}
