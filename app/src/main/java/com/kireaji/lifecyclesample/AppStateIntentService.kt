package com.kireaji.lifecyclesample

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.app.JobIntentService
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ProcessLifecycleOwner

/**
 * 3秒後にforegroundかbackgroundかトースト表示
 */
class AppStateIntentService: JobIntentService() {

    fun enqueueWork(context: Context, work: Intent) {
        enqueueWork(context, AppStateIntentService::class.java, JOB_ID, work)
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
    }

    override fun onHandleWork(intent: Intent) {
        (0..3).forEach {
            Thread.sleep(1000)
            Log.d(TAG, "onHandleWork count:$it")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")

        val isForeground = ProcessLifecycleOwner.get().lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)
        Toast.makeText(
            applicationContext,
            if (isForeground) "foreground" else "background",
            Toast.LENGTH_SHORT
        ).show()
    }

    companion object {
        val TAG = AppStateIntentService::class.java.simpleName
        const val JOB_ID = 1000
    }
}