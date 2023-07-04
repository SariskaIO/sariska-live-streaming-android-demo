package io.sariska.sariska_live_streaming_android_demo.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class StartLiveStreamApiCall {
    public interface liveStreamResponseCallback {
        void onResponse(String response);
        void onFailure(Throwable throwable);
    }
    public void startLiveStreaming(String apiUrl,String roomName,
                                   final StartLiveStreamApiCall.liveStreamResponseCallback callback){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(30, TimeUnit.SECONDS) // Set the connection timeout
                        .readTimeout(30, TimeUnit.SECONDS) // Set the read timeout
                        .build();

                MediaType mediaType = MediaType.parse("application/json");

                String requestBodyString = "{\"room_name\":\""+roomName+"\"}";

                RequestBody requestBody = RequestBody.create(mediaType, requestBodyString);

                GenerateToken httpClient = new GenerateToken();
                try {
                    httpClient.makeHttpRequest("https://api.sariska.io/api/v1/misc/generate-token", "asdasd", new GenerateToken.HttpRequestCallback() {
                        @Override
                        public void onResponse(String tokenresponse) throws JSONException {
                            String token = getTokenString(tokenresponse);
                            Request request = new Request.Builder()
                                    .url(apiUrl)
                                    .post(requestBody)
                                    .addHeader("Authorization", "Bearer " + token)
                                    .addHeader("Content-Type", "application/json")
                                    .build();
                            try {
                                Response response = client.newCall(request).execute();
                                if (response.isSuccessful()) {
                                    ResponseBody responses = response.body();
                                    String responseBody = response.body().string();
                                    // Process the response body
                                    callback.onResponse(responseBody);
                                } else {
                                    // Handle the error response
                                    callback.onFailure(new Throwable("Cannot complete API"));
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                // Handle the request failure
                                callback.onFailure(e);
                            }
                        }
                        @Override
                        public void onFailure(Throwable throwable) {

                        }
                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    private String getTokenString(String tokenresponse) throws JSONException {
        String responseString = tokenresponse;
        responseString = "[" + responseString + "]";
        JSONArray array = new JSONArray(responseString);
        String finalResponse = null;
        for(int i=0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            finalResponse = object.getString("token");
        }
        return finalResponse;
    }
}
