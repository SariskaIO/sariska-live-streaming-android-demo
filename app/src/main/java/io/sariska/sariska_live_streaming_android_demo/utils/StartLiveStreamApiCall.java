package io.sariska.sariska_live_streaming_android_demo.utils;

import org.json.JSONException;

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
        void onResponse(String response) throws JSONException;
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

                String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjY4ZDI0MGNjZThjNTlmOGQ1Yjk3MGY5ZTA4OGM1ZWQyYzAzOTllYWFlODZjZTc3ZDhlY2MyZDBkMTRlNzk0YTgiLCJ0eXAiOiJKV1QifQ.eyJjb250ZXh0Ijp7InVzZXIiOnsiaWQiOiJzbmhndG8wbiIsIm5hbWUiOiJjb29sX3NhaWxmaXNoIn0sImdyb3VwIjoiMSJ9LCJzdWIiOiJrZGJ4YzhvYXlneGV2YW1vYmx1eG96Iiwicm9vbSI6IioiLCJpYXQiOjE2OTg3NTU4NDIsIm5iZiI6MTY5ODc1NTg0MiwiaXNzIjoic2FyaXNrYSIsImF1ZCI6Im1lZGlhX21lc3NhZ2luZ19jby1icm93c2luZyIsImV4cCI6MTY5ODg0MjI0Mn0.cAxt6pfJUPH14NC4jjDjg8lYiictTU2MpY0l2JpIiVSDWx__kL6BSquAQ7n9XncxjtSs_xajnNjf_u-EWb3z7v3fh-cg_O3Pplip77thFjYZac35xuMfDK8KJLUEgWYy26lqGXvOK7HtB7vuELRMJ-F2geG32qzCp75rhUStT7wTJrAuxpWaWwCbnTdaPinpCtu9M2xrOb0rvUJ8Z4R3tAkR3n8xCyQ9mxIk8WvOjGIxM7nOPEo80xZf5TWR_8Qw24-Y4nWYBg2Wdw2-0xx57r37g5KfXgUuJtFvcYvfqt_IcTiOVmUSkqb2eyQUPTr8iAYzz9Gzk1I1L6ssai9huQ";

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
                        System.out.println("responce body"+ responseBody);
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
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}
