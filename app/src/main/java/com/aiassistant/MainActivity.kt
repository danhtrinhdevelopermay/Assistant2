package com.aiassistant

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private var viewModel: AIViewModel? = null
    private var pendingIntent: Intent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        pendingIntent = intent
        
        setContent {
            viewModel = viewModel()
            val vm = viewModel!!
            
            LaunchedEffect(Unit) {
                vm.initializeModel(BuildConfig.GEMINI_API_KEY)
                
                pendingIntent?.let { intent ->
                    handleIntent(intent)
                    pendingIntent = null
                }
            }

            AIAssistantTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF000000)
                ) {
                    AssistantScreen(viewModel = vm)
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        when (intent?.action) {
            Intent.ACTION_ASSIST,
            Intent.ACTION_VOICE_COMMAND,
            "android.intent.action.VOICE_COMMAND" -> {
                viewModel?.activate()
            }
        }
    }
}

@Composable
fun AIAssistantTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = Color(0xFF00C6FF),
            secondary = Color(0xFF0072FF),
            background = Color(0xFF000000),
            surface = Color(0xFF1C1C1E),
            onPrimary = Color.White,
            onSecondary = Color.White,
            onBackground = Color.White,
            onSurface = Color.White,
        ),
        content = content
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssistantScreen(viewModel: AIViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val messages by viewModel.messages.collectAsState()
    val isActive by viewModel.isActive.collectAsState()
    
    var inputText by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            coroutineScope.launch {
                listState.animateScrollToItem(messages.size - 1)
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        SiriAnimatedBackground(isActive = isActive)
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "AI Assistant",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (messages.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                PulsingGlow(
                                    modifier = Modifier.size(120.dp),
                                    isActive = isActive,
                                    color = Color(0xFF00C6FF)
                                )
                                
                                Text(
                                    text = when (uiState) {
                                        is UiState.Listening -> "Đang lắng nghe..."
                                        is UiState.Thinking -> "Đang suy nghĩ..."
                                        else -> "Xin chào! Tôi có thể giúp gì cho bạn?"
                                    },
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Color.White.copy(alpha = 0.7f)
                                )
                            }
                        }
                    }
                }

                items(messages) { message ->
                    MessageBubble(message = message)
                }

                if (uiState is UiState.Thinking) {
                    item {
                        ThinkingIndicator()
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            AnimatedVisibility(
                visible = isActive,
                enter = slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(500)
                ) + fadeIn(),
                exit = slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(500)
                ) + fadeOut()
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    SiriGradientBorder(
                        modifier = Modifier.matchParentSize(),
                        isActive = isActive,
                        borderWidth = 3.dp
                    )
                    
                    SiriSweepEffect(
                        modifier = Modifier.matchParentSize(),
                        isActive = isActive
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Color(0xFF1C1C1E).copy(alpha = 0.95f),
                                RoundedCornerShape(24.dp)
                            )
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextField(
                            value = inputText,
                            onValueChange = { inputText = it },
                            placeholder = {
                                Text(
                                    "Hỏi điều gì đó...",
                                    color = Color.White.copy(alpha = 0.5f)
                                )
                            },
                            modifier = Modifier.weight(1f),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                cursorColor = Color(0xFF00C6FF),
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            singleLine = true
                        )

                        IconButton(
                            onClick = {
                                if (inputText.isNotBlank()) {
                                    viewModel.sendMessage(inputText)
                                    inputText = ""
                                    focusManager.clearFocus()
                                }
                            },
                            enabled = inputText.isNotBlank() && uiState !is UiState.Thinking
                        ) {
                            Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = "Send",
                                tint = if (inputText.isNotBlank()) Color(0xFF00C6FF) else Color.Gray
                            )
                        }
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = {
                if (isActive) {
                    viewModel.deactivate()
                } else {
                    viewModel.activate()
                }
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = Color(0xFF00C6FF)
        ) {
            Text(
                text = if (isActive) "✕" else "AI",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun MessageBubble(message: ChatMessage) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.isUser) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .widthIn(max = 280.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(
                    if (message.isUser) {
                        Color(0xFF00C6FF)
                    } else {
                        Color(0xFF2C2C2E)
                    }
                )
                .padding(12.dp)
        ) {
            Text(
                text = message.text,
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun ThinkingIndicator() {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF2C2C2E))
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(3) { index ->
            val infiniteTransition = rememberInfiniteTransition(label = "dot$index")
            val alpha by infiniteTransition.animateFloat(
                initialValue = 0.3f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(600, delayMillis = index * 200),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "dotAlpha$index"
            )

            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(Color.White.copy(alpha = alpha), androidx.compose.foundation.shape.CircleShape)
            )
        }
    }
}
