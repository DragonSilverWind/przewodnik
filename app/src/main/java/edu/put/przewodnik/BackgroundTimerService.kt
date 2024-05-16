package edu.put.przewodnik

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder

class BackgroundTimerService : Service() {

    private var secondsElapsed = 0
    private var isRunning = false
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()

        handler = Handler()
        runnable = object : Runnable {
            override fun run() {
                if (isRunning) {
                    secondsElapsed++
                }
                handler.postDelayed(this, 1000) // Update time every second
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        isRunning = true
        val initialSeconds = intent?.getIntExtra("secondsElapsed", 0) ?: 0
        secondsElapsed = initialSeconds
        handler.post(runnable)
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
        handler.removeCallbacks(runnable)
    }
}
