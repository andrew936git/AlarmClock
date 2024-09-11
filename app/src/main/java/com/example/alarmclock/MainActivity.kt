package com.example.alarmclock

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.AlarmManager.RTC_WAKEUP
import android.app.PendingIntent
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private var calendar: Calendar? = null
    private var materialTimePicker: MaterialTimePicker? = null

    private lateinit var startButtonBT: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        init()
    }

    @SuppressLint("SetTextI18n")
    private fun init(){
        val toolbar = findViewById<Toolbar>(R.id.toolbarAlarmStart)
        title = "Будильник"
        setSupportActionBar(toolbar)

        startButtonBT = findViewById(R.id.startButtonBT)
        startButtonBT.setOnClickListener {
            materialTimePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Установите время")
                .build()
            materialTimePicker!!.addOnPositiveButtonClickListener {
                calendar = Calendar.getInstance()
                calendar?.set(Calendar.SECOND, 0)
                calendar?.set(Calendar.MILLISECOND, 0)
                calendar?.set(Calendar.MINUTE, materialTimePicker!!.minute)
                calendar?.set(Calendar.HOUR_OF_DAY, materialTimePicker!!.hour)

                val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

                val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

                alarmManager.setExact(
                    RTC_WAKEUP,
                    calendar!!.timeInMillis,
                    getAlarmPendingIntent()
                    )
                Toast.makeText(
                    this,
                    "Будильник установлен на ${dateFormat.format(calendar!!.time)}",
                    Toast.LENGTH_LONG).show()
            }
            materialTimePicker!!.show(supportFragmentManager, "tag_picker")
        }
    }

    private fun getAlarmPendingIntent(): PendingIntent {
        val intent = Intent(this, AlarmReceiver::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        intent.setAction("alarm wake up at " + calendar?.getTimeInMillis())
        return PendingIntent.getBroadcast(
            this,
            1,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.exit_menu -> {
                Toast.makeText(
                    applicationContext,
                    "Программа завершена",
                    Toast.LENGTH_LONG
                ).show()
                finishAffinity()
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}