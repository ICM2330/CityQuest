package com.example.entregaproyecto

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.entregaproyecto.databinding.ActivityFriendsBinding

class Friends: AppCompatActivity() {
    private lateinit var binding: ActivityFriendsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFriendsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.buttonMensajes.setOnClickListener{

            startActivity(Intent(baseContext,Chat::class.java))
        }


    }
}
