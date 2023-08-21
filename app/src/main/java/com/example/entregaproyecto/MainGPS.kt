package com.example.entregaproyecto


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.entregaproyecto.databinding.ActivityMainGpsBinding


class MainGPS : AppCompatActivity() {
    private lateinit var binding: ActivityMainGpsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainGpsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.PerfilUsuario.setOnClickListener {
            startActivity(Intent(baseContext, Perfil::class.java))
        }

        binding.NotificacionesUsuario.setOnClickListener {
            TODO("Realizar pantalla de notificaciones")
        }

        binding.TiendaDePuntos.setOnClickListener {
            startActivity(Intent(baseContext, TiendaDePuntos::class.java))
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        val home = binding.toolbar.menu.findItem(R.id.action_home)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_home->{
                openHome()
                return true
            }
            R.id.action_settings ->{
                openSettings()
                return true
            }

            else -> return super.onOptionsItemSelected(item)

        }
    }

    private fun openHome (){
        startActivity(Intent(this,MainGPS::class.java))
    }

    private fun openSettings(){
        startActivity(Intent(this,Settings::class.java))
    }
}