package com.project.neuronexus.audio

import com.project.neuronexus.model.VoiceFeatures
import kotlin.math.abs

object VoiceFeatureExtractor {

    fun extract(samples: ShortArray, sampleRate: Int): VoiceFeatures {
        val norm = Short.MAX_VALUE.toDouble()
        var sum = 0.0
        var zeroCross = 0
        var voiced = 0

        for (i in samples.indices) {
            val v = abs(samples[i] / norm)
            sum += v
            if (v > 0.02) voiced++

            if (i > 0) {
                if ((samples[i - 1] >= 0 && samples[i] < 0) ||
                    (samples[i - 1] < 0 && samples[i] >= 0)
                ) zeroCross++
            }
        }

        return VoiceFeatures(
            averageAmplitude = sum / samples.size,
            zeroCrossingRate = zeroCross.toDouble() / samples.size,
            speakingDurationSec = voiced.toDouble() / sampleRate
        )
    }

    fun score(f: VoiceFeatures, duration: Int): Int {
        val loud = if (f.averageAmplitude in 0.02..0.12) 35 else 15
        val clarity = if (f.zeroCrossingRate in 0.05..0.2) 35 else 15
        val ratio = f.speakingDurationSec / duration
        val dur = when {
            ratio > 0.6 -> 30
            ratio > 0.4 -> 20
            else -> 10
        }
        return (loud + clarity + dur).coerceIn(0, 100)
    }
}
