package com.example.myfitnessapp.util.tts

import android.app.Application
import java.util.Locale

/**
 * hide the underlying Text-To-Speech engine
 */
class TextToSpeakUtil private constructor(private val textToSpeechEngine: TextToSpeechEngine) : TextToSpeechEngine {
    override fun setLanguage(locale: Locale) = textToSpeechEngine.setLanguage(locale)
    override fun speak(text: String) = textToSpeechEngine.speak(text)

    companion object {
        fun create(
            application: Application,
            customTextToSpeechEngine: TextToSpeechEngine? = null
        ): TextToSpeakUtil {
            val ttsEngine = customTextToSpeechEngine ?: DefaultTextToSpeechEngine(application)
            return TextToSpeakUtil(ttsEngine)
        }
    }
}

