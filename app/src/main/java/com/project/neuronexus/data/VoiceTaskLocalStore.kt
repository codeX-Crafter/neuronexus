package com.project.neuronexus.data

import android.content.Context
import com.project.neuronexus.model.VoiceFeatures
import com.project.neuronexus.model.VoiceTaskResult

class VoiceTaskLocalStore(context: Context) {

    private val prefs = context.getSharedPreferences("voice_store", Context.MODE_PRIVATE)

    fun save(result: VoiceTaskResult) {
        prefs.edit()
            .putLong("time", result.timestampMs)
            .putFloat("amp", result.features.averageAmplitude.toFloat())
            .putFloat("zcr", result.features.zeroCrossingRate.toFloat())
            .putFloat("dur", result.features.speakingDurationSec.toFloat())
            .putInt("score", result.score)
            .apply()
    }

    fun latest(): VoiceTaskResult? {
        if (!prefs.contains("time")) return null
        return VoiceTaskResult(
            prefs.getLong("time", 0),
            VoiceFeatures(
                prefs.getFloat("amp", 0f).toDouble(),
                prefs.getFloat("zcr", 0f).toDouble(),
                prefs.getFloat("dur", 0f).toDouble()
            ),
            prefs.getInt("score", 0)
        )
    }
}
