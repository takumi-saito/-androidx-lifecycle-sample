package com.kireaji.lifecyclesample

import android.hardware.*
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * 加速度センサー
 * foreground時、センサーイベントをログ出力
 */
class SensorHandler(
    private val sensorManager: SensorManager
) : SensorEventListener, LifecycleObserver {

    private val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun register() {
        Log.d(TAG, "register")
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun unregister() {
        Log.d(TAG, "unregister")
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event ?: return
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            val sensorX = event.values[0]
            val sensorY = event.values[1]
            val sensorZ = event.values[2]
            val logMessage = "accelerometer X:$sensorX Y:$sensorY Z:$sensorZ"
            Log.d(TAG, logMessage)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    companion object {
        val TAG = SensorHandler::class.java.simpleName
    }
}
