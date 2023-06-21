package io.sariska.sariska_live_streaming_android_demo.LiveStreamFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import io.sariska.sariska_live_streaming_android_demo.R;

public class StartLiveStreamFragment extends Fragment {

    public static StartLiveStreamFragment newInstance() {
        return new StartLiveStreamFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start_live, container, false);
        // Add your UI elements and logic specific to Start Live fragment
        return view;
    }
}
