package com.example.alphaversion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 @author Ilai Shimoni ilaishimoni@gmail.com
 @version 1.0
 @since 3/11/22
this activity shows information about signed-in user and shows details
 */

public class ProfileActivity extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;

    private String userID;

    TextView NameAfterLog;
    TextView AgeAfterLog;
    TextView EmailAfterLog;
    TextView PasswordAfterLog;

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        // Inflate the menu;
        getMenuInflater().inflate(R.menu.optionsmenu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toast.makeText(ProfileActivity.this, "data being uploaded...", Toast.LENGTH_SHORT).show();


        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        NameAfterLog = (TextView) findViewById(R.id.NameAfterLog);
        AgeAfterLog = (TextView) findViewById(R.id.AgeAfterLog);
        EmailAfterLog = (TextView) findViewById(R.id.EmailAfterLog);
        PasswordAfterLog = (TextView) findViewById(R.id.PasswordAfterLog);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null){
                    String fullName = userProfile.FullName;
                    String Email = userProfile.Email;
                    String Age = userProfile.Age;
                    String Password = userProfile.Password;

                    NameAfterLog.setText(fullName);
                    EmailAfterLog.setText(Email);
                    AgeAfterLog.setText(Age);
                    PasswordAfterLog.setText(Password);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "something went wrong!", Toast.LENGTH_LONG).show();

            }
        });

    }

    public void Logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(ProfileActivity.this, MainActivity.class));
    }

    public void auth(MenuItem item) {
        startActivity(new Intent(ProfileActivity.this, MainActivity.class));
    }

    public void storage(MenuItem item) {
        startActivity(new Intent(ProfileActivity.this, ast_for_storage.class));
    }

    public void change(MenuItem item) {
        startActivity(new Intent(ProfileActivity.this, chnage_by_color.class));
    }

    public void color(MenuItem item) {
        startActivity(new Intent(ProfileActivity.this, chnage_by_color.class));

    }

    public void notification(MenuItem item) {
        startActivity(new Intent(ProfileActivity.this, not.class));

    }
}