package com.example.alphaversion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class not extends AppCompatActivity {

    EditText Et;
    TextView TimeTV;

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    Calendar calendar;

    int hours;
    int minutes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not);


        Et = (EditText) findViewById(R.id.ET);
        TimeTV = (TextView) findViewById(R.id.TimeTV);

        // made for later android version devices to make sure all types of phones get notification regardless their version
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("my notification","my notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }


    public void notification(View view) {
        String showText = Et.getText().toString().trim();
        if (showText.isEmpty()){
            Et.setError("please fill in a message");
        }
        else{
            NotificationCompat.Builder builder = new NotificationCompat.Builder(not.this, "my notification");
            builder.setContentTitle("Smarqium");
            builder.setContentText(showText);
            builder.setSmallIcon(R.drawable.ic_launcher_background);
            builder.setAutoCancel(true);

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(not.this);
            managerCompat.notify(1, builder.build());
        }
    }

    public void pickTime(View view) {
        calendar = Calendar.getInstance();
        hours = calendar.get(Calendar.HOUR_OF_DAY);
        minutes = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(not.this, androidx.appcompat.R.style.Theme_AppCompat, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                Calendar c = Calendar.getInstance();
                c.set(calendar.HOUR_OF_DAY, hourOfDay);
                c.set(Calendar.MINUTE, minute);
                c.setTimeZone(TimeZone.getDefault());
                SimpleDateFormat format = new SimpleDateFormat("k:mm a");
                String time = format.format(c.getTime());
                TimeTV.setText(time);

            }
        },hours, minutes, false);
        timePickerDialog.show();

    }

    public void setAlarm(View view) {

        if(TimeTV.getText().equals("use this button to set clock")){
            Toast.makeText(this, "please pick hour", Toast.LENGTH_SHORT).show();
        }
        else{
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            Intent intent = new Intent(this, AlarmReceiver.class);

            pendingIntent = pendingIntent.getBroadcast(not.this, 0, intent, 0);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent);

            Toast.makeText(this, "Alarm set Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public void cancel(View view) {
        Intent intent = new Intent(this, AlarmReceiver.class);

        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        if(alarmManager == null){
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        }

        alarmManager.cancel(pendingIntent);
        Toast.makeText(this, "Alarm Cancelled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // Inflate the menu;
        getMenuInflater().inflate(R.menu.optionsmenu, menu);
        return true;
    }
    public void color(MenuItem item) {
        startActivity(new Intent(not.this, chnage_by_color.class));
    }
    public void notification(MenuItem item) {
        startActivity(new Intent(not.this, not.class));
    }
    public void auth(MenuItem item) {
        startActivity(new Intent(not.this, MainActivity.class));
    }

    public void storage(MenuItem item) {
        startActivity(new Intent(not.this, ast_for_storage.class));
    }


    public void timeToast(MenuItem item) {
        startActivity(new Intent(not.this, anotherNOT.class));

    }

    public void Graph(MenuItem item) {
        startActivity(new Intent(not.this, simpleGraph.class));

    }
}