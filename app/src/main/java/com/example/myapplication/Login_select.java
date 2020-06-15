package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Login_select extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_select);
    }

    public void Login(View view) {
        startActivity(new Intent(getApplicationContext(),Login_form.class));
    }

    public void account(View view) {
        startActivity(new Intent(getApplicationContext(),Account.class));
    }
}
