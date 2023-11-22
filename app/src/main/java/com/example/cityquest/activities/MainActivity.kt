package com.example.cityquest.activities

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.cityquest.R
import com.example.cityquest.databinding.ActivityMainBinding
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
import com.parse.livequery.ParseLiveQueryClient
import com.parse.livequery.SubscriptionHandling
import np.com.susanthapa.curved_bottom_navigation.CbnMenuItem

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var liveQueryClient: ParseLiveQueryClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val activeIndex = savedInstanceState?.getInt("activeIndex") ?: 0
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_profile,
                R.id.navigation_add_photo
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        val menuItems = arrayOf(
            CbnMenuItem(
                R.drawable.ic_home,
                R.drawable.avd_home,
                R.id.navigation_home
            ),
            CbnMenuItem(
                R.drawable.ic_dashboard,
                R.drawable.avd_dashboard,
                R.id.navigation_dashboard
            ),
            CbnMenuItem(
                R.drawable.ic_add_photo,
                R.drawable.avd_add_photo,
                R.id.navigation_add_photo
            ),
            CbnMenuItem(
                R.drawable.ic_notification,
                R.drawable.avd_notification,
                R.id.navigation_notifications
            ),
            CbnMenuItem(
                R.drawable.ic_profile,
                R.drawable.avd_profile,
                R.id.navigation_profile
            )
        )

        binding.navView.setMenuItems(menuItems, activeIndex)
        binding.navView.setupWithNavController(navController)

        initLiveQuery()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("activeIndex", binding.navView.getSelectedIndex())
        super.onSaveInstanceState(outState)
    }

    private fun initLiveQuery() {
        try {
            liveQueryClient = ParseLiveQueryClient.Factory.getClient()
            Log.d("ChatActivity", "LiveQuery Client iniciado correctamente")
            subscribeToMessages()
        } catch (e: Exception) {
            Log.e("ChatActivity", "Error al iniciar LiveQuery: ${e.localizedMessage}")
        }
    }

    private fun subscribeToMessages() {
        val currentUser = ParseUser.getCurrentUser()
        val query = ParseQuery.getQuery<ParseObject>("Mensaje")
        query.whereEqualTo("id_receptor", currentUser.objectId)
        //query.whereEqualTo("id_emisor", "idChatContrario")

        val subscriptionHandling: SubscriptionHandling<ParseObject> = liveQueryClient.subscribe(query)

        subscriptionHandling.handleEvent(SubscriptionHandling.Event.CREATE) { _, mensaje ->
            runOnUiThread {
                Log.d("ChatActivity", "Nuevo mensaje recibido a través de LiveQuery")
                val contenidoMensaje = mensaje.getString("contenido") ?: ""
                val emisor = mensaje.getString("id_emisor") ?: ""
                sendNotification(this, this@MainActivity::class.java, contenidoMensaje, "¡Nuevo mensaje!")
            }
        }
    }

    private fun sendNotification(context: Context, targetActivity: Class<*>, message: String, title: String) {
        val channelId = "cityquest"

        val notificationManager = context.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Canal de notificaciones", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(context, targetActivity)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.logo)
            .setContentTitle(title)
            .setContentText(message)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        notificationManager.notify(1, builder.build())
    }
}