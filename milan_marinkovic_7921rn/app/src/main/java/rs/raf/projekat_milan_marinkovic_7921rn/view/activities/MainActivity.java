package rs.raf.projekat_milan_marinkovic_7921rn.view.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import rs.raf.projekat_milan_marinkovic_7921rn.R;
import rs.raf.projekat_milan_marinkovic_7921rn.view.fragments.SplashScreenFragment;

public class MainActivity extends AppCompatActivity {



    public static final String PREF_NAME = "user_preference";
    public static final String KEY_USER = "user";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mainFcv, new SplashScreenFragment())
                    .commit();
        }
    }


}
