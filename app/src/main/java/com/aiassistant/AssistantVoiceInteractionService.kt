package com.aiassistant

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
        showSession(Bundle(), 0)
    }

    override fun onShutdown() {
        super.onShutdown()
        Log.d(TAG, "Service shutdown")
    }
}
