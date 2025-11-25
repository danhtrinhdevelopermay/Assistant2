package com.aiassistant

import android.content.Intent
import android.os.Bundle
import android.service.voice.VoiceInteractionService

class AssistantVoiceInteractionService : VoiceInteractionService() {
    
    override fun onReady() {
        super.onReady()
        android.util.Log.d("VoiceInteraction", "Service is ready")
    }

    override fun onLaunchVoiceAssistFromKeyguard() {
        super.onLaunchVoiceAssistFromKeyguard()
        showSession(Bundle(), 0)
    }

    override fun onShutdown() {
        super.onShutdown()
        android.util.Log.d("VoiceInteraction", "Service shutdown")
    }
}
