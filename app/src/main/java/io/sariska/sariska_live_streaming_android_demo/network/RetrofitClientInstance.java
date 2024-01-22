package io.sariska.sariska_live_streaming_android_demo.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {

    // Static retrofit client used for making api calls to start recording
    private static Retrofit retrofit;
    // Base URL.
    // TODO: Later move to env variables in order to test in dev
    private static final String BASE_URL = "https://api.sariska.io/";

    // Return a retrofit client
    public static Retrofit getRetrofitInstance(){
        if (retrofit == null){
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS) // Set the connection timeout
                    .readTimeout(30, TimeUnit.SECONDS) // Set the read timeout
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}


