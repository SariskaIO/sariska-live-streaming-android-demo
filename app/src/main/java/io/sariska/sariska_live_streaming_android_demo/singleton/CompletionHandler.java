package io.sariska.sariska_live_streaming_android_demo.singleton;

public interface CompletionHandler {
    void onSuccess(String token);
    void onFailure(Throwable throwable);
}
