package com.ph.confession.services


import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.widget.Toast
import com.ph.confession.R
import com.ph.confession.activities.MainActivity

/**
 * Created by TutorialsPoint7 on 8/23/2016.
 */

class NotificationService : Service() {
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {

//                sendNotification(4)

//                Toast.makeText(this@NotificationService, "Service started", Toast.LENGTH_LONG).show()

                handler.postDelayed(this, 2000)
            }
        }, 6000)  //the time is in miliseconds
        return Service.START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show()
    }

//    private fun sendNotification(count: Int) {
//
//        //Get an instance of NotificationManager//
//        val notificationIntent = Intent(this@NotificationService, MainActivity::class.java)
//        val contentIntent = PendingIntent.getActivity(this,
//                0, notificationIntent,
//                PendingIntent.FLAG_CANCEL_CURRENT)
//
//        val mBuilder = NotificationCompat.Builder(this@NotificationService)
//                .setSmallIcon(R.drawable.ic_mail_old)
//                .setContentTitle("Ensense Notification")
//                .setContentText(count.toString() + " of your tickets has been corrected")
//                .setContentIntent(contentIntent)
//                .setAutoCancel(true)
//
//        // Gets an instance of the NotificationManager service//
//
//        val mNotificationManager = this@NotificationService.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//        mNotificationManager.notify(2, mBuilder.build())
//
//    }
}