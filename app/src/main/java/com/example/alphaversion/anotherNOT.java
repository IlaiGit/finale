package com.example.alphaversion;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Locale;

public class anotherNOT extends AppCompatActivity {

    Button timeButton;
    int hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another_not);

        timeButton = findViewById(R.id.timeButton);
    }

    public void popTimePicker(View view)
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
            {
                hour = selectedHour;
                minute = selectedMinute;
                timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d",hour, minute));
            }
        };

        // int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, /*style,*/ onTimeSetListener, hour, minute, true);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // Inflate the menu;
        getMenuInflater().inflate(R.menu.optionsmenu, menu);
        return true;
    }

    public void timeToast(MenuItem item) {
        startActivity(new Intent(anotherNOT.this, anotherNOT.class));
    }

    public void notification(MenuItem item) {
        startActivity(new Intent(anotherNOT.this, not.class));
    }

    public void color(MenuItem item) {
        startActivity(new Intent(anotherNOT.this, chnage_by_color.class));
    }

    public void storage(MenuItem item) {
        startActivity(new Intent(anotherNOT.this, ast_for_storage.class));
    }

    public void auth(MenuItem item) {
        startActivity(new Intent(anotherNOT.this, MainActivity.class));
    }

    public void show(View view) {
        Toast.makeText(this, timeButton.getText().toString(), Toast.LENGTH_SHORT).show();
    }

    public void Graph(MenuItem item) {
        startActivity(new Intent(anotherNOT.this, simpleGraph.class));
    }
}