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
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.cityquest.R
import com.example.cityquest.adapters.UserAdapter
import com.example.cityquest.databinding.ActivityChatListBinding
import com.example.cityquest.models.User
import com.google.firebase.Firebase
import com.google.firebase.appcheck.appCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import com.google.firebase.initialize
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
import com.parse.livequery.ParseLiveQueryClient
import com.parse.livequery.SubscriptionHandling

class ChatListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatListBinding
    private lateinit var userAdapter: UserAdapter
    private var userList = mutableListOf<User>()
    private lateinit var liveQueryClient: ParseLiveQueryClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setupListView()
        loadUsersFromParse()
        initLiveQuery()

        Firebase.initialize(context = this)
        Firebase.appCheck.installAppCheckProviderFactory(
            DebugAppCheckProviderFactory.getInstance(),
        )
    }

    private fun setupListView() {
        userAdapter = UserAdapter(this, userList)
        binding.listView.adapter = userAdapter
    }

    private fun loadUsersFromParse() {
        val query: ParseQuery<ParseUser> = ParseUser.getQuery()
        query.findInBackground { users, e ->
            if (e == null) {
                userList.clear()
                users?.forEach { parseUser ->
                    val user = User(
                        parseUser.get("name") as? String,
                        parseUser.objectId,
                        parseUser.username
                    )
                    if (parseUser.get("name") as? String != null)
                        userList.add(user)
                }
                userAdapter.notifyDataSetChanged()
            } else {
                val og = null
                Log.e("ChatListActivity", "Error al cargar usuarios: ${e.localizedMessage}")
                Toast.makeText(this, "Error al cargar usuarios: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
            }
        }
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
                sendNotification(this, this@ChatListActivity::class.java, contenidoMensaje, "¡Nuevo mensaje!")
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