package edu.put.przewodnik

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.daimajia.androidanimations.library.YoYo
import com.daimajia.androidanimations.library.Techniques
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    lateinit var bottomNav: BottomNavigationView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonK1 = findViewById<Button>(R.id.buttonKategoria1)
        buttonK1.setOnClickListener {
            val intent = Intent(this, Kategoria1Activity::class.java)
            startActivity(intent)
        }

        val buttonK2 = findViewById<Button>(R.id.buttonKategoria2)
        buttonK2.setOnClickListener {
            val intent = Intent(this, Kategoria2Activity::class.java)
            startActivity(intent)
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
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Dostosuj interfejs użytkownika dla orientacji poziomej
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // Dostosuj interfejs użytkownika dla orientacji pionowej
        }
    }

}

