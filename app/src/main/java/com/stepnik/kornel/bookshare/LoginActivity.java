package com.stepnik.kornel.bookshare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.stepnik.kornel.bookshare.models.User;
import com.stepnik.kornel.bookshare.services.AppData;
import com.stepnik.kornel.bookshare.services.UserService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;


/**
 * Created by korSt on 04.11.2016.
 */

public class LoginActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "loginFile";
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http:/192.168.0.105:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    UserService userService = retrofit.create(UserService.class);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
        boolean hasLoggedIn = settings.getBoolean("hasLoggedIn", false);
        if(hasLoggedIn) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            setContentView(R.layout.activity_login);
            final EditText username = (EditText) findViewById(R.id.et_username);
            final EditText password = (EditText) findViewById(R.id.et_password);
            Button loginButton = (Button) findViewById(R.id.b_login);
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isLogged(username.getText().toString(), password.getText().toString());
                }
            });
        }
    }

    public boolean isLogged(String username, String password) {
        Call<User> login = userService.login(username, password);
        login.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                Log.d("isOK", String.valueOf(response.isSuccessful()));

                if (response.isSuccessful()){
                    Log.d("response", response.body().toString());

                    AppData.loggedUser = response.body();

                    proceedUser();

                } else {
                    Utilities.displayMessage(getString(R.string.err_no_user), LoginActivity.this);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("response F", t.getMessage());

            }
        });
        return false;
    }

    private void proceedUser() {
        setAsLogged();

        try {
            Utilities.saveToFile("userData", AppData.loggedUser, LoginActivity.this);
        } catch (IOException e) {
            e.printStackTrace();
            Utilities.displayMessage(getString(R.string.err_savefile), this);
        }

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        LoginActivity.this.finish();
    }


    private void setAsLogged() {
        SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("hasLoggedIn", true);
        editor.apply();
    }

//    private void displayMessage(String text) {
//        Toast.makeText(LoginActivity.this, text,
//                Toast.LENGTH_LONG).show();
//    }

}