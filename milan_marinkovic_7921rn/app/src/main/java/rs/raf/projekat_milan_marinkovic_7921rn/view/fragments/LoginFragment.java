package rs.raf.projekat_milan_marinkovic_7921rn.view.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import rs.raf.projekat_milan_marinkovic_7921rn.R;
import rs.raf.projekat_milan_marinkovic_7921rn.application.MyApplication;
import rs.raf.projekat_milan_marinkovic_7921rn.models.User;
import rs.raf.projekat_milan_marinkovic_7921rn.view.activities.MainActivity;
import rs.raf.projekat_milan_marinkovic_7921rn.viewmodels.LoginViewModel;

public class LoginFragment extends Fragment {

    private LoginViewModel loginViewModel;

    private Button loginButton;
    private EditText passwordEt;
    private EditText usernameEt;
    private EditText emailEt;

    public LoginFragment(){
        super(R.layout.fragment_login);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);
        checkUser();
    }

    private void init(View view){
        loginButton = (Button) view.findViewById(R.id.loginButton);
        passwordEt = (EditText) view.findViewById(R.id.passwordLoginTv);
        usernameEt = (EditText) view.findViewById(R.id.loginUsernameTv);
        emailEt = (EditText) view.findViewById(R.id.emailLoginTv);
    }

    private void checkUser(){

        loginButton.setOnClickListener(v -> {
            String passwordFromFile = readPasswordFromFile();
            System.out.println(passwordFromFile);
            String passwordFromEt = passwordEt.getText().toString().trim();
            String usernameFromEt = usernameEt.getText().toString().trim();
            String emailFromEt = emailEt.getText().toString().trim();

            boolean flagPass = false;
            boolean flagEmail = false;
            boolean flagUsername = false;
            String messagePass = "";
            String messageEmail = "";
            String messageUsername = "";

            if(passwordFromEt.equals("")){
                messagePass = "lozinku, ";
            }else{
                flagPass = true;
            }

            if(emailFromEt.equals("")){
                messageEmail = "email, ";
            }else{
                flagEmail = true;
            }

            if(usernameFromEt.equals("")){
                messageUsername = "username, ";
            }else{
                flagUsername = true;
            }

            if(flagUsername && flagEmail && flagPass){

                if(passwordFromFile != null && passwordFromFile.equals(passwordFromEt)){


                    User user = new User(usernameFromEt, passwordFromEt, emailFromEt);
                    user.setLogged(true);

                    SharedPreferences sharedPreferences = requireContext().getSharedPreferences(MainActivity.PREF_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    Gson gson = new Gson();
                    String userJson = gson.toJson(user);
                    editor.putString(MainActivity.KEY_USER, userJson);
                    editor.apply();

                    Fragment fragment = new BottomNavigationFragment();
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.mainFcv, fragment)
                            .addToBackStack(null)
                            .commit();

                }else{
                    Toast.makeText(requireActivity(), "lozinka je netacna, unesite je ponovo", Toast.LENGTH_SHORT).show();
                }
            }else{
                String fullMessage = "Molimo vas unesite " + messageUsername + messagePass + messageEmail;
                Toast.makeText(requireActivity(), fullMessage, Toast.LENGTH_SHORT).show();
            }


        });


    }


    public String readPasswordFromFile(){
        String passwordString = null;

        Context context = MyApplication.context;
        try {
            FileInputStream fileInputStream = context.openFileInput(MyApplication.FILE_NAME);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            passwordString = stringBuilder.toString();
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return passwordString;
    }
}
