package com.example.entregaproyecto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.example.entregaproyecto.databinding.ActivityPerfilBinding

class Perfil : AppCompatActivity() {
    private lateinit var binding: ActivityPerfilBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_perfil)
    }
}