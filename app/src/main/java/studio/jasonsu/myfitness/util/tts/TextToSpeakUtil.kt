package studio.jasonsu.myfitness.util.tts

import android.content.Context
import java.util.Locale

/**
 * hide the underlying Text-To-Speech engine
 */
class TextToSpeakUtil private constructor(private val textToSpeechEngine: TextToSpeechEngine) : TextToSpeechEngine {
    override fun setLanguage(locale: Locale) = textToSpeechEngine.setLanguage(locale)
    override fun speak(text: String) = textToSpeechEngine.speak(text)

    companion object {
        fun create(
            context: Context,
            customTextToSpeechEngine: TextToSpeechEngine? = null
        ): TextToSpeakUtil {
            val ttsEngine = customTextToSpeechEngine ?: DefaultTextToSpeechEngine(context)
            return TextToSpeakUtil(ttsEngine)
        }
    }
}

