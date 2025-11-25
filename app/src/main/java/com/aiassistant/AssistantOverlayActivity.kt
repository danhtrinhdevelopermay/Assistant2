package com.aiassistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aiassistant.ui.theme.AIAssistantTheme

class AssistantOverlayActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            val viewModel: AIViewModel = viewModel()
            
            LaunchedEffect(Unit) {
                viewModel.initializeModel(BuildConfig.GEMINI_API_KEY)
                viewModel.activate()
            }
            
            AIAssistantTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.7f)),
                    contentAlignment = Alignment.Center
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .fillMaxHeight(0.7f),
                        shape = RoundedCornerShape(24.dp),
                        color = Color(0xFF000000)
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            AssistantScreen(
                                viewModel = viewModel,
                                isOverlay = true,
                                onClose = { finish() }
                            )
                        }
                    }
                }
            }
        }
    }
    
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
