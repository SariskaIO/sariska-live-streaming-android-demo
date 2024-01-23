package io.sariska.sariska_live_streaming_android_demo.viewmodel;

import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import io.sariska.sariska_live_streaming_android_demo.api.ApiInterface;
import io.sariska.sariska_live_streaming_android_demo.model.StartLiveStreamApiResponse;
import io.sariska.sariska_live_streaming_android_demo.network.RetrofitClientInstance;
import io.sariska.sariska_live_streaming_android_demo.singleton.StartLiveStreamCompletionHandler;
import io.sariska.sariska_live_streaming_android_demo.singleton.TokenManagerInstance;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StartStreamViewModel extends ViewModel {
    private MutableLiveData<StartLiveStreamApiResponse> responseMutableLiveData;
    public LiveData<StartLiveStreamApiResponse> getApiResponse(){
        return responseMutableLiveData;
    }
    public void loadApiResponse(String roomName, TextView someViewText, StartLiveStreamCompletionHandler completionHandler){
        ApiInterface apiInterface = RetrofitClientInstance.getRetrofitInstance()
                .create(ApiInterface.class);

        JSONObject jsonBody = new JSONObject();

        try {
            jsonBody.put("room_name", roomName);
            jsonBody.put("layout", "mobile");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create
                (MediaType.parse("application/json"), jsonBody.toString());

        // TODO: Add method to get token response
        apiInterface.startRecording
                ("Bearer "+ TokenManagerInstance.getInstance().getJwtToken()
                        , "application/json", requestBody).enqueue(new Callback<StartLiveStreamApiResponse>() {
                    @Override
                    public void onResponse(Call<StartLiveStreamApiResponse> call, Response<StartLiveStreamApiResponse> response) {
                        String hls_url = response.body().getHls_url();
                        someViewText.setText(hls_url);
                        completionHandler.onSuccess(hls_url);
                    }

                    @Override
                    public void onFailure(Call<StartLiveStreamApiResponse> call, Throwable t) {
                        // TODO: Handle failure better
                        completionHandler.onFailure(t);
                    }
                }
        );
    }
}
