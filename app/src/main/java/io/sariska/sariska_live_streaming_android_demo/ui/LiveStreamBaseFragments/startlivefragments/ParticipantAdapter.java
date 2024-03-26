package io.sariska.sariska_live_streaming_android_demo.ui.LiveStreamBaseFragments.startlivefragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.oney.WebRTCModule.WebRTCView;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.sariska.sariska_live_streaming_android_demo.R;
import io.sariska.sdk.JitsiRemoteTrack;

public class ParticipantAdapter extends RecyclerView.Adapter<ParticipantAdapter.ViewHolder> {
    private List<JitsiRemoteTrack> participantList;
    // Add to trackArrayList
    public ParticipantAdapter(List<JitsiRemoteTrack> participantList) {
        this.participantList = participantList;
    }

    public List<JitsiRemoteTrack> getParticipantList() {
        return participantList;
    }

    public void addToParticipantList(JitsiRemoteTrack track, int count){
        participantList.add(count-1,track);
        notifyItemInserted(count-1);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.participant_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Bind data here if needed
        WebRTCView view = participantList.get(position).render();
        view.setMirror(true);
        view.setObjectFit("cover");
        holder.videoContainer.addView(view);
    }

    @Override
    public int getItemCount() {
        return participantList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.videoContainer)
        RelativeLayout videoContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
