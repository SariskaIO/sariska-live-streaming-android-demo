package io.sariska.sariska_live_streaming_android_demo.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import io.sariska.sariska_live_streaming_android_demo.api.ApiInterface;
import io.sariska.sariska_live_streaming_android_demo.model.ApiResponse;
import io.sariska.sariska_live_streaming_android_demo.network.RetrofitClientInstance;
import io.sariska.sariska_live_streaming_android_demo.singleton.TokenManagerInstance;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StreamViewModel extends ViewModel {
    private MutableLiveData<ApiResponse> responseMutableLiveData;
    public LiveData<ApiResponse> getApiResponse(){
        return responseMutableLiveData;
    }
    public void loadApiResponse(){
        ApiInterface apiInterface = RetrofitClientInstance.getRetrofitInstance()
                .create(ApiInterface.class);
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("room_name", "randomroom");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create
                (MediaType.parse("application/json"), jsonBody.toString());

        // TODO: Add method to get token response
        apiInterface.startRecording
                ("Bearer "+ TokenManagerInstance.getInstance().getJwtToken(),
                        "application/json", requestBody).enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                        responseMutableLiveData.setValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable t) {
                        // TODO: Handle failure better
                        System.out.println("API call to live streaming failed");
                    }
                }
        );
    }
}
