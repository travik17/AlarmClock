package com.example.travik.wekker;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    private static boolean FirstStartUP = true;
    private static final String FirstTime = "FirstTime";
    public static Integer PrepTime;
    public static ArrayList<String> nameOfEvent = new ArrayList<>();
    public static ArrayList<String> startDates = new ArrayList<>();
    public static ArrayList<String> endDates = new ArrayList<>();
    public static ArrayList<String> descriptions = new ArrayList<>();

    private static boolean SpinnerDetermine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<String> Events = new ArrayList<>();
        readCalendarEvent(getApplicationContext());
        populateEventSpinner();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putBoolean(FirstTime, FirstStartUP);
        //savedInstanceState.putInt(PrepTimeTag, PrepTime);
        super.onSaveInstanceState(savedInstanceState);
    }

    public void SettingsSpinner(){
        Spinner spinner = (Spinner) findViewById(R.id.SpinnerPrepTime);
        SpinnerDetermine = true;
        spinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.MinutesPrep, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
        if(SpinnerDetermine){
            String text = parent.getItemAtPosition(position).toString();
            ((TextView) findViewById(R.id.PrepTimeText)).setText(text);
            PrepTime = Integer.parseInt(text);
        } else {
            String text = parent.getItemAtPosition(position).toString();
            String startDate = startDates.get(position);
            ((TextView) findViewById(R.id.event)).setText(text);
            ((TextView) findViewById(R.id.EventStart)).setText(startDate);
        }
    }

    public void onNothingSelected(AdapterView<?> arg0){

    }

    public static void readCalendarEvent(Context context){
        ContentResolver resolver = context.getContentResolver();

        Cursor cursor = resolver.query(
                Uri.parse("content://com.android.calendar/events"),
                new String[] {"calendar_id", "title", "description", "dtstart", "dtend", "eventLocation"},
                null, null, null);

        cursor.moveToFirst();

        String CNames[] = new String[cursor.getCount()];

        nameOfEvent.clear();
        startDates.clear();
        endDates.clear();
        descriptions.clear();

        for (int i = 0; i< CNames.length; i++){
            nameOfEvent.add(cursor.getString(1));
            startDates.add(getDate(Long.parseLong(cursor.getString(3))));
            endDates.add((getDate(Long.parseLong(cursor.getString(4)))));
            descriptions.add(cursor.getString(2));
            CNames[i] = cursor.getString(1);
            cursor.moveToNext();
        }
        cursor.close();
    }

    public static String getDate(long milliSeconds){
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return  format.format(calendar.getTime());
    }

    public void populateEventSpinner(){
        Spinner spinner = (Spinner) findViewById(R.id.spinner_calendar);
        SpinnerDetermine = false;
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nameOfEvent);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

}
