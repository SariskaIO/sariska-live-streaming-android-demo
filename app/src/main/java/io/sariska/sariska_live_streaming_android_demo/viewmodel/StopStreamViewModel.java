package io.sariska.sariska_live_streaming_android_demo.viewmodel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import org.json.JSONException;
import org.json.JSONObject;
import io.sariska.sariska_live_streaming_android_demo.api.ApiInterface;
import io.sariska.sariska_live_streaming_android_demo.model.StopLiveStreamApiRespose;
import io.sariska.sariska_live_streaming_android_demo.network.RetrofitClientInstance;
import io.sariska.sariska_live_streaming_android_demo.singleton.StopLiveStreamCompletionHandler;
import io.sariska.sariska_live_streaming_android_demo.singleton.TokenManagerInstance;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StopStreamViewModel extends ViewModel {
    private MutableLiveData<StopLiveStreamApiRespose> responseMutableLiveData;
    public LiveData<StopLiveStreamApiRespose> getApiResponse(){
        return responseMutableLiveData;
    }
    public void loadApiResponse(String roomName, StopLiveStreamCompletionHandler completionHandler){
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
        apiInterface.stopRecording("Bearer "+ TokenManagerInstance.getInstance().getJwtToken()
                        , "application/json", requestBody).enqueue(new Callback<StopLiveStreamApiRespose>() {
               @Override
               public void onResponse(Call<StopLiveStreamApiRespose> call, Response<StopLiveStreamApiRespose> response) {
                   completionHandler.onSuccess(response.body().isStarted());
               }

               @Override
               public void onFailure(Call<StopLiveStreamApiRespose> call, Throwable t) {
                   // TODO: Handle failure better
                   completionHandler.onFailure(t);
               }
            }
        );
    }
}
