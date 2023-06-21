package io.sariska.sariska_live_streaming_android_demo.LiveStreamFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import io.sariska.sariska_live_streaming_android_demo.R;

public class FrontFragmentLayout extends Fragment {

    private Button btnStartLive;
    private Button btnJoinStream;

    public static FrontFragmentLayout newInstance() {
        return new FrontFragmentLayout();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        btnStartLive = view.findViewById(R.id.btnStartLive);
        btnJoinStream = view.findViewById(R.id.btnJoinStream);

        btnStartLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Replace with Fragment

            }
        });

        btnJoinStream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Replace with Fragment

            }
        });

        return view;
    }
}
