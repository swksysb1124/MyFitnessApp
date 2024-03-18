package studio.jasonsu.myfitness.util.tts

import java.util.Locale

/**
 * Define text-to-speech behavior
 */
interface TextToSpeechEngine {
    fun setLanguage(locale: Locale)
    fun speak(text: String)
}