package com.example.entregaproyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
}