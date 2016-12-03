package com.stepnik.kornel.bookshare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.stepnik.kornel.bookshare.models.LoginRequest;
import com.stepnik.kornel.bookshare.models.LoginResponse;
import com.stepnik.kornel.bookshare.models.User;
import com.stepnik.kornel.bookshare.services.AppData;
import com.stepnik.kornel.bookshare.services.UserServiceAPI;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by korSt on 04.11.2016.
 */

public class LoginActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "loginFile";
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.10.1:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    UserServiceAPI userService = retrofit.create(UserServiceAPI.class);




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
        LoginRequest loginRequest = new LoginRequest(username, password);
        Call<LoginResponse> login = userService.login(loginRequest);
        login.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {
                Log.d("isOK", String.valueOf(response.isSuccessful()));

                if (response.isSuccessful()){
                    Log.d("response", response.body().toString());
                    LoginResponse resp = new LoginResponse(response.body().getUserId(), response.body().getToken());
                    AppData.loggedUser = resp;

                    proceedUser();

                } else {
                    Utilities.displayMessage(getString(R.string.err_no_user), LoginActivity.this);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
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