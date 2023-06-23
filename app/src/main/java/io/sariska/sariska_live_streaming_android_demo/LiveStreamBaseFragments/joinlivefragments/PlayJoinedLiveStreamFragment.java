package io.sariska.sariska_live_streaming_android_demo.LiveStreamBaseFragments.joinlivefragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.media3.common.MediaItem;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.datasource.DataSource;
import androidx.media3.datasource.DefaultHttpDataSource;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.hls.HlsMediaSource;
import androidx.media3.ui.PlayerView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import io.sariska.sariska_live_streaming_android_demo.R;

@UnstableApi public class PlayJoinedLiveStreamFragment extends Fragment {

    private ExoPlayer exoPlayer;
    private PlayerView playerView;
    public static PlayJoinedLiveStreamFragment newInstance() {
        return new PlayJoinedLiveStreamFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_join_live, container, false);
        // Add your UI elements and logic specific to Start Live fragment
        playerView = view.findViewById(R.id.video_view);

        ExoPlayer player = new ExoPlayer.Builder(getContext()).build();

        playerView.setPlayer(player);
        // Build the media item.

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("dipak");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String data = snapshot.getValue().toString();
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String urlString = jsonObject.getString("hls_url");
                    Uri uri = Uri.parse(urlString);
                    DataSource.Factory dataSourceFactory = new DefaultHttpDataSource.Factory();
                    HlsMediaSource hlsMediaSource =
                            new HlsMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(uri));
                    player.setMediaSource(hlsMediaSource);
                    player.prepare();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("somthing idiotic");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;
    }
}
