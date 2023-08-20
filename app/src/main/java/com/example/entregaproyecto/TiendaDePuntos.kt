package com.example.entregaproyecto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.entregaproyecto.databinding.ActivityTiendaDePuntosBinding

class TiendaDePuntos : AppCompatActivity() {
    private lateinit var binding: ActivityTiendaDePuntosBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTiendaDePuntosBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}