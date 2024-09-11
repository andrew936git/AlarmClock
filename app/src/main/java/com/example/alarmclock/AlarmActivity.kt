package com.example.alarmclock

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import kotlin.system.exitProcess
import kotlin.collections.ArrayList as ArrayList

class AlarmActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_alarm)
        init()
    }

    private fun init() {
        val toolbarAlarmStop = findViewById<Toolbar>(R.id.toolbarAlarmStop)
        setSupportActionBar(toolbarAlarmStop)
        val stopButtonIV = findViewById<ImageView>(R.id.stopButtonIV)

        stopButtonIV.setOnClickListener {
            finish()
            exitProcess(0)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
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