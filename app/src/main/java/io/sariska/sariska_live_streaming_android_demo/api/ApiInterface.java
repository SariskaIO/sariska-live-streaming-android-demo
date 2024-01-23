package io.sariska.sariska_live_streaming_android_demo.api;

import io.sariska.sariska_live_streaming_android_demo.model.StartLiveStreamApiResponse;
import io.sariska.sariska_live_streaming_android_demo.model.StopLiveStreamApiRespose;
import io.sariska.sariska_live_streaming_android_demo.model.TokenResponse;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("terraform/v1/hooks/srs/startRecording")
    Call<StartLiveStreamApiResponse> startRecording(
            @Header("Authorization") String token,
            @Header("Content-Type") String contentType,
            @Body RequestBody body
    );

    @POST("terraform/v1/hooks/srs/stopRecording")
    Call<StopLiveStreamApiRespose> stopRecording(
            @Header("Authorization") String token,
            @Header("Content-Type") String contentType,
            @Body RequestBody body
    );

    @POST("api/v1/misc/generate-token")
    Call<TokenResponse> generateToken(
            @Header("Content-Type") String contentType,
            @Body RequestBody body
    );
}

