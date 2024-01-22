package io.sariska.sariska_live_streaming_android_demo.ui.LiveStreamBaseFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import io.sariska.sariska_live_streaming_android_demo.ui.LiveStreamBaseFragments.joinlivefragments.LoginJoinLiveStreamFragement;
import io.sariska.sariska_live_streaming_android_demo.ui.LiveStreamBaseFragments.startlivefragments.LoginStartLiveStreamFragment;
import io.sariska.sariska_live_streaming_android_demo.R;


public class MainFragment extends Fragment {
    private Button btnStartLive;
    private Button btnJoinStream;
    public static MainFragment newInstance() {
        return new MainFragment();
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
                LoginStartLiveStreamFragment fragment = LoginStartLiveStreamFragment.newInstance();
                navigateToFragment(fragment);
            }
        });

        btnJoinStream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Replace with Fragment
                LoginJoinLiveStreamFragement playJoinedLiveStreamFragment = LoginJoinLiveStreamFragement.newInstance();
                navigateToFragment(playJoinedLiveStreamFragment);
            }
        });
        return view;
    }

    private void navigateToFragment(Fragment fragment) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null); // Add to back stack to enable back navigation
        fragmentTransaction.commit();
    }
}
