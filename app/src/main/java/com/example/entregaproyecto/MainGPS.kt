package com.example.entregaproyecto


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.entregaproyecto.R.id.action_menu
import com.example.entregaproyecto.R.id.action_settings
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

        binding.subirFoto.setOnClickListener {
            startActivity(Intent(baseContext, NuevaLocacion::class.java))
        }

        binding.NotificacionesUsuario.setOnClickListener {
            startActivity(
                Intent(
                    baseContext, MainActivity::class.java
                )
            )
            TODO("Realizar pantalla de notificaciones")
        }

        binding.TiendaDePuntos.setOnClickListener {

            startActivity(Intent(baseContext, TiendaDePuntos::class.java))
        }



        binding.insertarLocalizacion.setOnClickListener {
            startActivity(Intent(baseContext, CercaDeTi::class.java))
        }

        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {

                R.id.action_menu -> {

                    val intent = Intent(this, MainActivity::class.java)

                    startActivity(intent)

                    true
                }

                R.id.action_settings -> {
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
        setContentView(binding.root)

        return true
    }
}
