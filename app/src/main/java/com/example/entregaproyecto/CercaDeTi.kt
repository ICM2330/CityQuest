package com.example.entregaproyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.entregaproyecto.databinding.ActivityCercaDeTiBinding

class CercaDeTi : AppCompatActivity() {

    private lateinit var binding: ActivityCercaDeTiBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityCercaDeTiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.SeleccionDeLocacion.setOnClickListener{

            startActivity(Intent(baseContext,IrA::class.java))
        }
    }
}