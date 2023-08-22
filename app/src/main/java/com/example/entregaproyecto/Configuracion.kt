package com.example.entregaproyecto

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.entregaproyecto.databinding.ActivityConfiguracionBinding

class Configuracion : AppCompatActivity() {
    private lateinit var binding: ActivityConfiguracionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfiguracionBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
