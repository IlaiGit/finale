package com.example.alphaversion;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class chnage_by_color extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView LV;
    ArrayList<String> myArrayList = new ArrayList<>();
    LinearLayout RL;

    String value;
    String key;

    DatabaseReference mRef;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chnage_by_color);
        RL = (LinearLayout) findViewById(R.id.RL);
        ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(chnage_by_color.this, android.R.layout.simple_list_item_1, myArrayList);
        LV = (ListView) findViewById(R.id.LV);
        LV.setAdapter(myArrayAdapter);
        LV.setOnItemClickListener(this);


        mRef = FirebaseDatabase.getInstance().getReference().child("Colors");
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                value = snapshot.getValue(String.class);
                key = (String) snapshot.getKey();
                myArrayList.add(value + " " + key);
                myArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                myArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String RAL = myArrayList.get(i);
        String[] split = RAL.split(" ");

        RL.setBackgroundColor(Color.parseColor(split[1]));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // Inflate the menu;
        getMenuInflater().inflate(R.menu.optionsmenu, menu);
        return true;
    }
    public void color(MenuItem item) {
        startActivity(new Intent(chnage_by_color.this, chnage_by_color.class));
    }
    public void notification(MenuItem item) {
        startActivity(new Intent(chnage_by_color.this, not.class));
    }
    public void auth(MenuItem item) {
        startActivity(new Intent(chnage_by_color.this, MainActivity.class));
    }

    public void storage(MenuItem item) {
        startActivity(new Intent(chnage_by_color.this, ast_for_storage.class));
    }

    public void timeToast(MenuItem item) {
        startActivity(new Intent(chnage_by_color.this, anotherNOT.class));
    }

    public void Graph(MenuItem item) {
        startActivity(new Intent(chnage_by_color.this, simpleGraph.class));
    }
}