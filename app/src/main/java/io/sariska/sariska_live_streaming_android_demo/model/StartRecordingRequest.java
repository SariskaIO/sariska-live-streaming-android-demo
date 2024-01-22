package io.sariska.sariska_live_streaming_android_demo.model;

public class StartRecordingRequest {
    private String room_name;

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public boolean isIs_low_latency() {
        return is_low_latency;
    }

    public void setIs_low_latency(boolean is_low_latency) {
        this.is_low_latency = is_low_latency;
    }

    private boolean is_low_latency;
}
