package com.kleber.beautyplanner.Agenda;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.kleber.beautyplanner.R;

public class AgendaActivity extends AppCompatActivity {
    CustomCalendarView customCalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        customCalendarView = (CustomCalendarView)findViewById(R.id.custom_calendar_view);
    }
}