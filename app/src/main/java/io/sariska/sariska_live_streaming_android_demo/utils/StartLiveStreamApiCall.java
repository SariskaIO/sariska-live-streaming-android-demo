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

    public void startLiveStreaming(String apiUrl,String roomName, final StartLiveStreamApiCall.liveStreamResponseCallback callback){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();

                MediaType mediaType = MediaType.parse("application/json");

                String requestBodyString = "{\"room_name\":\""+roomName+"\"}";

                RequestBody requestBody = RequestBody.create(mediaType, requestBodyString);

                String url = apiUrl;

                String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImRkMzc3ZDRjNTBiMDY1ODRmMGY4MDJhYmFiNTIyMjg5ODJiMTk2YzAzNzYwNzE4NDhiNWJlNTczN2JiMWYwYTUiLCJ0eXAiOiJKV1QifQ.eyJjb250ZXh0Ijp7InVzZXIiOnsiaWQiOiJqc24xd25rNiIsIm5hbWUiOiJvbGRfa29hbGEifSwiZ3JvdXAiOiIyMDIifSwic3ViIjoicXdmc2Q1N3BxOWR4YWtxcXVxNnNlcSIsInJvb20iOiIqIiwiaWF0IjoxNjg3NDM0NTkxLCJuYmYiOjE2ODc0MzQ1OTEsImlzcyI6InNhcmlza2EiLCJhdWQiOiJtZWRpYV9tZXNzYWdpbmdfY28tYnJvd3NpbmciLCJleHAiOjE2ODc1MjA5OTF9.6LB1arWp7VbRitOZOqTU3s7TxNlKAD_K_M9o7ZSJZ9-ZMCO4QRx793afMTXkJGT_qLtHmPEbwL6opGqaNH2s2YANFC-3QgcetLAGk_IFYal3m0l8zo26blL3fBoWr4FSNVG4H0ILWN4nXGc0xtXztts4YxlnVXmdG5I6MXG5W2J7gajyVRa5SbcCrS76ktaZEU8HICAlGfifuH49l4DLL40MGO7smbqy1VxlSayuCOA83P2H0oqjUmeQisrqTR6--fIqo_UyruESnLcRZe5tuYv5I7P22biHtRoUEjQ76U2whbwFhYz5C7dBggTe0eKs--nVF13PhFGuYHGQGtDwRA";

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
        }).start();
    }
}
