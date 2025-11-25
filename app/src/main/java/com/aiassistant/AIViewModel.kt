package com.aiassistant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.generationConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class UiState {
    object Idle : UiState()
    object Listening : UiState()
    object Thinking : UiState()
    data class Success(val response: String) : UiState()
    data class Error(val message: String) : UiState()
}

data class ChatMessage(
    val text: String,
    val isUser: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)

class AIViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    private val _isActive = MutableStateFlow(false)
    val isActive: StateFlow<Boolean> = _isActive.asStateFlow()

    private var generativeModel: GenerativeModel? = null

    fun initializeModel(apiKey: String) {
        if (apiKey.isBlank()) {
            _uiState.value = UiState.Error("Vui lòng cấu hình Gemini API key")
            return
        }

        generativeModel = GenerativeModel(
            modelName = "gemini-1.5-flash",
            apiKey = apiKey,
            generationConfig = generationConfig {
                temperature = 0.7f
                topK = 40
                topP = 0.95f
                maxOutputTokens = 1024
            }
        )
    }

    fun activate() {
        _isActive.value = true
        _uiState.value = UiState.Listening
    }

    fun deactivate() {
        _isActive.value = false
        _uiState.value = UiState.Idle
    }

    fun sendMessage(prompt: String) {
        if (prompt.isBlank()) return
        
        viewModelScope.launch {
            try {
                _messages.value = _messages.value + ChatMessage(prompt, true)
                _uiState.value = UiState.Thinking

                val model = generativeModel ?: run {
                    val error = "Model chưa được khởi tạo. Vui lòng kiểm tra API key."
                    _uiState.value = UiState.Error(error)
                    _messages.value = _messages.value + ChatMessage(error, false)
                    return@launch
                }

                val response = model.generateContent(prompt)
                val responseText = response.text ?: "Xin lỗi, tôi không thể tạo câu trả lời."

                _messages.value = _messages.value + ChatMessage(responseText, false)
                _uiState.value = UiState.Success(responseText)

            } catch (e: Exception) {
                val errorMessage = when {
                    e.message?.contains("network", ignoreCase = true) == true ||
                    e.message?.contains("unable to resolve host", ignoreCase = true) == true -> {
                        "Không có kết nối Internet. Vui lòng kiểm tra mạng và thử lại."
                    }
                    e.message?.contains("quota", ignoreCase = true) == true ||
                    e.message?.contains("limit", ignoreCase = true) == true -> {
                        "API đã đạt giới hạn sử dụng. Vui lòng thử lại sau hoặc kiểm tra quota."
                    }
                    e.message?.contains("api key", ignoreCase = true) == true ||
                    e.message?.contains("unauthorized", ignoreCase = true) == true ||
                    e.message?.contains("403", ignoreCase = true) == true -> {
                        "API key không hợp lệ. Vui lòng kiểm tra cấu hình."
                    }
                    e.message?.contains("timeout", ignoreCase = true) == true -> {
                        "Yêu cầu hết thời gian chờ. Vui lòng thử lại."
                    }
                    else -> {
                        "Lỗi: ${e.message ?: "Không thể kết nối với AI. Vui lòng thử lại."}"
                    }
                }
                _uiState.value = UiState.Error(errorMessage)
                _messages.value = _messages.value + ChatMessage(errorMessage, false)
            }
        }
    }

    fun clearMessages() {
        _messages.value = emptyList()
        _uiState.value = UiState.Idle
    }
}
