package com.project.neuronexus.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.project.neuronexus.audio.AudioRecorder
import com.project.neuronexus.audio.VoiceFeatureExtractor
import com.project.neuronexus.data.VoiceTaskLocalStore
import com.project.neuronexus.model.VoiceTaskResult
import com.project.neuronexus.tts.TtsController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VoiceTaskViewModel(app: Application) : AndroidViewModel(app) {

    private val recorder = AudioRecorder(app)
    private val extractor = VoiceFeatureExtractor
    private val store = VoiceTaskLocalStore(app)
    private val tts = TtsController(app)

    private val _uiState = MutableStateFlow(VoiceTaskUiState(result = store.latest()))
    val uiState = _uiState.asStateFlow()

    fun startTask() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isRecording = true, status = "Recording...")
            val samples = recorder.record(10)
            val features = extractor.extract(samples, 16000)
            val score = extractor.score(features, 10)

            val result = VoiceTaskResult(System.currentTimeMillis(), features, score)
            store.save(result)

            _uiState.value = VoiceTaskUiState(false, "Done", result)
            tts.speak("Your score is $score")
        }
    }

    override fun onCleared() {
        tts.shutdown()
    }

    class Factory(private val app: Application) : ViewModelProvider.Factory {
        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
            return VoiceTaskViewModel(app) as T
        }
    }
}
