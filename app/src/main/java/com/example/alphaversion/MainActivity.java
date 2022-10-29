package com.example.alphaversion;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    Intent MainActivity;

    EditText Email_login;
    EditText Password_login;

    private FirebaseAuth myAuth;
    ProgressBar prog_sign;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Email_login = (EditText) findViewById(R.id.Email_login);
        Password_login = (EditText) findViewById(R.id.Password_login);
        prog_sign = (ProgressBar) findViewById(R.id.prog_sign);

        myAuth = FirebaseAuth.getInstance();




    }

    public void Register_signin (View view) {
        MainActivity = new Intent(this , RegisterUser.class);
        Email_login.setText("");
        Password_login.setText("");
        startActivity(MainActivity);
    }

    public void Login(View view) {
        String Email = Email_login.getText().toString().trim();
        String Password = Password_login.getText().toString().trim();

        if(Email.isEmpty()){
            Email_login.setError("Email is required");
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            Email_login.setError("Please enter a valid email");
        }
        else if(Password.isEmpty()){
            Password_login.setError("Password is required!");
        }
        else if(Password.length() < 6){
            Password_login.setError("Password must contain at least 8 characters");
        }
        // check if user exists
        else{
            prog_sign.setVisibility(View.VISIBLE);
            myAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        FirebaseAuth auth = FirebaseAuth.getInstance();
                        FirebaseUser user = auth.getCurrentUser();

                        if(user.isEmailVerified()){
                            //redirect to user profile
                            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                        }
                        else{
                            auth.getCurrentUser().sendEmailVerification()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        Toast.makeText(MainActivity.this, "Email sent", Toast.LENGTH_LONG).show();

                                                    }
                                                }
                                            });
                            //Toast.makeText(MainActivity.this, "Verify account by confirming your email", Toast.LENGTH_LONG).show();
                        }

                    }
                    else{
                        Toast.makeText(MainActivity.this, "Failed to login, please check credentials", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }

    public void ForgotPassword(View view) {
        startActivity(new Intent(MainActivity.this, ForgotPassword.class));
    }
}