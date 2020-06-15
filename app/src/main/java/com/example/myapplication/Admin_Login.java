package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class Admin_Login extends AppCompatActivity {
    private static final Pattern PASSWORD_PATTERN= Pattern.compile("^"+"(?=.*[0-9])"+"(?=.*[a-z])"+"(?=.*[A-Z])"+"(?=.*[@#$%^&+=])"+"(?=\\s+$)"+".{4,}"+"$");
    private EditText Username;
    private EditText Password;
    private Button login;
    @SuppressLint("WrongViewCast")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__login);

        Username=findViewById(R.id.username1);
        Password=findViewById(R.id.password1);
        login=findViewById(R.id.login1);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm(v);
            }
        });
    }

    private boolean validateUsername(){
        String username=Username.getText().toString().trim();
        if(username.isEmpty()){
            Username.setError("Field can't be empty");
            return false;
        }
        else if(username.length()>15){
            Username.setError("Username too long");
            return false;
        }
        else{
            Username.setError(null);
            return true;
        }
    }
    private boolean validatePassword(){
        String password=Password.getText().toString().trim();
        if(password.isEmpty()){
            Password.setError("Field can't be empty");
            return false;
        }
        else if(!PASSWORD_PATTERN.matcher(password).matches()){
            Password.setError("Password too weak");
            return false;
        }
        else{
            Password.setError(null);
            return true;
        }
    }
    public void confirm(View v){
        if(!validateUsername()|validatePassword()){
            return;
        }
        String string="Username"+Username.getText().toString();
        string+="\n";
        string+="Password"+Password.getText().toString();
        Toast.makeText(this,string,Toast.LENGTH_SHORT);
    }


    public void signup(View view){
        startActivity(new Intent(getApplicationContext(),SignUp_form.class));
    }

    public void Send_OTP(View view){
        startActivity(new Intent(getApplicationContext(),Send_OTP.class));
    }
}
