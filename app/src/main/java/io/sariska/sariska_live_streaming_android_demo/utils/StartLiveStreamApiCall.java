package io.sariska.sariska_live_streaming_android_demo.utils;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class StartLiveStreamApiCall {
    public interface liveStreamResponseCallback {
        void onResponse(String response);
        void onFailure(Throwable throwable);
    }

    public void startLiveStreaming(String apiUrl,final StartLiveStreamApiCall.liveStreamResponseCallback callback){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                MediaType mediaType = MediaType.parse("application/json");
                String requestBodyString = "{\"room_name\":\"dipak\"}";
                RequestBody requestBody = RequestBody.create(mediaType, requestBodyString);
                String url = "https://api.sariska.io/terraform/v1/hooks/srs/startRecording";
                String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImRkMzc3ZDRjNTBiMDY1ODRmMGY4MDJhYmF" +
                        "iNTIyMjg5ODJiMTk2YzAzNzYwNzE4NDhiNWJlNTczN2JiMWYwYTUiLCJ0eXAiOiJKV1QifQ.eyJjb2" +
                        "50ZXh0Ijp7InVzZXIiOnsiaWQiOiJpZXJ6bnZuZiIsIm5hbWUiOiJlcXVhbF9hbnRlYXRlciJ9LCJncm91c" +
                        "CI6IjIwMiJ9LCJzdWIiOiJxd2ZzZDU3cHE5ZHhha3FxdXE2c2VxIiwicm9vbSI6IioiLCJpYXQiOjE2ODcxODA3" +
                        "MTYsIm5iZiI6MTY4NzE4MDcxNiwiaXNzIjoic2FyaXNrYSIsImF1ZCI6Im1lZGlhX21lc3NhZ2luZ19jby1icm93c2l" +
                        "uZyIsImV4cCI6MTY4NzI2NzExNn0.nkzx2A5f5VEz31QU2YrwNcmOl26mB032FHpCycyyJYIOAbsRtZYvhyvfFYq2UfXGc7P4d-2rUy7fve8NysVOxcCb1ZCEDZJIy4aRYJuZmpjaSv5TnhTPFiYvPidFdR3DZsoTq2WXQqoiSO5AMjQZtYuxtO_BMnnJqXKfefLbF3E6eSPQRnPRRQjlajGo-L_W1705zRl2HFdaIv57DQJuMrXKaxAXpaOBtyq1rfi0LavizIjHXf0OzDnOoW8Ifm34rNlVPHqxRYmMhpSGuRa7OTVrIwjNoyAoph9bBvnS5FiYOblJBd9PC22HO7L_wj8V-WYC7ww5Gf01g1ka6Vp86g";
                Request request = new Request.Builder()
                        .url(url)
                        .post(requestBody)
                        .addHeader("Authorization", "Bearer " + token)
                        .addHeader("Content-Type", "application/json")
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
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
        });
    }
}
