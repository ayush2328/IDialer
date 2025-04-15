package com.goodwy.dialer.receivers

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.util.Log
import com.goodwy.dialer.activities.CallActivity

class LegacyCallReceiver : BroadcastReceiver() {
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context, intent: Intent) {
        val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
        if (state == TelephonyManager.EXTRA_STATE_RINGING) {
            val incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
            Log.d("LegacyCallReceiver", "Incoming call from: $incomingNumber")

            // Start your call UI or spam check here
            context.startActivity(CallActivity.getStartIntent(context).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                putExtra("number", incomingNumber)
            })
        }
    }
}
