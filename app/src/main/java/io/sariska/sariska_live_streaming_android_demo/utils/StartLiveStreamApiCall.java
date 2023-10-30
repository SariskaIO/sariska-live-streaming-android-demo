package io.sariska.sariska_live_streaming_android_demo.utils;

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
                        .connectTimeout(10, TimeUnit.SECONDS) // Set the connection timeout
                        .readTimeout(30, TimeUnit.SECONDS) // Set the read timeout
                        .build();

                MediaType mediaType = MediaType.parse("application/json");

                String requestBodyString = "{\"room_name\":\""+roomName+"\"}";

                RequestBody requestBody = RequestBody.create(mediaType, requestBodyString);

                String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjY4ZDI0MGNjZThjNTlmOGQ1Yjk3MGY5ZTA4OGM1ZWQyYzAzOTllYWFlODZjZTc3ZDhlY2MyZDBkMTRlNzk0YTgiLCJ0eXAiOiJKV1QifQ.eyJjb250ZXh0Ijp7InVzZXIiOnsiaWQiOiJ3cGNiaXU5cSIsIm5hbWUiOiJ0cm9waWNhbF9wZWFjb2NrIn0sImdyb3VwIjoiMSJ9LCJzdWIiOiJrZGJ4YzhvYXlneGV2YW1vYmx1eG96Iiwicm9vbSI6IioiLCJpYXQiOjE2OTg2NTg2OTMsIm5iZiI6MTY5ODY1ODY5MywiaXNzIjoic2FyaXNrYSIsImF1ZCI6Im1lZGlhX21lc3NhZ2luZ19jby1icm93c2luZyIsImV4cCI6MTY5ODc0NTA5M30.CZzUtYd9oupG4G84szy6OYD-vRHjZsb7zMEGV71HRNlLwuqdsmks-7X6zJeoCREC809ZpRuj3K-NwdA5YpF-oo9aTwuBZ_jnubm26YsZN9N39i5oVimLRPN-nIMFnNWC7WT11lIPwS6YhBeQOwXW8WvS9-H2nHeeod9yRp4j2dPAvKvSg3yIGNpnI5LM2YFZwQrraVacorNaO0nl2-cssM6fAsi5Ik3_tA9UqBsLrlfVbmYVlkP1V-tQ8ttZLaNMUJ6p1ziFs_IK7PLa2yrWCxTu6Ll9z4RcXBklRV6F69gVUYK3rsvdihN-_pGtc5jXKNjsIfDK8W9C8WN8QUqgjQ";

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
        }).start();
    }
}
