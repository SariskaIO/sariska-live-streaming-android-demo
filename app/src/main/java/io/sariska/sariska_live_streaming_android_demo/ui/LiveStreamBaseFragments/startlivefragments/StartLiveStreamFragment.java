package io.sariska.sariska_live_streaming_android_demo.ui.LiveStreamBaseFragments.startlivefragments;

import static com.facebook.react.bridge.UiThreadUtil.runOnUiThread;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.oney.WebRTCModule.WebRTCView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.sariska.sariska_live_streaming_android_demo.R;
import io.sariska.sariska_live_streaming_android_demo.singleton.TokenManagerInstance;
import io.sariska.sariska_live_streaming_android_demo.utils.GenerateToken;
import io.sariska.sariska_live_streaming_android_demo.utils.StartLiveStreamApiCall;
import io.sariska.sdk.Conference;
import io.sariska.sdk.Connection;
import io.sariska.sdk.JitsiLocalTrack;
import io.sariska.sdk.JitsiRemoteTrack;
import io.sariska.sdk.SariskaMediaTransport;

public class StartLiveStreamFragment extends Fragment {
    @BindView(R.id.remoteRecycleView)
    public RecyclerView recyclerView;
    private VideoTrackAdapter videoAdapter;
    private List<JitsiLocalTrack> localTracks;
    private Connection connection;
    private Conference conference;
    public ImageView startStreamingButton;
    private Bundle roomDetails;
    private String roomName;
    private TextView someViewText;

    private String hls_url="";
    private Button copyButton;
    private ClipboardManager clipboard;
    @BindView(R.id.local_video_view_container)
    public RelativeLayout mLocalContainer;
    public static StartLiveStreamFragment newInstance() {
        return new StartLiveStreamFragment();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start_live, container, false);
        ButterKnife.bind(this, view);
        startStreamingButton = view.findViewById(R.id.myButton);
        someViewText = view.findViewById(R.id.textToCopy);
        copyButton = view.findViewById(R.id.copyButton);
        videoAdapter = new VideoTrackAdapter();
        recyclerView.setAdapter(videoAdapter);
        clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        roomDetails = getArguments();
        roomName = roomDetails.getString("roomName");
        // Sariska Initialize SDK
        initializeSdk();
        return view;
    }

    // TODO: Initialize SDK
    public void initializeSdk(){
        SariskaMediaTransport.initializeSdk(getActivity().getApplication());
        createLocalTracks();
    }

    // TODO: Create Local Tracks
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

    // TODO: Create Connection
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
        });

        conference.addEventListener("TRACK_ADDED", p -> {
            JitsiRemoteTrack track = (JitsiRemoteTrack) p;
            if (track.getStreamURL().equals(localTracks.get(1).getStreamURL())) {
                //So as to not add local track in remote container
                return;
            }
            getActivity().runOnUiThread(() -> {
                if (track.getType().equals("video")) {
                    System.out.println("Adding to userList");
                    videoAdapter.setVideoTracks(track.render());
                    addRemoteVideoTrack(track);
                }
            });
        });
        conference.join();
    }

    private void addOnClickListenerToStreamingButton() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startStreamingButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!conference.isJoined()){
                            return;
                        }
                        StartLiveStreamApiCall streamApiCall = new StartLiveStreamApiCall();
                        streamApiCall.startLiveStreaming("https://api.sariska.io/terraform/v1/hooks/srs/startRecording",
                                roomName, new StartLiveStreamApiCall.liveStreamResponseCallback() {
                                    @Override
                                    public void onResponse(String response) throws JSONException {
                                        JSONObject jsonResponse = new JSONObject(response);
                                        hls_url = jsonResponse.getString("hls_url");
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                someViewText.setText(hls_url);
                                            }
                                        });
                                    }
                                    @Override
                                    public void onFailure(Throwable throwable) {
                                        System.out.println(throwable.getCause());
                                        System.out.println(throwable);
                                        System.out.println("Failure Failure Failure");
                                    }
                                });
                    }
                });

                copyButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ClipData clip = ClipData.newPlainText("label", someViewText.getText());
                        clipboard.setPrimaryClip(clip);
                    }
                });
            }
        });
    }

    public void addRemoteVideoTrack(JitsiRemoteTrack videoTrack){
        videoAdapter.notifyDataSetChanged();
    }
}
