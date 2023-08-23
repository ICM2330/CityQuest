package com.example.entregaproyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem

import com.example.entregaproyecto.databinding.ActivityPerfilBinding

class Perfil : AppCompatActivity() {
    private lateinit var binding: ActivityPerfilBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {

                R.id.action_menu -> {
                    Log.d("MenuDebug", "Menu item selected: ${item.itemId}")
                    val intent = Intent(this, MainActivity::class.java)

                    startActivity(intent)

                    true
                }

                R.id.action_settings -> {
                    Log.d("MenuDebug", "Menu item selected: ${item.itemId}")
                    val intent = Intent(this, Settings::class.java)
                    startActivity(intent)
                    true

                }


                else -> false
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)

        return true
    }
}
