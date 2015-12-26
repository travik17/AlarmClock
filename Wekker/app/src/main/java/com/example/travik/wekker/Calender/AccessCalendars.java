package com.example.travik.wekker.Calender;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.CalendarContract;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.travik.wekker.MainActivity;
import com.example.travik.wekker.R;

import java.util.ArrayList;
import java.util.List;

public class AccessCalendars extends MainActivity{

    public AccessCalendars(){
    }

    public static void GetCalender(Context context){
        String[] projection =
                new String[]{
                        CalendarContract.Calendars._ID,
                        CalendarContract.Calendars.NAME,
                        CalendarContract.Calendars.ACCOUNT_NAME,
                        CalendarContract.Calendars.ACCOUNT_TYPE
                };

        List<Long> id = new ArrayList<>();
        List<String> DisplayName = new ArrayList<>();
        List<String> accountName = new ArrayList<>();
        List<String> ownerName = new ArrayList<>();
        Integer i = 0;

        if (Build.VERSION.SDK_INT >= 23){
            int hasReadPermission = context.checkSelfPermission(android.Manifest.permission.READ_CALENDAR);
            if(hasReadPermission != PackageManager.PERMISSION_GRANTED){
                return;
            }
        }

        Cursor calCursor =
                context.getContentResolver().query(
                        CalendarContract.Calendars.CONTENT_URI,
                        projection,
                        CalendarContract.Calendars.VISIBLE + " = 1",
                        null,
                        CalendarContract.Calendars._ID + " ASC");

        if(calCursor.moveToFirst()){
            do{
                id.add(calCursor.getLong(0));
                DisplayName.add(calCursor.getString(1));
                accountName.add(calCursor.getString(2));
                ownerName.add(calCursor.getString(3));
                i++;
            } while (calCursor.moveToNext());
        }
        populateCalendarSpinner(DisplayName);

        calCursor.close();
    }

    public static void populateCalendarSpinner(List<String> DisplayName){
        Spinner spinner = (Spinner) Activity.findViewById(R.id.spinner_calendar);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, DisplayName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}

