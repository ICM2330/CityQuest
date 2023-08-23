package com.example.entregaproyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.entregaproyecto.databinding.ActivityIrBinding

class IrA : AppCompatActivity() {

    private lateinit var binding: ActivityIrBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIrBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ir.setOnClickListener{
            startActivity(Intent(baseContext,MainGPS::class.java))
        }
    }


}