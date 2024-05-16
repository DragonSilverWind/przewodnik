package edu.put.przewodnik

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class Kategoria1Activity : AppCompatActivity() {

    lateinit var bottomNav: BottomNavigationView
    lateinit var wycieczka: ImageView
    lateinit var animator: ObjectAnimator
    val szlakiObrazkiMap = mapOf(
        "Szlak Orlich Gniazd" to R.drawable.szlak_orlich_gniazd,
        "Mały Szlak Beskidzki" to R.drawable.maly_szlak_beskidzki,
        "Szlak 1" to R.drawable.camera_icon
        // Dodaj więcej par klucz-wartość w zależności od potrzeb
    )

    @SuppressLint("MissingInflatedId", "ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kategoria1)

        val arrayAdapter: ArrayAdapter<*>
        val szlakiLista = arrayOf(
            "Szlak Orlich Gniazd",
            "Mały Szlak Beskidzki",
            "Szlak 5",
            "Szlak 4",
            "Szlak 6",
            "Szlak 8",
            "Szlak 7",
            "Szlak 3"
        )

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewSzlaki)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        val szlakiAdapter = SzlakiAdapter(szlakiLista, szlakiObrazkiMap) { selectedItem ->
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("selectedItem", selectedItem)
            startActivity(intent)
        }
        recyclerView.adapter = szlakiAdapter

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        wycieczka = findViewById(R.id.wycieczka)
        startAnimation()

        bottomNav = findViewById(R.id.bottomNav) as BottomNavigationView
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.stoper -> {
                    val intent = Intent(this, StoperActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    private fun startAnimation() {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenWidth = displayMetrics.widthPixels

        // Utwórz animację przesunięcia od lewej do prawej
        animator = ObjectAnimator.ofFloat(wycieczka, "translationX", -500f, screenWidth.toFloat())
        animator.duration = 10000 // Czas trwania animacji w milisekundach
        animator.repeatCount = ObjectAnimator.INFINITE // Powtórz animację w nieskończoność
        animator.start() // Uruchom animację
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        animator.cancel()
        startAnimation()
    }
}
