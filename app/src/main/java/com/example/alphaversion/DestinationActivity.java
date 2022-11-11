package com.example.alphaversion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class DestinationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // Inflate the menu;
        getMenuInflater().inflate(R.menu.optionsmenu, menu);
        return true;
    }

    public void timeToast(MenuItem item) {
        startActivity(new Intent(DestinationActivity.this, anotherNOT.class));
    }

    public void notification(MenuItem item) {
        startActivity(new Intent(DestinationActivity.this, not.class));
    }

    public void color(MenuItem item) {
        startActivity(new Intent(DestinationActivity.this, chnage_by_color.class));
    }

    public void storage(MenuItem item) {
        startActivity(new Intent(DestinationActivity.this, ast_for_storage.class));
    }

    public void auth(MenuItem item) {
        startActivity(new Intent(DestinationActivity.this, MainActivity.class));
    }

    public void Graph(MenuItem item) {
        startActivity(new Intent(DestinationActivity.this, simpleGraph.class));
    }
}