package edu.put.przewodnik


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.widget.ImageView
import android.content.BroadcastReceiver
import android.content.IntentFilter
import android.text.format.Time
import com.google.android.material.bottomnavigation.BottomNavigationView
import edu.put.przewodnik.databinding.ActivityMainBinding
import kotlin.math.roundToInt


class StoperActivity : AppCompatActivity() {

    lateinit var bottomNav: BottomNavigationView

    private lateinit var timeTextView: TextView
    private lateinit var startStopButton: Button
    private lateinit var resetButton: Button
    private lateinit var binding: ActivityMainBinding

    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    private var isRunning = false
    private var secondsElapsed = 0
    private lateinit var startStopButtonIcon: ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stoper)

        timeTextView = findViewById(R.id.timeTV)
        startStopButton = findViewById(R.id.starStopButton)
        resetButton = findViewById(R.id.resetButton)



        handler = Handler(Looper.getMainLooper())

        startStopButton.setOnClickListener {
            isRunning = !isRunning
            if (isRunning) {
                startStopButton.text = "Stop"
                startTimer()
            } else {
                startStopButton.text = "Start"
                stopTimer()
            }
        }

        resetButton.setOnClickListener {
            isRunning = false
            secondsElapsed = 0
            startStopButton.text = "Start"
            updateTime()
        }
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        bottomNav = findViewById(R.id.bottomNav) as BottomNavigationView
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    // Przechodzenie do nowej aktywności zamiast ładowania fragmentu
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.stoper -> {
                    // Przechodzenie do nowej aktywności zamiast ładowania fragmentu
                    val intent = Intent(this, StoperActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    private fun startTimer() {
        val serviceIntent = Intent(this, BackgroundTimerService::class.java)
        serviceIntent.putExtra("secondsElapsed", secondsElapsed)
        startService(serviceIntent)
        startUpdatingTime()
    }

    private fun stopTimer() {
        val serviceIntent = Intent(this, BackgroundTimerService::class.java)
        stopService(serviceIntent)
        stopUpdatingTime()
    }

    private fun startUpdatingTime() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (isRunning) {
                    secondsElapsed++
                    updateTime()
                    handler.postDelayed(this, 1000)
                }
            }
        }, 1000)
    }

    private fun stopUpdatingTime() {
        handler.removeCallbacksAndMessages(null)
    }

    private fun updateTime() {
        val hours = secondsElapsed / 3600
        val minutes = (secondsElapsed % 3600) / 60
        val seconds = secondsElapsed % 60

        val timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds)
        timeTextView.text = timeString
    }

    override fun onPause() {
        super.onPause()
        val sharedPreferences = getSharedPreferences("StoperPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("secondsElapsed", secondsElapsed)
        editor.apply()
    }

    override fun onResume() {
        super.onResume()
        val sharedPreferences = getSharedPreferences("StoperPrefs", Context.MODE_PRIVATE)
        secondsElapsed = sharedPreferences.getInt("secondsElapsed", 0)
        updateTime()
    }


}
