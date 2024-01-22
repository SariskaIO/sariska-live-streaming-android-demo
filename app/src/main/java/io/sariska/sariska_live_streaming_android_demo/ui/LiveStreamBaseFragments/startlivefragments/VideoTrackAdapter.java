package io.sariska.sariska_live_streaming_android_demo.ui.LiveStreamBaseFragments.startlivefragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oney.WebRTCModule.WebRTCView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.sariska.sariska_live_streaming_android_demo.R;

public class VideoTrackAdapter extends RecyclerView.Adapter<VideoTrackAdapter.VideoViewHolder> {
    private List<WebRTCView> videoTracks = new ArrayList<>();

    @NonNull
    @Override
    public VideoTrackAdapter.VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_remote_views, parent, false);
        return new VideoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoTrackAdapter.VideoViewHolder holder, int position) {
        WebRTCView view = videoTracks.get(position);
        view.setMirror(true);
        view.setObjectFit("cover");
        holder.remote_video_view_container.addView(view);
    }

    @Override
    public int getItemCount() {
        return videoTracks.size();
    }

    public List<WebRTCView> getVideoTracks(){
        return videoTracks;
    }

    public void setVideoTracks(WebRTCView videoTracks) {
        this.videoTracks.add(0,videoTracks);
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        // Define your view references here
        @BindView(R.id.remote_video_view_container)
        RelativeLayout remote_video_view_container;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize views here
            ButterKnife.bind(this, itemView);
        }
    }
}
