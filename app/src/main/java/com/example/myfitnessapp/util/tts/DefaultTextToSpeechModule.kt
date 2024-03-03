package com.example.myfitnessapp.util.tts

import android.app.Application
import android.speech.tts.TextToSpeech
import java.util.Locale

/**
 * Implement text-to-speech function by integrating [android.speech.tts.TextToSpeech]
 */
class DefaultTextToSpeechModule(application: Application) : TextToSpeechModule {
    private var isTextToSpeechInit = false

    private val textToSpeech: TextToSpeech =
        TextToSpeech(application.applicationContext) { status ->
            if (status == TextToSpeech.SUCCESS) {
                isTextToSpeechInit = true
            }
        }

    override fun setLanguage(locale: Locale) {
        checkTextToSpeakInitialized()
        textToSpeech.setLanguage(locale)
    }

    override fun speak(text: String) {
        checkTextToSpeakInitialized()
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null)
    }

    private fun checkTextToSpeakInitialized() {
        if (!isTextToSpeechInit) {
            throw IllegalStateException("TextToSpeech module is not yet initialized successfully")
        }
    }
}