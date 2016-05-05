package com.digitalpanandpaper.www.digitalpanandpaper_adventuremanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import Data.Domain;
import Interfaces.ILogin;
import Login.LoginManager;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
                if (login.login(userName,passWord))
                {
                    Domain.setUser(userName);
                    Intent viewChange = new Intent(this,CharChooserActivity.class);
                    startActivity(viewChange);
                }
                else //login failed
                {
                    error.setText(R.string.login_error);
                    error.setVisibility(View.VISIBLE);
                }
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
