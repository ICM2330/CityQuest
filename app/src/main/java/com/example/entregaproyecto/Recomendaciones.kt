package com.example.entregaproyecto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.entregaproyecto.databinding.ActivityMainGpsBinding
import com.example.entregaproyecto.databinding.ActivityRecomendacionesBinding

class Recomendaciones : AppCompatActivity() {
    private lateinit var binding: ActivityRecomendacionesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecomendacionesBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}