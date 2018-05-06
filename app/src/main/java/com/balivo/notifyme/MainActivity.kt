package com.balivo.notifyme

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var mNotifyManager: NotificationManager
    private val NOTIFICATION_ID : Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mNotifyManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val mNotifyButton = findViewById(R.id.notify) as Button
        mNotifyButton.setOnClickListener(object: View.OnClickListener {

            override fun onClick(p0: View?) {
                sendNotification()
            }

        });
    }


    fun sendNotification() {

        val notificationIntent = Intent(this, MainActivity::class.java)

        val notificationPendingIntent = PendingIntent.getActivity(this,
                NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notifyBuilder = NotificationCompat.Builder(this)
                .setContentTitle("You've been notified!")
                .setContentText("This is your notification text.")
                .setSmallIcon(R.drawable.ic_android)
                .setContentIntent(notificationPendingIntent)

        val myNotification = notifyBuilder.build()
        mNotifyManager.notify(NOTIFICATION_ID, myNotification)

    }
}
