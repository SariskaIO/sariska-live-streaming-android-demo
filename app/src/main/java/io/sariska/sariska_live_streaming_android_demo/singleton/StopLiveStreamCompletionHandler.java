package io.sariska.sariska_live_streaming_android_demo.singleton;

public interface StopLiveStreamCompletionHandler {
    void onSuccess(boolean started);
    void onFailure(Throwable throwable);
}
