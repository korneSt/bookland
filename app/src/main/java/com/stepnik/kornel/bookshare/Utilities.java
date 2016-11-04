package com.stepnik.kornel.bookshare;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by korSt on 04.11.2016.
 */

public class Utilities {

    public static void saveToFile(String fileName, Object object, Activity activity) throws IOException {
        FileOutputStream fos = activity.openFileOutput(fileName, Context.MODE_PRIVATE);
        ObjectOutputStream os = new ObjectOutputStream(fos);
        os.writeObject(object);
        os.close();
        fos.close();
    }

    public static Object loadFromFile(String fileName, Activity activity) throws IOException, ClassNotFoundException {
        FileInputStream fis = activity.openFileInput(fileName);
        ObjectInputStream is = new ObjectInputStream(fis);
        Object result = is.readObject();
        is.close();
        fis.close();
        return result;
    }

    public static boolean isNetworkAvailable(Activity activity) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void displayMessage(String text, Activity activity) {
        Toast.makeText(activity, text,
                Toast.LENGTH_LONG).show();
    }
}
