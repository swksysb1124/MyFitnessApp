package com.example.myfitnessapp.util.tts

import android.app.Application
import com.example.myfitnessapp.util.SingletonHolder
import java.util.Locale

/**
 * Encapsulate underlying Text-To-Speech module
 */
class TextToSpeakUtil private constructor(application: Application) : TextToSpeechModule {
    private val textToSpeech: TextToSpeechModule = DefaultTextToSpeechModule(application)
    override fun setLanguage(locale: Locale) = textToSpeech.setLanguage(locale)
    override fun speak(text: String) = textToSpeech.speak(text)
    companion object : SingletonHolder<TextToSpeakUtil, Application>(::TextToSpeakUtil)
}

