package com.example.alphaversion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity {

    private FirebaseAuth myAuth;

    EditText FullName;
    EditText Age;
    EditText Email_signup;
    EditText Password_signup;
    ProgressBar prog_reg;

    Intent RegisterUser;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        myAuth = FirebaseAuth.getInstance();

        FullName = (EditText) findViewById(R.id.FullName);
        Age = (EditText) findViewById(R.id.Age);
        Email_signup = (EditText) findViewById(R.id.Email_signup);
        Password_signup = (EditText) findViewById(R.id.Password_signup);
        prog_reg = (ProgressBar) findViewById(R.id.prog_reg);
    }

    public void Register_signup(View view) {
        String FullName_String = FullName.getText().toString().trim();
        String Age_String = Age.getText().toString().trim();
        String Email_signup_String= Email_signup.getText().toString().trim();
        String Password_signup_String= Password_signup.getText().toString().trim();

        if(FullName_String.isEmpty() || !FullName_String.contains(" ")) {
            FullName.setError("Full name is required!");
        }
        else if(Age_String.isEmpty() || Age_String.equals("0")){
            Age.setError("Valid Age is required!");
        }
        else if(Email_signup_String.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(Email_signup_String).matches()){
            Email_signup.setError(" Valid email is required!");
        }
        else if(Password_signup_String.isEmpty() || Password_signup_String.length() < 8){
            Password_signup.setError("Password must contain at least 8 characters");

        }
        // registry
        else{
            prog_reg.setVisibility(View.VISIBLE);
             myAuth.createUserWithEmailAndPassword(Email_signup_String,Password_signup_String )
                     .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                         @Override
                         public void onComplete(@NonNull Task<AuthResult> task) {
                             if(task.isSuccessful()){
                                User user = new User(FullName_String, Age_String, Email_signup_String, Password_signup_String);

                                 FirebaseDatabase.getInstance().getReference("Users")
                                         .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                         .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                             @Override
                                             public void onComplete(@NonNull Task<Void> task) {

                                                if(task.isSuccessful()){
                                                    Toast.makeText(RegisterUser.this, "User has been registered successfully!", Toast.LENGTH_LONG).show();
                                                    prog_reg.setVisibility(View.GONE);


                                                    // redirect
                                                }
                                                else{
                                                    Toast.makeText(RegisterUser.this, "Failed to register", Toast.LENGTH_LONG).show();
                                                    prog_reg.setVisibility(View.GONE);

                                                }

                                             }
                                         });
                             }else{
                                 Toast.makeText(RegisterUser.this, "Failed to register", Toast.LENGTH_LONG).show();
                                 prog_reg.setVisibility(View.GONE);
                             }
                         }
                     });

            }

        }


    public void Back_to_login(View view) {
        FullName.setText("");
        Age.setText("");
        Email_signup.setText("");
        Password_signup.setText("");
        startActivity(new Intent(RegisterUser.this, MainActivity.class));
    }
}
