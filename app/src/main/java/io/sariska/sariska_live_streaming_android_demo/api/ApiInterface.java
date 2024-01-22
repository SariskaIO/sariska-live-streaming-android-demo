package io.sariska.sariska_live_streaming_android_demo.api;

import com.google.android.gms.common.api.Api;

import io.sariska.sariska_live_streaming_android_demo.model.ApiResponse;
import io.sariska.sariska_live_streaming_android_demo.model.TokenResponse;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("terraform/v1/hooks/srs/startRecording")
    Call<ApiResponse> startRecording(
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

