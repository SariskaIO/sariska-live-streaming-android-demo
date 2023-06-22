package io.sariska.sariska_live_streaming_android_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.io.IOException;

import io.sariska.sariska_live_streaming_android_demo.LiveStreamBaseFragments.MainFragment;
import io.sariska.sariska_live_streaming_android_demo.utils.GenerateToken;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState == null){
            // Create an instance of MainFragment
            MainFragment fragment = MainFragment.newInstance();
            // Replace the container with MainFragment
            replaceFragment(fragment);
        }
    }



    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }
}