package com.example.myfitnessapp.util

import android.app.Application
import android.speech.tts.TextToSpeech
import android.util.Log
import java.util.Locale

private const val TAG = "TextToSpeakUtil"

class TextToSpeakUtil private constructor(application: Application) :
    com.example.myfitnessapp.util.TextToSpeech {

    private var isTextToSpeechInit = false

    private val textToSpeech: TextToSpeech =
        TextToSpeech(application.applicationContext) { status ->
            if (status == TextToSpeech.SUCCESS) {
                Log.d(TAG, "TextToSpeech init success")
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

    companion object : SingletonHolder<TextToSpeakUtil, Application>(::TextToSpeakUtil)
}

interface TextToSpeech {
    fun setLanguage(locale: Locale)
    fun speak(text: String)
}