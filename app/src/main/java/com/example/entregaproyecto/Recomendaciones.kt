package com.example.entregaproyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.entregaproyecto.databinding.ActivityMainGpsBinding
import com.example.entregaproyecto.databinding.ActivityRecomendacionesBinding

class Recomendaciones : AppCompatActivity() {
    private lateinit var binding: ActivityRecomendacionesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecomendacionesBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.action_menu -> {
                val intent = Intent(baseContext, MainGPS::class.java)
                startActivity(intent)
            }

            R.id.action_settings -> {

                val intent = Intent(baseContext, Settings::class.java)
                startActivity(intent)

            }
        }

        return true
    }
}