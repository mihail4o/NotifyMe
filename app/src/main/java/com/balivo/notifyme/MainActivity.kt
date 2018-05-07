package com.balivo.notifyme

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.view.View
import android.widget.Button
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter


class MainActivity : AppCompatActivity() {

    private lateinit var mNotifyManager: NotificationManager
    private val NOTIFICATION_ID : Int = 1
    private lateinit var mNotifyButton : Button
    private lateinit var mUpdateButton : Button
    private lateinit var mCancelButton : Button
    private val NOTIFICATION_GUIDE_URL = "https://developer.android.com/design/patterns/notifications.html"
    val ACTION_UPDATE_NOTIFICATION =
            "com.balivo.notifyme.ACTION_UPDATE_NOTIFICATION"

    val mReceiver = NotificationReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        registerReceiver(mReceiver, IntentFilter(ACTION_UPDATE_NOTIFICATION))

        mNotifyManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        mNotifyButton = findViewById(R.id.notify) as Button
        mNotifyButton.setOnClickListener(object: View.OnClickListener {

            override fun onClick(p0: View?) {
                sendNotification()
            }

        })

        mUpdateButton = findViewById(R.id.update) as Button
        mUpdateButton.setOnClickListener(object: View.OnClickListener {

            override fun onClick(p0: View?) {
                updateNotification()
            }

        })

        mCancelButton = findViewById(R.id.cancel) as Button
        mCancelButton.setOnClickListener(object: View.OnClickListener {

            override fun onClick(p0: View?) {
                cancelNotification()
            }

        })

        mNotifyButton.setEnabled(true)
        mUpdateButton.setEnabled(false)
        mCancelButton.setEnabled(false)
    }


    fun sendNotification() {

        val notificationIntent = Intent(this, MainActivity::class.java)

        val notificationPendingIntent = PendingIntent.getActivity(this,
                NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val learnMoreIntent = Intent(Intent.ACTION_VIEW, Uri
                .parse(NOTIFICATION_GUIDE_URL))
        val learnMorePendingIntent = PendingIntent.getActivity(this,
                NOTIFICATION_ID, learnMoreIntent, PendingIntent.FLAG_ONE_SHOT)

        val updateIntent = Intent(ACTION_UPDATE_NOTIFICATION)
        val updatePendingIntent = PendingIntent.getBroadcast(this,
                NOTIFICATION_ID, updateIntent, PendingIntent.FLAG_ONE_SHOT)

        val notifyBuilder = NotificationCompat.Builder(this)
                .setContentTitle("You've been notified!")
                .setContentText("This is your notification text.")
                .setSmallIcon(R.drawable.ic_android)
                .setContentIntent(notificationPendingIntent)
                /*
                Note: The high priority notification will not drop down in front of the active screen
                 unless both the priority and the defaults are set. The priority alone is not enough.
                 */
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .addAction(R.drawable.ic_learn_more,"Learn More", learnMorePendingIntent)
                .addAction(R.drawable.ic_update, "Update", updatePendingIntent)

        val myNotification = notifyBuilder.build()
        mNotifyManager.notify(NOTIFICATION_ID, myNotification)

        mNotifyButton.setEnabled(false)
        mUpdateButton.setEnabled(true)
        mCancelButton.setEnabled(true)
    }


    fun updateNotification() {

        val androidImage = BitmapFactory
                .decodeResource(getResources(),R.drawable.mascot_1)
        val learnMoreIntent = Intent(Intent.ACTION_VIEW, Uri
                .parse(NOTIFICATION_GUIDE_URL))
        val learnMorePendingIntent = PendingIntent.getActivity(this,
                NOTIFICATION_ID, learnMoreIntent, PendingIntent.FLAG_ONE_SHOT)


        val notificationIntent = Intent(this, MainActivity::class.java)
        val notificationPendingIntent = PendingIntent.getActivity(this,
                NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val notifyBuilder = NotificationCompat.Builder(this)
                .setContentTitle("You've been notified!")
                .setContentText("This is your notification text.")
                .setSmallIcon(R.drawable.ic_android)
                .setContentIntent(notificationPendingIntent)
                /*
                Note: The high priority notification will not drop down in front of the active screen
                 unless both the priority and the defaults are set. The priority alone is not enough.
                 */
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setStyle(NotificationCompat.BigPictureStyle()
                        .bigPicture(androidImage)
                        .setBigContentTitle("Notification Updated!"))
                .addAction(R.drawable.ic_learn_more,"Learn More", learnMorePendingIntent)

        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build())

        mNotifyButton.setEnabled(false)
        mUpdateButton.setEnabled(false)
        mCancelButton.setEnabled(true)
    }

    fun cancelNotification() {
        mNotifyManager.cancel(NOTIFICATION_ID)

        mNotifyButton.setEnabled(true)
        mUpdateButton.setEnabled(false)
        mCancelButton.setEnabled(false)
    }

    inner class NotificationReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            updateNotification()
        }
    }

    override fun onDestroy() {
        unregisterReceiver(mReceiver)
        super.onDestroy()
    }
}
