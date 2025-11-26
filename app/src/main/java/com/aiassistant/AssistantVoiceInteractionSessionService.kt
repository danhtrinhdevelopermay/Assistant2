package com.aiassistant

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.service.voice.VoiceInteractionSession
import android.service.voice.VoiceInteractionSessionService
import android.util.Log

class AssistantVoiceInteractionSessionService : VoiceInteractionSessionService() {

    companion object {
        private const val TAG = "VoiceSession"
    }

    override fun onNewSession(args: Bundle?): VoiceInteractionSession {
        Log.d(TAG, "Creating new voice interaction session")
        return AssistantVoiceInteractionSession(this)
    }

    class AssistantVoiceInteractionSession(context: Context) : VoiceInteractionSession(context) {

        override fun onShow(args: Bundle?, showFlags: Int) {
            super.onShow(args, showFlags)
            Log.d(TAG, "Session shown with flags: $showFlags")
            
            try {
                val intent = Intent(context, AssistantOverlayActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or 
                           Intent.FLAG_ACTIVITY_CLEAR_TOP or
                           Intent.FLAG_ACTIVITY_SINGLE_TOP or
                           Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
                    args?.let { putExtras(it) }
                }
                startAssistantActivity(intent)
                Log.d(TAG, "Started assistant activity successfully")
            } catch (e: Exception) {
                Log.e(TAG, "Error starting assistant activity: ${e.message}")
                context.startActivity(Intent(context, AssistantOverlayActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                })
            }
        }

        override fun onHide() {
            super.onHide()
            Log.d(TAG, "Session hidden")
        }
    }
}
