package io.sariska.sariska_live_streaming_android_demo;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import io.sariska.sariska_live_streaming_android_demo.singleton.CompletionHandler;
import io.sariska.sariska_live_streaming_android_demo.singleton.TokenManagerInstance;
import io.sariska.sariska_live_streaming_android_demo.ui.LiveStreamBaseFragments.MainFragment;
import io.sariska.sariska_live_streaming_android_demo.viewmodel.StreamViewModel;

public class MainActivity extends AppCompatActivity {

    private StreamViewModel streamViewModel;
    private TokenManagerInstance tokenManagerInstance = TokenManagerInstance.getInstance();;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Generate Token at the beginning of the call
        TokenManagerInstance tokenManagerInstance = TokenManagerInstance.getInstance();
        tokenManagerInstance.refreshToken(new CompletionHandler() {
            @Override
            public void onSuccess(String token) {
                if(savedInstanceState == null){
                    // Create an instance of MainFragment
                    MainFragment fragment = MainFragment.newInstance();
                    // Replace the container with MainFragment
                    replaceFragment(fragment);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                System.out.println(throwable);
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