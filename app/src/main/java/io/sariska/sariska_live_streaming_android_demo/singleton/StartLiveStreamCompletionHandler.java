package io.sariska.sariska_live_streaming_android_demo.singleton;

public interface StartLiveStreamCompletionHandler {
    void onSuccess(String hls_url);
    void onFailure(Throwable throwable);
}
