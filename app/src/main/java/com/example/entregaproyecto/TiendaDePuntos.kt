package com.example.entregaproyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.entregaproyecto.databinding.ActivityTiendaDePuntosBinding

class TiendaDePuntos : AppCompatActivity() {
    private lateinit var binding: ActivityTiendaDePuntosBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTiendaDePuntosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_settings -> {
                    val intent = Intent(this, Configuracion::class.java)
                    startActivity(intent)
                    true
                }
                R.id.action_menu -> {
                    val intent = Intent(this, MainGPS::class.java)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.action_settings -> {
                val intent = Intent(baseContext, MainGPS::class.java)
                startActivity(intent)
            }

            R.id.action_home -> {

                val intent = Intent(baseContext, Settings::class.java)
                startActivity(intent)

            }
        }

        return true
    }
}