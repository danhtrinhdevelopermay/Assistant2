package com.aiassistant

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.service.voice.VoiceInteractionSession
import android.service.voice.VoiceInteractionSessionService
import android.util.Log

class AssistantVoiceInteractionSessionService : VoiceInteractionSessionService() {

    override fun onNewSession(args: Bundle?): VoiceInteractionSession {
        Log.d("VoiceSession", "Creating new session")
        return AssistantVoiceInteractionSession(this)
    }

    class AssistantVoiceInteractionSession(context: Context) : VoiceInteractionSession(context) {

        override fun onShow(args: Bundle?, showFlags: Int) {
            super.onShow(args, showFlags)
            Log.d("VoiceSession", "Session shown")
            
            val intent = Intent(context, AssistantOverlayActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or 
                       Intent.FLAG_ACTIVITY_CLEAR_TOP or
                       Intent.FLAG_ACTIVITY_SINGLE_TOP
                args?.let { putExtras(it) }
            }
            context.startActivity(intent)
        }

        override fun onHide() {
            super.onHide()
            Log.d("VoiceSession", "Session hidden")
            finish()
        }
    }
}
