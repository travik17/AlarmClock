package com.example.travik.wekker.Alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.example.travik.wekker.BaseActivity;
import com.example.travik.wekker.MainActivity;
import com.example.travik.wekker.R;

import java.util.Calendar;

public class AlarmActivity extends BaseActivity {

    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private TimePicker timePicker;
    private static  AlarmActivity alarmActivity;
    private TextView alarmTextView;

    public static AlarmActivity instance(){
        return alarmActivity;
    }

    @Override
    public void onStart(){
        super.onStart();
        alarmActivity = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_alarm_clock);
        timePicker = (TimePicker) findViewById(R.id.alarmTimePicker);
        alarmTextView = (TextView) findViewById(R.id.alarmText);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    }

    public void onToggleClicked(View view){
        if(((ToggleButton) view).isChecked()){
            Log.d("AlarmActivity", "Alarm On");

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
            calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());


            Integer AddTime = MainActivity.PrepTime * 60000;

            Intent myIntent = new Intent(AlarmActivity.this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this, 0, myIntent, 0);
            alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis() + AddTime, pendingIntent);
        } else {
            Log.d("AlarmActivity", "Alarm off");
            alarmManager.cancel(pendingIntent);
            setAlarmText("");
        }
    }

    public void setAlarmText(String alarmText){
        alarmTextView.setText(alarmText);

    }

}
