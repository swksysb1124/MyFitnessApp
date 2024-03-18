package studio.jasonsu.myfitness.util.tts

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale

/**
 * Implement text-to-speech function by integrating [android.speech.tts.TextToSpeech]
 */
class DefaultTextToSpeechEngine(context: Context) : TextToSpeechEngine {
    private var isTextToSpeechInit = false

    private val textToSpeech: TextToSpeech =
        TextToSpeech(context) { status ->
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