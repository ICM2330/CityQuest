package com.example.cityquest.activities

import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.cityquest.R
import com.example.cityquest.databinding.ActivityWelcomeBinding
import com.parse.FunctionCallback
import com.parse.ParseCloud
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
import com.parse.livequery.ParseLiveQueryClient
import com.parse.livequery.SubscriptionHandling

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityWelcomeBinding
    private lateinit var liveQueryClient: ParseLiveQueryClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.loginButton.setOnClickListener {
            startActivity(Intent(baseContext, LoginActivity::class.java))
        }

        binding.signUpButton.setOnClickListener {
            startActivity(Intent(baseContext, RegisterActivity::class.java))
        }

        val params = HashMap<String, String>()

        ParseCloud.callFunctionInBackground("pushsample", params,
            FunctionCallback<Object> { response, exc ->
                if (exc == null) {
                    //alertDisplayer("Successful Push", "Check on your phone the notifications to confirm!")
                } else {
                    //Toast.makeText(this@WelcomeActivity, exc.message, Toast.LENGTH_LONG).show()
                }
            })
    }
}