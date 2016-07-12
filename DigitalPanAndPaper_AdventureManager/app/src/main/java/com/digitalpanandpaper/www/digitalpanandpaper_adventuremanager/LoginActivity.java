package com.digitalpanandpaper.www.digitalpanandpaper_adventuremanager;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import Interfaces.ILogin;
import Login.LoginManager;

public class LoginActivity extends AppCompatActivity {

    public static Context context;
    public static Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context=this;
        activity=this;
    }

    public void login(View view){
        EditText username = (EditText)findViewById(R.id.usernameEditText);
        EditText password = (EditText)findViewById(R.id.passwordEditText);
        TextView error = (TextView)findViewById(R.id.errorLabelTextView);
        error.setVisibility(View.GONE);
        ILogin login = LoginManager.getLogin(); //using manager to get real or mock login.
        String userName = username.getText().toString();
        String passWord = password.getText().toString();
        if (login.isUserOk(userName))
            if (login.isPassOk(passWord))
                login.login(userName,passWord);

            else //password invalid
            {
                error.setText(R.string.password_error);
                error.setVisibility(View.VISIBLE);
            }
        else //username invalid
        {
            error.setText(R.string.username_error);
            error.setVisibility(View.VISIBLE);
        }


    }
}
