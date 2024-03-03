package com.example.myfitnessapp.util.tts

import java.util.Locale

/**
 * Define text-to-speech behavior
 */
interface TextToSpeechModule {
    fun setLanguage(locale: Locale)
    fun speak(text: String)
}