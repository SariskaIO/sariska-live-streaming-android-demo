package io.sariska.sariska_live_streaming_android_demo.utils;

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
import okhttp3.logging.HttpLoggingInterceptor;


public class StartLiveStreamApiCall {
    public interface liveStreamResponseCallback {
        void onResponse(String response) throws JSONException;
        void onFailure(Throwable throwable);
    }
    public void startLiveStreaming(String apiUrl,String roomName,
                                   final StartLiveStreamApiCall.liveStreamResponseCallback callback){

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(30, TimeUnit.SECONDS) // Set the connection timeout
                        .readTimeout(30, TimeUnit.SECONDS) // Set the read timeout
                        .writeTimeout(30, TimeUnit.SECONDS)
                        .addInterceptor(loggingInterceptor)
                        .build();

                MediaType mediaType = MediaType.parse("application/json");

                JSONObject requestBodyJson = new JSONObject();
                try {
                    requestBodyJson.put("room_name", roomName);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                try {
                    requestBodyJson.put("is_low_latency", true);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                System.out.println(requestBodyJson);
                String jsonData = "{\"room_name\":\""+roomName+"\"}";
                String requestBodyString = jsonData;

                RequestBody requestBody = RequestBody.create(mediaType, requestBodyString);

                String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjY4ZDI0MGNjZThjNTlmOGQ1Yjk3MGY5ZTA4OGM1ZWQyYzAzOTllYWFlODZjZTc3ZDhlY2MyZDBkMTRlNzk0YTgiLCJ0eXAiOiJKV1QifQ.eyJjb250ZXh0Ijp7InVzZXIiOnsiaWQiOiJpdGtuN2k5aSIsIm5hbWUiOiJtZWFuX2xvY3VzdCJ9LCJncm91cCI6IjEifSwic3ViIjoia2RieGM4b2F5Z3hldmFtb2JsdXhveiIsInJvb20iOiIqIiwiaWF0IjoxNzA0ODc4NTk0LCJuYmYiOjE3MDQ4Nzg1OTQsImlzcyI6InNhcmlza2EiLCJhdWQiOiJtZWRpYV9tZXNzYWdpbmdfY28tYnJvd3NpbmciLCJleHAiOjE3MDQ5NjQ5OTR9.ykh1Y9l5LolXwHss-ABqtOU78ZhHX7_s0nU7E0H9n59ZQSLbDVbwksMLLAFK4LmEjEbVNTc_l8-uhf-d9sClhr7hpWduWqisNF0WUp5Sc3dZfIOOlkoWFr1Q5losFOlDu-xthVE2ODLY_4GekEHCbhZ9YK7XWza1D-XfUTil0XuJLMLb6giCy0stEurPn4uKdI_Hl5XGUq3_6tnw7wtNu7SjAb8I3QS2oEzKe7bQyCwykuPokh7qWhTLBwBZ5RYX3y8oco3VgA4hQi2RbIAk-PqYKTO9_TCVYKxbUdcPc5onN0P70suS3YXT4r-NWg1Fnb4W2mqT8-hE3Y73FoWKrQ";

                Request request = new Request.Builder()
                        .url(apiUrl)
                        .addHeader("Authorization", "Bearer " + token)
                        .addHeader("Content-Type", "application/json")
                        .post(requestBody)
                        .build();

                System.out.println(request);
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
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}
