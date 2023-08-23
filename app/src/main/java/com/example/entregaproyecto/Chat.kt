package com.example.entregaproyecto

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.entregaproyecto.databinding.ActivityChatBinding

class Chat: AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}
