package com.aiassistant

import android.content.Intent
import android.os.Bundle
import android.service.voice.VoiceInteractionService
import android.util.Log

class AssistantVoiceInteractionService : VoiceInteractionService() {
    
    companion object {
        private const val TAG = "VoiceInteraction"
    }
    
    override fun onReady() {
        super.onReady()
        Log.d(TAG, "Service is ready - Assistant can now be invoked")
    }

    override fun onLaunchVoiceAssistFromKeyguard() {
        super.onLaunchVoiceAssistFromKeyguard()
        Log.d(TAG, "Launching from keyguard")
        showSession(Bundle(), SHOW_SOURCE_ASSIST_GESTURE)
    }

    override fun onShutdown() {
        super.onShutdown()
        Log.d(TAG, "Service shutdown")
    }
    
    override fun onBind(intent: Intent): android.os.IBinder? {
        Log.d(TAG, "Service bound with intent: ${intent.action}")
        return super.onBind(intent)
    }
}
