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
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.bumptech.glide.Glide
import com.example.cityquest.R
import com.example.cityquest.databinding.ActivityChatBinding
import com.parse.ParseCloud
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
import com.parse.livequery.ParseLiveQueryClient
import com.parse.livequery.SubscriptionHandling

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private lateinit var messageEditText: EditText
    private lateinit var chatContainer: LinearLayout
    private var idChatContrario: String = ""
    private lateinit var liveQueryClient: ParseLiveQueryClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val nombreReceptor = intent.getStringExtra("nombre").toString()
        val emailReceptor = intent.getStringExtra("email").toString()
        idChatContrario = intent.getStringExtra("imageUrl").toString()

        binding.recipientNameTextView.text = nombreReceptor
        if (idChatContrario.isNotEmpty()) {
            Glide.with(this).load(idChatContrario).into(binding.profileImageView)
        }

        messageEditText = binding.messageEditText
        chatContainer = binding.chatContainer

        setListeners()
        loadExistingMessages()
        initLiveQuery()
    }

    private fun setListeners() {
        binding.botonBack.setOnClickListener {
            onBackPressed() // Regresa a la actividad anterior
        }

        binding.sendButton.setOnClickListener {
            sendMessage()
        }
    }

    private fun sendMessage() {
        val messageText = messageEditText.text.toString().trim()
        if (messageText.isNotEmpty()) {
            val currentUser = ParseUser.getCurrentUser()
            val mensajeParse = ParseObject("Mensaje")
            mensajeParse.put("contenido", messageText)
            mensajeParse.put("id_emisor", currentUser.objectId)
            mensajeParse.put("id_receptor", idChatContrario)

            mensajeParse.saveInBackground { e ->
                if (e == null) {
                    runOnUiThread {
                        addMessageToChat(messageText, true)
                        sendPushNotification(idChatContrario, messageText)
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(this, "Error al enviar el mensaje: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            messageEditText.text.clear()
        }
    }

    private fun sendPushNotification(recipientUserId: String, message: String) {
        val params = HashMap<String, Any>()
        params["recipientUserId"] = recipientUserId
        params["message"] = message

        ParseCloud.callFunctionInBackground<String>("sendPushNotification", params) { result, e ->
            if (e == null) {
                Log.d("ChatActivity", "Push notification sent successfully")
            } else {
                Log.e("ChatActivity", "Error sending push notification: ${e.message}")
            }
        }
    }

    private fun loadExistingMessages() {
        val currentUser = ParseUser.getCurrentUser()
        val queryEmisorReceptor = ParseQuery.getQuery<ParseObject>("Mensaje")
        queryEmisorReceptor.whereEqualTo("id_emisor", currentUser.objectId)
        queryEmisorReceptor.whereEqualTo("id_receptor", idChatContrario)

        val queryReceptorEmisor = ParseQuery.getQuery<ParseObject>("Mensaje")
        queryReceptorEmisor.whereEqualTo("id_emisor", idChatContrario)
        queryReceptorEmisor.whereEqualTo("id_receptor", currentUser.objectId)

        val combinedQuery = ParseQuery.or(listOf(queryEmisorReceptor, queryReceptorEmisor))
        combinedQuery.orderByAscending("createdAt")

        combinedQuery.findInBackground { mensajes, e ->
            if (e == null) {
                runOnUiThread {
                    mensajes.forEach { mensaje ->
                        val contenidoMensaje = mensaje.getString("contenido") ?: ""
                        val emisor = mensaje.getString("id_emisor") ?: ""
                        addMessageToChat(contenidoMensaje, emisor == currentUser.objectId)
                    }

                    binding.scrollView.post {
                        binding.scrollView.fullScroll(View.FOCUS_DOWN)
                    }
                }
            } else {
                runOnUiThread {
                    Toast.makeText(this, "Error al cargar mensajes: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun addMessageToChat(message: String, isSender: Boolean) {
        val messageTextView = TextView(this)
        messageTextView.text = message
        messageTextView.textAlignment = if (isSender) View.TEXT_ALIGNMENT_TEXT_END else View.TEXT_ALIGNMENT_TEXT_START
        chatContainer.addView(messageTextView)

        binding.scrollView.post {
            binding.scrollView.fullScroll(View.FOCUS_DOWN)
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
        query.whereEqualTo("id_emisor", idChatContrario)

        val subscriptionHandling: SubscriptionHandling<ParseObject> = liveQueryClient.subscribe(query)

        subscriptionHandling.handleEvent(SubscriptionHandling.Event.CREATE) { _, mensaje ->
            runOnUiThread {
                Log.d("ChatActivity", "Nuevo mensaje recibido a través de LiveQuery")
                val contenidoMensaje = mensaje.getString("contenido") ?: ""
                val emisor = mensaje.getString("id_emisor") ?: ""
                sendNotification(this, this@ChatActivity::class.java, contenidoMensaje, "¡Nuevo mensaje!")
                addMessageToChat(contenidoMensaje, emisor == currentUser.objectId)

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