package com.example.cityquest.util

import android.app.AlertDialog
import android.app.Application
import android.util.Log
import android.widget.Toast
import com.example.cityquest.activities.WelcomeActivity
import com.parse.FunctionCallback
import com.parse.Parse
import com.parse.ParseCloud
import com.parse.ParseInstallation
import com.parse.ParsePush
import com.parse.ParseUser
import com.parse.livequery.ParseLiveQueryClient
import okhttp3.OkHttpClient


class ParseConn : Application() {
    override fun onCreate() {
        super.onCreate()
        Parse.initialize(
            Parse.Configuration.Builder(this)
                .applicationId("tHW5TLLoDiIlNQh75oW5kJ0y4joxdm5KhiYQ00aS")
                .clientKey("6O7O7t3lidiYXjG9n9CIh1fELdBV53xLnHqB0Rok")
                .server(IP)
                .build()
        )

        // Save the installation object in the background
        ParseInstallation.getCurrentInstallation().saveInBackground { e ->
            if (e == null) {
                Log.d("ParseConn", "Installation saved successfully")
            } else {
                Log.e("ParseConn", "Error saving installation: ${e.localizedMessage}")
            }
        }

        // Subscribe to the push channel
        ParsePush.subscribeInBackground("cityquest")

        // Set Parse log level to debug for troubleshooting
        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG)
    }





    companion object {
        const val IP = "https://cityquest.b4a.io/parse/"
    }
}