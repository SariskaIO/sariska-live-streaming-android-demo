package io.sariska.sariska_live_streaming_android_demo.ui.LiveStreamBaseFragments.startlivefragments;

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

public class LoginStartLiveStreamFragment extends Fragment {
    private Button btnStartLive;
    private TextInputEditText roomName;
    private TextInputEditText userName;
    public static LoginStartLiveStreamFragment newInstance() {
        return new LoginStartLiveStreamFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_start_live, container, false);
        btnStartLive = view.findViewById(R.id.btnLogin);
        roomName = view.findViewById(R.id.etUsername);
        userName = view.findViewById(R.id.etPassword);
        startLive();
        return view;
    }

    private void startLive() {
        btnStartLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(roomName.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "Please Enter Room Name",Toast.LENGTH_SHORT).show();
                    return;
                }else if(userName.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "Please Enter User Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                Bundle roomDetails = new Bundle();
                roomDetails.putString("roomName", roomName.getText().toString());
                roomDetails.putString("userName", userName.getText().toString());
                StartLiveStreamFragment fragment = StartLiveStreamFragment.newInstance();
                fragment.setArguments(roomDetails);
                navigateToFragment(fragment);
            }
        });
    }

    private void navigateToFragment(Fragment fragment) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null); // Add to back stack to enable back navigation
        fragmentTransaction.commit();
    }
}
