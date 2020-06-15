package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import java.util.Objects;
import java.util.regex.Pattern;

public class Login_form extends AppCompatActivity {
    private static final Pattern PASSWORD_PATTERN= Pattern.compile("^"+"(?=.*[0-9])"+"(?=.*[a-z])"+"(?=.*[A-Z])"+"(?=.*[@#$%^&+=])"+".{4,}");
    private EditText Email;
    private EditText Password;
    private Button btn;
    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    String email;
    String password;
    @SuppressLint("WrongViewCast")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);

        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        btn = findViewById(R.id.login);
        mAuth = FirebaseAuth.getInstance();
        db=FirebaseDatabase.getInstance();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm(v);
                loginuser();
            }
        });
    }

    private boolean validateEmail(){
        String email=Email.getText().toString().trim();

        if(email.isEmpty()){
            Email.setError("Field can't empty");
            return false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Email.setError("Email too weak");
            return false;
        }
        else{
            Email.setError(null);
            return true;
        }
    }
    private boolean validatePassword(){
        String password=Password.getText().toString().trim();

        if(password.isEmpty()){
            Password.setError("Field can't empty");
            return false;
        }else if(!PASSWORD_PATTERN.matcher(password).matches()){
            Password.setError("Password too weak");
            return false;
        }
        else{
            Password.setError(null);
            return true;
        }
    }

    void loginuser() {
        email=Email.getText().toString().trim();
        password=Password.getText().toString().trim();
        validateEmail();
        validatePassword();

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Login_form.this,"User login successfully",Toast.LENGTH_SHORT).show();
                    Map<String,String> user=new HashMap<>();
                    user.put("email",email);
                    user.put("password",password);
                    db.getReference().child("users").child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).setValue(user);

                    Intent intent = new Intent(Login_form.this, Subject.class);
                    startActivity(intent);


                } else {
                    Toast.makeText(Login_form.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void confirm(View v){
        if(!validateEmail() | !validatePassword()){
            return;
        }
        String string ="Email"+Email.getText().toString();
        string +="\n";
        string +="Password"+Password.getText().toString();
        Toast.makeText(this,string,Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(Login_form.this,Subject.class);
        startActivity(intent);
    }

    public void signup(View view) {
        startActivity(new Intent(getApplicationContext(),SignUp_form.class));
    }

}
