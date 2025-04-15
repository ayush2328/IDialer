package com.goodwy.dialer.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.goodwy.dialer.R
import com.goodwy.dialer.activities.CallActivity

@Suppress("DEPRECATION")
class CallHandlingService : Service() {

    private lateinit var telephonyManager: TelephonyManager

    private val phoneStateListener = object : PhoneStateListener() {
        override fun onCallStateChanged(state: Int, incomingNumber: String?) {
            if (state == TelephonyManager.CALL_STATE_RINGING) {
                Log.d("CallHandlingService", "Incoming call: $incomingNumber")

                // Show custom call screen or spam check logic
                val intent = CallActivity.getStartIntent(applicationContext).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    putExtra("number", incomingNumber)
                }
                startActivity(intent)
            }
        }
    }

    override fun onCreate() {
        super.onCreate()

        telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundServiceCompat()
        }
        return START_STICKY
    }

    private fun startForegroundServiceCompat() {
        val channelId = "call_service_channel"
        val channelName = "Call Monitoring Service"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Monitoring Incoming Calls")
            .setContentText("Looking for spam & fraud calls")
            .setSmallIcon(R.drawable.ic_call_accept)
            .build()

        startForeground(101, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE)
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
