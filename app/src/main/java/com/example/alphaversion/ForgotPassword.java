package com.example.alphaversion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    EditText recoveryMail;
    ProgressBar progressBar;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        recoveryMail = (EditText) findViewById(R.id.recoveryMail);
        progressBar = (ProgressBar) findViewById(R.id.prog);

        auth = FirebaseAuth.getInstance();
    }

    public void ResetPassword(View view) {
        String email = recoveryMail.getText().toString().trim();

        if(email.isEmpty()){
            recoveryMail.setError("Email is required!");
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            recoveryMail.setError("Please provide a valid Email!");
        }
        else{
            progressBar.setVisibility(View.VISIBLE);
            auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(ForgotPassword.this, "Check email to reset your password!", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);

                    }
                    else{
                        Toast.makeText(ForgotPassword.this, "Something went wrong, try again", Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}