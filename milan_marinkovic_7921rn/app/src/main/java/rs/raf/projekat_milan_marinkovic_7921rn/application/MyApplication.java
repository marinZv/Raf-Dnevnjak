package rs.raf.projekat_milan_marinkovic_7921rn.application;

import android.app.Application;
import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import timber.log.Timber;

public class MyApplication extends Application {

    public static final String FILE_NAME = "password.txt";
    private String password;

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());

        context = getApplicationContext();
        File file = new File(getFilesDir(), FILE_NAME);

        if(!file.exists()){

            try {
                FileOutputStream outputStream = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
                String password = "ZvecanKosmet99";
                outputStream.write(password.getBytes(StandardCharsets.UTF_8));
                outputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

}
