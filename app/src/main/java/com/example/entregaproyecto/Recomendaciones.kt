package com.example.entregaproyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {

                R.id.action_menu -> {
                    Log.d("MenuDebug", "Menu item selected: ${item.itemId}")
                    val intent = Intent(this, MainActivity::class.java)

                    startActivity(intent)

                    true
                }

                R.id.action_settings -> {
                    Log.d("MenuDebug", "Menu item selected: ${item.itemId}")
                    val intent = Intent(this, Settings::class.java)
                    startActivity(intent)
                    true

                }


                else -> false
            }

        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)

        return true
    }
}