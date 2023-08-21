package com.example.entregaproyecto


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.entregaproyecto.databinding.ActivityMainGpsBinding


class MainGPS : AppCompatActivity() {
    private lateinit var binding: ActivityMainGpsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainGpsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.PerfilUsuario.setOnClickListener {
            startActivity(Intent(baseContext, Perfil::class.java))
        }

        binding.NotificacionesUsuario.setOnClickListener {startActivity(Intent(baseContext, MainActivity::class.java
        ))
            TODO("Realizar pantalla de notificaciones")
        }

        binding.TiendaDePuntos.setOnClickListener {

            startActivity(Intent(baseContext, TiendaDePuntos::class.java))
        }

        val menuHome = binding.toolbar.menu.getItem(0)
        menuHome.setOnMenuItemClickListener { clickedItem ->
            if (clickedItem.itemId == R.id.action_menu) {
                startActivity(Intent(baseContext, MainActivity::class.java))
                true
            } else {
                false
            }
        }

        val menuSettings = binding.toolbar.menu.getItem(1)
        menuSettings.setOnMenuItemClickListener { clickedItem ->
            if (clickedItem.itemId == R.id.action_settings) {
                startActivity(Intent(baseContext, Settings::class.java))
                true
            } else {
                false
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        setContentView(binding.root)

        return true
    }

   /* override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("MenuDebug", "Menu item selected: ${item.itemId}")

        when (item.itemId) {

            R.id.action_menu -> {
                Log.d("MenuDebug", "Menu item selected: ${item.itemId}")
                val intent = Intent(this, MainActivity::class.java)

                startActivity(intent)

                return true
            }

            R.id.action_settings -> {
                Log.d("MenuDebug", "Menu item selected: ${item.itemId}")
                val intent = Intent(this, Settings::class.java)
                startActivity(intent)
                return true

            }
        }


        return super.onOptionsItemSelected(item)
    }*/
}