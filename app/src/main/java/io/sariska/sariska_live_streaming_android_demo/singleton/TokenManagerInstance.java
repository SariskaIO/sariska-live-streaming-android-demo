package io.sariska.sariska_live_streaming_android_demo.singleton;

import org.json.JSONException;
import org.json.JSONObject;

import io.sariska.sariska_live_streaming_android_demo.api.ApiInterface;
import io.sariska.sariska_live_streaming_android_demo.model.TokenResponse;
import io.sariska.sariska_live_streaming_android_demo.network.RetrofitClientInstance;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TokenManagerInstance {
    private static TokenManagerInstance tokenManagerInstance;
    private String jwtToken;
    private final ApiInterface apiInterface;
    public TokenManagerInstance() {
        apiInterface = RetrofitClientInstance.getRetrofitInstance().create(ApiInterface.class);
    }

    public static synchronized TokenManagerInstance getInstance(){
        if (tokenManagerInstance == null){
            tokenManagerInstance = new TokenManagerInstance();
        }
        return tokenManagerInstance;
    }

    public String getJwtToken(){
        return jwtToken;
    }

    public void refreshToken(CompletionHandler completionHandler){
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("apiKey", "{your-api-key}");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create
                (MediaType.parse("application/json"), jsonBody.toString());

        apiInterface.generateToken("application/json", requestBody)
                .enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    jwtToken = response.body().getToken(); // Assuming getToken() fetches the token from StartLiveStreamApiResponse
                    completionHandler.onSuccess(jwtToken);
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                // Handle failure
                completionHandler.onFailure(t);
            }
        });
    }
}
