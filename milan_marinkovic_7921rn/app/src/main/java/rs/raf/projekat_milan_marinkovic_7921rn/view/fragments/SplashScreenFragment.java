package rs.raf.projekat_milan_marinkovic_7921rn.view.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import rs.raf.projekat_milan_marinkovic_7921rn.R;
import rs.raf.projekat_milan_marinkovic_7921rn.models.User;
import rs.raf.projekat_milan_marinkovic_7921rn.view.activities.MainActivity;

public class SplashScreenFragment extends Fragment {

    private ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_splash_screen, container, false);
        imageView = view.findViewById(R.id.logoIv);
        imageView.setImageResource(R.drawable.logo);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                boolean flag = false;

                SharedPreferences sharedPreferences = requireContext().getSharedPreferences(MainActivity.PREF_NAME, Context.MODE_PRIVATE);
                String userJson = sharedPreferences.getString(MainActivity.KEY_USER, null);
                // Kreirajte Gson objekat
                if(userJson != null){
                    Gson gson = new Gson();
                    User user = gson.fromJson(userJson, User.class);
                    if(user.isLogged()){
                        flag = true;
                    }
                }

                Fragment fragment = null;
                if(flag){
                    fragment = new BottomNavigationFragment();
                }else{
                    fragment = new LoginFragment();
                }

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.mainFcv, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        }, 3000);
    }
}
