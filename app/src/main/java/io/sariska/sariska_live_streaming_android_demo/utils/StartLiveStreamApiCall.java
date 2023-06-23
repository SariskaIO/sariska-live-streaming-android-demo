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

                String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImRkMzc3ZDRjNTBiMDY1ODRmMGY4MDJhYmFiNTIyMjg5ODJiMTk2YzAzNzYwNzE4NDhiNWJlNTczN2JiMWYwYTUiLCJ0eXAiOiJKV1QifQ.eyJjb250ZXh0Ijp7InVzZXIiOnsiaWQiOiJtemsyamt3cCIsIm5hbWUiOiJwcm9wb3NlZF9nb2F0In0sImdyb3VwIjoiMjAyIn0sInN1YiI6InF3ZnNkNTdwcTlkeGFrcXF1cTZzZXEiLCJyb29tIjoiKiIsImlhdCI6MTY4NzUxODA1MSwibmJmIjoxNjg3NTE4MDUxLCJpc3MiOiJzYXJpc2thIiwiYXVkIjoibWVkaWFfbWVzc2FnaW5nX2NvLWJyb3dzaW5nIiwiZXhwIjoxNjg3NjA0NDUxfQ.uTKJu3NQBpQUj93_eD7ly2rsSVT9dsZItzUwLJto9NEBaLxNEJMI_kbktnTSZ9ReW1cI43dXWFFoT4Du_q-mXPESEDG5_7EFKiOmF1tVD_00hNekWJrq7bU01-y3yogsxi4HyI_9H3-HNM5qP1eL0vtqRxDI9x91Irvc6CoiPfYvBYWPhxXjI9AAGkzZ2BX6M4VUBDUi_BjEbIlzRwld78IRcTc8ldWo38_MQmmzp5C6pzBL8PQcV21NdjRWpz31TP3pezl-HmGOuy9ky61NuimfOpnqRE6En6OxVzPlJOUJq8Wvli_QgxzZWL9mksMWxrHKR9pRbC2HHHsUC-7Skw";

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
