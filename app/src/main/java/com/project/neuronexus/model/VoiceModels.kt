package com.project.neuronexus.model

data class VoiceFeatures(
    val averageAmplitude: Double,
    val zeroCrossingRate: Double,
    val speakingDurationSec: Double
)

data class VoiceTaskResult(
    val timestampMs: Long,
    val features: VoiceFeatures,
    val score: Int
)
