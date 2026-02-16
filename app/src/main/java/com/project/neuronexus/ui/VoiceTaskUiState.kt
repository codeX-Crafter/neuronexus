package com.project.neuronexus.ui

import com.project.neuronexus.model.VoiceTaskResult

data class VoiceTaskUiState(
    val isRecording: Boolean = false,
    val status: String = "Tap start",
    val result: VoiceTaskResult? = null
)
