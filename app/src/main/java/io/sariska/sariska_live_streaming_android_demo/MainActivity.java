package io.sariska.sariska_live_streaming_android_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.io.IOException;

import io.sariska.sariska_live_streaming_android_demo.LiveStreamFragments.MainFragment;
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
        try {
            generateToken();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateToken() throws IOException {
        GenerateToken httpClient = new GenerateToken();
        String apiUrl = "https://api.sariska.io/api/v1/misc/generate-token";
        httpClient.makeHttpRequest(apiUrl, new GenerateToken.HttpRequestCallback() {
            @Override
            public void onResponse(String response) {
                System.out.println("Response: "+response);
            }

            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("Response: "+ throwable);
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }
}