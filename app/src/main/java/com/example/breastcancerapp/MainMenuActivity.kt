package com.example.breastcancerapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class MainMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        val backButton: ImageButton? = findViewById(R.id.button_back)
        backButton?.setOnClickListener {
            onBackPressed()
        }
    }

    fun openTentang(view: View) {
        startActivity(Intent(this, TentangActivity::class.java))
    }

    fun openDataset(view: View) {
        startActivity(Intent(this, DatasetActivity::class.java))
    }

    fun openFitur(view: View) {
        startActivity(Intent(this, FiturActivity::class.java))
    }

    fun openModel(view: View) {
        startActivity(Intent(this, ModelActivity::class.java))
    }

    fun openSimulasi(view: View) {
        startActivity(Intent(this, PredictionActivity::class.java))
    }
}
