package rs.raf.projekat_milan_marinkovic_7921rn.view.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rs.raf.projekat_milan_marinkovic_7921rn.R;
import rs.raf.projekat_milan_marinkovic_7921rn.application.MyApplication;
import rs.raf.projekat_milan_marinkovic_7921rn.models.User;
import rs.raf.projekat_milan_marinkovic_7921rn.view.activities.MainActivity;

public class ProfilFragment extends Fragment {

    private TextView profileNameTv;
    private TextView profileEmailTv;
    private Button changePasswordButton;
    private Button logoutButton;
    private User user;

    public ProfilFragment(){
        super(R.layout.fragment_profil);
        System.out.println("Pozvao sam se u ProfilFragmentu");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);
        initUser();
        initView();
        initListeners();
        initChangePassword();
    }

    private void initUser(){
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(MainActivity.PREF_NAME, requireContext().MODE_PRIVATE);
        String userJson = sharedPreferences.getString(MainActivity.KEY_USER, null);
        if(userJson != null){
            Gson gson = new Gson();
            user = gson.fromJson(userJson, User.class);
        }
    }

    private void init(View view){
        profileNameTv = (TextView)view.findViewById(R.id.profileNameTv);
        profileEmailTv = (TextView) view.findViewById(R.id.profileEmailTv);
        changePasswordButton = (Button)view.findViewById(R.id.changePasswordButton);
        logoutButton = view.findViewById(R.id.logOutButton);
    }

    private void initView(){
       profileNameTv.setText(user.getUsername());
       profileEmailTv.setText(user.getEmail());
    }

    private void initListeners(){
        logoutButton.setOnClickListener(v -> {

            SharedPreferences sharedPreferences = requireContext().getSharedPreferences(MainActivity.PREF_NAME, Context.MODE_PRIVATE);

            // Brisanje User objekta iz SharedPreferences-a
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(MainActivity.KEY_USER); // KEY_USER je ključ pod kojim je User objekat sačuvan
            editor.apply();

            Fragment fragment = new LoginFragment();
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainFcv, fragment)
                    .commit();
        });
    }

    private void initChangePassword(){
        changePasswordButton.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.change_password_dialog, null);
            builder.setView(dialogView);

            AlertDialog alertDialog = builder.create();
            alertDialog.show();

            EditText inputNewPassword = (EditText) dialogView.findViewById(R.id.changePasswordEt);
            EditText confirmInputNewPassword = (EditText) dialogView.findViewById(R.id.changePasswordConfirmationEt);
            Button confirmChangePasswordButton = dialogView.findViewById(R.id.changePasswordConfirmationButton);

            confirmChangePasswordButton.setOnClickListener(w -> {
                String stringInputNewPassword = inputNewPassword.getText().toString().trim();
                String stringConfirmInputNewPassword = confirmInputNewPassword.getText().toString().trim();

                if(checkString(stringConfirmInputNewPassword)){
                    if(stringInputNewPassword.equals(stringConfirmInputNewPassword)){
                        writePasswordToFile(stringConfirmInputNewPassword);
                        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(MainActivity.PREF_NAME, Context.MODE_PRIVATE);
                        String userJson = sharedPreferences.getString(MainActivity.KEY_USER, null);
                        if(userJson != null){
                            Gson gson = new Gson();
                            User user = gson.fromJson(userJson, User.class);
                            user.setPassword(stringInputNewPassword);

                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            String userJsonString = gson.toJson(user);
                            editor.putString(MainActivity.KEY_USER, userJsonString);
                            editor.apply();
                        }
                    }else{
                        Toast.makeText(requireContext(), "Sifre se ne poklapaju", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(requireContext(), "Lozinka mora da sadrzi bar jedno veliko slovo i jednu cifru", Toast.LENGTH_SHORT).show();
                }

                alertDialog.dismiss();
            });
        });
    }

    public boolean checkString(String input) {
        String regex = "^(?=.*[A-Z])(?=.*\\d).+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public void writePasswordToFile(String password) {
        Context context = MyApplication.context;
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(MyApplication.FILE_NAME, Context.MODE_PRIVATE);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            bufferedWriter.write(password);
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
