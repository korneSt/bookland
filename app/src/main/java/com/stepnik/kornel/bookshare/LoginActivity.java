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
            .baseUrl("http://192.168.0.101:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    UserService userService = retrofit.create(UserService.class);
    EditText username;
    EditText password;
    Button loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
        boolean hasLoggedIn = settings.getBoolean("hasLoggedIn", false);
        if(hasLoggedIn)
        {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            setContentView(R.layout.activity_login);
            username = (EditText) findViewById(R.id.et_username);
            password = (EditText) findViewById(R.id.et_password);
            loginButton = (Button) findViewById(R.id.b_login);
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isLogged();
                }
            });
        }
    }

    public boolean isLogged() {
        Call<User> login = userService.login(username.getText().toString(), password.getText().toString());
        login.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                Log.d("isOK", String.valueOf(response.isSuccessful()));
                if (response.isSuccessful()){
                    SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_NAME, 0); // 0 - for private mode
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putBoolean("hasLoggedIn", true);
                    editor.putString("username", response.body().getUsername());
                    AppData.loggedUser = response.body();
                    editor.apply();
                    Log.d("response", response.body().toString());
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    LoginActivity.this.finish();
                } else {
                    displayMessage();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("response F", t.getMessage());

            }
        });
        return false;
    }



    private void displayMessage() {
        Toast.makeText(LoginActivity.this, "Brak takiego uzytkownika",
                Toast.LENGTH_LONG).show();
    }

}