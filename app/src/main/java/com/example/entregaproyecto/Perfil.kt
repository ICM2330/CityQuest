package com.example.entregaproyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.example.entregaproyecto.databinding.ActivityPerfilBinding

class Perfil : AppCompatActivity() {
    private lateinit var binding: ActivityPerfilBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.botonAmgigos.setOnClickListener{

            startActivity(Intent(baseContext,Friends::class.java))
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