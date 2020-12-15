package com.kireaji.lifecyclesample

import android.content.Intent
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button_1).setOnClickListener {
            enqueueWork()
        }

        val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        lifecycle.addObserver(SensorHandler(sensorManager))
        lifecycle.addObserver(MyLifecycleObserver())
    }

    private fun enqueueWork() {
        val intent = Intent(this, AppStateIntentService::class.java)
        AppStateIntentService().enqueueWork(this, intent)
    }
}