package com.example.entregaproyecto

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.entregaproyecto.databinding.ActivityConfiguracionBinding

class Configuracion : AppCompatActivity() {
    private lateinit var binding: ActivityConfiguracionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfiguracionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tiendaDePuntosConf.setOnClickListener {
            startActivity(Intent(baseContext,TiendaDePuntos::class.java))
        }

        binding.notificacionesUsuarioConf.setOnClickListener {
            startActivity(Intent(baseContext,Notificaciones::class.java))
        }

        binding.perfilUsuarioConf.setOnClickListener {
            startActivity(Intent(baseContext,Perfil::class.java))
        }

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
}