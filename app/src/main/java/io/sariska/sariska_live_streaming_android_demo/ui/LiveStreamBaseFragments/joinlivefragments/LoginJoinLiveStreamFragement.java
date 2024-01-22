package io.sariska.sariska_live_streaming_android_demo.ui.LiveStreamBaseFragments.joinlivefragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputEditText;

import io.sariska.sariska_live_streaming_android_demo.R;

public class LoginJoinLiveStreamFragement extends Fragment {
    private Button joinLiveButton;
    private TextInputEditText textInputEditText;
    public static LoginJoinLiveStreamFragement newInstance() {
        return new LoginJoinLiveStreamFragement();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_join_live, container, false);
        joinLiveButton = view.findViewById(R.id.btnJoinLiveStream);
        textInputEditText = view.findViewById(R.id.joinstreamtext);

        joinLiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(textInputEditText.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "Please Enter room name", Toast.LENGTH_LONG).show();
                    return;
                }

                @SuppressLint("UnsafeOptInUsageError")
                PlayJoinedLiveStreamFragment playJoinedLiveStreamFragment = PlayJoinedLiveStreamFragment.newInstance();
                Bundle roomDetails = new Bundle();
                roomDetails.putString("roomName", textInputEditText.getText().toString());
                playJoinedLiveStreamFragment.setArguments(roomDetails);
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
