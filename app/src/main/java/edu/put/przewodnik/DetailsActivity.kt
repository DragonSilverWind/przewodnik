package edu.put.przewodnik

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.RelativeLayout
import androidx.appcompat.widget.Toolbar
import edu.put.przewodnik.MainActivity
import kotlin.String
import android.widget.Toast
import android.content.Intent
import androidx.constraintlayout.widget.ConstraintLayout
import android.text.method.ScrollingMovementMethod
import android.view.Gravity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton




class DetailsActivity : AppCompatActivity() {
    lateinit var bottomNav: BottomNavigationView
    private lateinit var textview: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val selectedTrailName: String = intent.getStringExtra("selectedItem") ?: ""

        // Ustawienie tekstu w TextView na nazwę szlaku
        val trailNameTextView = findViewById<TextView>(R.id.nazwaSzlaku)
        trailNameTextView.text = selectedTrailName


        val trailsMap = mapOf(
            "Szlak Orlich Gniazd" to "Jeden z najstarszym polskich szlaków turystycznych. Przebiega przez województwo śląskie i małopolskie łącząc Częstochowę z Krakowem. \n\n" +
                    "Kolor szlaku: czerwony \nDługość szlaku: 190km \n\nPrzebieg szlaku:\n" +
                    "Częstochowa – Olsztyn (18km) – Złoty Potok (36km) – Żarki (48km) – Mirów (56km) – Podlesice (69km) – Zawiercie Kromołów (80km) – Podzamcze (86km) – Smoleń (98km) – Bydlin (110km) – Rabsztyn (124km) – Olkusz (127km) – Krzeszowice (153km) – Brzoskwinia (170km) – Kraków",
            "Mały Szlak Beskidzki" to "Pewnie idzie przez Beskidy lol",
            "Karpacki Szlak Rowerowy" to "eeeeeeeee",
            "Szlak 3" to "Opis szlaku 3"
        )

        val szlakiObrazkiMap = mapOf(
            "Szlak Orlich Gniazd" to R.drawable.szlak_orlich_gniazd,
            "Mały Szlak Beskidzki" to R.drawable.maly_szlak_beskidzki,
            "Szlak 1" to R.drawable.camera_icon
            // Dodaj więcej par klucz-wartość w zależności od potrzeb
        )

        val selectedTrailDescription = trailsMap[selectedTrailName]
        trailNameTextView.text = selectedTrailName

        val trailDescriptionTextView = findViewById<TextView>(R.id.textView2)
        trailDescriptionTextView.text = selectedTrailDescription
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            showToast("Zrobiono zdjęcie")
        }

        val layout = findViewById<ConstraintLayout>(R.id.constraintLayout)
        layout.setOnTouchListener(object : OnSwipeTouchListener(this@DetailsActivity) {
            override fun onSwipeLeft() {
                super.onSwipeUp()
                // Przejdź do MainActivity
                val intent = Intent(this@DetailsActivity, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                startActivity(intent)
                finish()
            }
        })

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
        textview = findViewById(R.id.textView2)
        textview.text = selectedTrailDescription

        // to perform the movement action
        // Moves the cursor or scrolls to the
        // top or bottom of the document
        textview.movementMethod = ScrollingMovementMethod()

    }
    private fun showToast(message: String) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        // Ustawienie pozycji Toast nad paskiem nawigacji
        toast.setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 500)
        toast.show()
    }
}