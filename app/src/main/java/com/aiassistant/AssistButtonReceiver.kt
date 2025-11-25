package com.aiassistant

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class AssistButtonReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) return

        Log.d("AssistButtonReceiver", "Received intent: ${intent.action}")

        when (intent.action) {
            Intent.ACTION_VOICE_COMMAND,
            "android.intent.action.VOICE_COMMAND" -> {
                val launchIntent = Intent(context, MainActivity::class.java).apply {
                    action = Intent.ACTION_VOICE_COMMAND
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
                }
                context.startActivity(launchIntent)
            }
        }
    }
}
