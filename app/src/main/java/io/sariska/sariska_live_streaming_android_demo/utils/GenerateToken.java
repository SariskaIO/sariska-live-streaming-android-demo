package io.sariska.sariska_live_streaming_android_demo.utils;

import org.json.JSONException;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GenerateToken {

    public interface HttpRequestCallback {
        void onResponse(String response) throws JSONException;
        void onFailure(Throwable throwable);
    }

    public void makeHttpRequest(String apiUrl,final HttpRequestCallback callback) throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                String json = "{\n" +
                        "    \"apiKey\": \"249202aabed00b41363794b526eee6927bd35cbc9bac36cd3edcaa\",\n" +
                        "    \"user\": {\n" +
                        "        \"name\": \""+"dipsak"+"\",\n" +
                        "        \"moderator\": true,\n" +
                        "        \"email\": \"dipak@work.com\",\n" +
                        "        \"avatar\":\"null\"\n" +
                        "    }\n" +
                        "}";
                RequestBody body = RequestBody.create(JSON, json);
                Request request = new Request.Builder()
                        .url(apiUrl)
                        .post(body)
                        .addHeader("Content-Type", "application/json")
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        final String responseBody = response.body().string();
                        callback.onResponse(responseBody);
                    } else {
                        // Handle the error response
                        callback.onFailure(new IOException("Request not successful"));
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
