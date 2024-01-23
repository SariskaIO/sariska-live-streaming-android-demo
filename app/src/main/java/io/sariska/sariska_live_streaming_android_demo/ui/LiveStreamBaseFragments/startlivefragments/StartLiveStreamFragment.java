package io.sariska.sariska_live_streaming_android_demo.ui.LiveStreamBaseFragments.startlivefragments;

import static com.facebook.react.bridge.UiThreadUtil.runOnUiThread;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.OptIn;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.media3.common.MediaItem;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.datasource.DataSource;
import androidx.media3.datasource.DefaultHttpDataSource;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.hls.HlsMediaSource;
import androidx.media3.ui.PlayerView;
import com.oney.WebRTCModule.WebRTCView;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.sariska.sariska_live_streaming_android_demo.R;
import io.sariska.sariska_live_streaming_android_demo.singleton.StartLiveStreamCompletionHandler;
import io.sariska.sariska_live_streaming_android_demo.singleton.StopLiveStreamCompletionHandler;
import io.sariska.sariska_live_streaming_android_demo.singleton.TokenManagerInstance;
import io.sariska.sariska_live_streaming_android_demo.viewmodel.StartStreamViewModel;
import io.sariska.sariska_live_streaming_android_demo.viewmodel.StopStreamViewModel;
import io.sariska.sdk.Conference;
import io.sariska.sdk.Connection;
import io.sariska.sdk.JitsiLocalTrack;
import io.sariska.sdk.JitsiRemoteTrack;
import io.sariska.sdk.SariskaMediaTransport;

public class StartLiveStreamFragment extends Fragment {
    private List<JitsiLocalTrack> localTracks;
    private Connection connection;
    private Conference conference;
    private Bundle roomDetails;
    private String roomName;
    private TextView someViewText;
    private StartStreamViewModel startStreamViewModel;
    private StopStreamViewModel stopStreamViewModel;
    private Button copyButton;
    public Button startStreamingButton;
    private Button endCallButton;
    private Button stopLiveButton;
    private ClipboardManager clipboard;
    private PlayerView playerView;
    @BindView(R.id.local_video_view_container)
    public RelativeLayout mLocalContainer;

    @BindView(R.id.remote_video_view_container)
    public RelativeLayout mRemoteContainer;
    public static StartLiveStreamFragment newInstance() {
        return new StartLiveStreamFragment();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start_live, container, false);
        ButterKnife.bind(this, view);
        initializeViews(view);
        initializeSdk();
        return view;
    }

    private void initializeViews(View view) {
        someViewText = view.findViewById(R.id.textToCopy);
        playerView = view.findViewById(R.id.livestreams_player_view);
        startStreamingButton = view.findViewById(R.id.myButton);
        copyButton = view.findViewById(R.id.copyButton);
        stopLiveButton = view.findViewById(R.id.stopLiveButton);
        endCallButton = view.findViewById(R.id.endCallButton);
        clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        roomDetails = getArguments();
        roomName = roomDetails.getString("roomName");
    }

    public void initializeSdk(){
        SariskaMediaTransport.initializeSdk(getActivity().getApplication());
        createLocalTracks();
    }

    public void createLocalTracks(){
        Bundle options = new Bundle();
        options.putBoolean("audio", true);
        options.putBoolean("video", true);
        options.putInt("resolution", 360);
        SariskaMediaTransport.createLocalTracks(options, tracks -> {
            getActivity().runOnUiThread(()->{
                localTracks = tracks;
                for (JitsiLocalTrack track : tracks) {
                    if (track.getType().equals("video")) {
                        WebRTCView view = track.render();
                        view.setObjectFit("cover");
                        view.setMirror(true);
                        mLocalContainer.addView(track.render());
                    }
                }
            });
        });

        createConnection();
    }

    public void createConnection(){
        connection = SariskaMediaTransport.JitsiConnection(TokenManagerInstance.getInstance().
                getJwtToken(), roomName, false);

        connection.addEventListener("CONNECTION_ESTABLISHED", this::createConference);

        connection.addEventListener("CONNECTION_FAILED", () -> {
        });

        connection.addEventListener("CONNECTION_DISCONNECTED", () -> {
        });

        connection.connect();
    }

    private void createConference() {

        conference = connection.initJitsiConference();

        conference.addEventListener("CONFERENCE_JOINED", () -> {
            for (JitsiLocalTrack track : localTracks) {
                conference.addTrack(track);
            }

            addOnClickListenerToStreaming();
            addOnClickListenerToStopStreaming();
            addOnClickListenerToEndCall();
            addOnClickListenerToCopyUrl();
        });

        conference.addEventListener("TRACK_ADDED", p -> {
            JitsiRemoteTrack track = (JitsiRemoteTrack) p;
            if (track.getStreamURL().equals(localTracks.get(1).getStreamURL())) {
                //So as to not add local track in remote container
                return;
            }
            getActivity().runOnUiThread(() -> {
                if (track.getType().equals("video")) {
                    // TODO: Add Remote View
                    WebRTCView view = track.render();
                    mRemoteContainer.addView(view);
                }
            });
        });

        conference.addEventListener("TRACK_REMOVED", p -> {
            JitsiRemoteTrack track = (JitsiRemoteTrack) p;
            runOnUiThread(() -> {

            });
        });

        conference.join();
    }

    private void addOnClickListenerToCopyUrl() {
        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipData clip = ClipData.newPlainText("label", someViewText.getText());
                clipboard.setPrimaryClip(clip);
            }
        });
    }

    private void addOnClickListenerToStreaming(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startStreamingButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!conference.isJoined()){
                            Toast.makeText(getContext(), "CONFERENCE NOT JOINED YET", Toast.LENGTH_LONG).show();
                            return;
                        }
                        Toast.makeText(getContext(), "CONFERENCE JOINED, STARTING STREAM", Toast.LENGTH_LONG).show();

                        startStreamViewModel = new ViewModelProvider(getActivity()).get(StartStreamViewModel.class);
                        startStreamViewModel.loadApiResponse(roomName,someViewText ,new StartLiveStreamCompletionHandler() {
                            @OptIn(markerClass = UnstableApi.class) @Override
                            public void onSuccess(String hls_url) {
                                System.out.println("HLS URL HERE");
                                System.out.println(hls_url);
                                ExoPlayer player = new ExoPlayer.Builder(getContext()).build();
                                playerView.setPlayer(player);
                                Uri uri = Uri.parse(hls_url);
                                DataSource.Factory dataSourceFactory = new DefaultHttpDataSource.Factory();
                                HlsMediaSource hlsMediaSource = new HlsMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(uri));
                                player.setMediaSource(hlsMediaSource);
                                player.prepare();
                            }

                            @Override
                            public void onFailure(Throwable throwable) {
                                System.out.println(throwable);
                            }
                        });
                    }
                });
            }
        });
    }

    private void addOnClickListenerToStopStreaming(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                stopLiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!conference.isJoined()){
                            Toast.makeText(getContext(), "CONFERENCE NOT JOINED YET", Toast.LENGTH_LONG).show();
                        }
                        stopStreamViewModel = new ViewModelProvider(getActivity()).get(StopStreamViewModel.class);
                        stopStreamViewModel.loadApiResponse(roomName, new StopLiveStreamCompletionHandler() {
                            @Override
                            public void onSuccess(boolean started) {
                                if (!started){
                                    Toast.makeText(getActivity(), "STREAMING STOPPPED", Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(getActivity(), "STREAMING CANNOT BE STOPPPED", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Throwable throwable) {
                                Toast.makeText(getActivity(), "Failed to Stop Streaming", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
            }
        });
    }

    private void addOnClickListenerToEndCall(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                endCallButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        conference.leave();
                        connection.disconnect();
                        for (JitsiLocalTrack track : localTracks){
                            track.dispose();
                        }
                        mLocalContainer.removeAllViews();
                        mRemoteContainer.removeAllViews();
                    }
                });
            }
        });
    }
}
