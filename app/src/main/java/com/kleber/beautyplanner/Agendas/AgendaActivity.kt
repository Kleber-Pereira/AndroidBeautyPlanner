package com.kleber.beautyplanner.Agendas

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.kleber.beautyplanner.R

class AgendaActivity : AppCompatActivity() {
    var customCalendarView: CustomCalendarView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agenda)
        customCalendarView = findViewById<View>(R.id.custom_calendar_view) as CustomCalendarView
    }
}