package io.sariska.sariska_live_streaming_android_demo.LiveStreamBaseFragments.joinlivefragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import io.sariska.sariska_live_streaming_android_demo.R;

public class JoinLiveStreamFragment extends Fragment {

    private ExoPlayer exoPlayer;

    private PlayerView playerView;
    public static JoinLiveStreamFragment newInstance() {
        return new JoinLiveStreamFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_join_live, container, false);
        // Add your UI elements and logic specific to Start Live fragment
        playerView = view.findViewById(R.id.video_view);

        ExoPlayer player = new ExoPlayer.Builder(getContext()).build();

        playerView.setPlayer(player);


        return view;
    }
}
