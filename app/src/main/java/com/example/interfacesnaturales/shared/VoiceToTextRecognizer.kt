package com.example.interfacesnaturales.shared

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class VoiceRecorderState(
    val estaHablando: Boolean = false,
    val textoHablado: String = "",
    val error: String? = null
)



class VoiceToTextRecognizer(
    private val app: Application
) : RecognitionListener {


    // Manejo el estado aquí dentro
    private val _state = MutableStateFlow(VoiceRecorderState())

    val state =  _state.asStateFlow()

    // Esta es la clase que se ecarga de pasar de audio a texto
    private val recognizer = SpeechRecognizer.createSpeechRecognizer(app)

    // Esta función se lanza cuando se quiera empezar a escuchar
    fun startListening(languageCode: String = "en") {
        // Limpio el estado
        _state.update { VoiceRecorderState() }

        // Si no está disponible almaceno el error
        if (!SpeechRecognizer.isRecognitionAvailable(app)) {
            _state.update {
                it.copy(
                    error = "Speech recognition is not available"
                )
            }
        }

        // Crea un Intent para reconocimiento de audio en un lenguaje en concreto
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, languageCode)
        }

        //Establecemos el listener, todas las funciones que hemos hecho
        recognizer.setRecognitionListener(this)

        // Empieza a escuchar
        recognizer.startListening(intent)

        // Actualizamos el estado para indicar que estamos hablando
        _state.update {
            it.copy(
                estaHablando = true
            )
        }
    }

    fun stopListening() {
        // Indica que ya no se está hablando, lo almacenamos en el estado
        _state.update {
            it.copy(
                estaHablando = false
            )
        }

        // Dejamos que estar pendientes del audio

        recognizer.stopListening()
    }

    override fun onReadyForSpeech(params: Bundle?) {
        // Si está preparado para recibir audio, quitamos errores si los hubiera
        _state.update {
            it.copy(
                error = null
            )
        }
    }



    override fun onEndOfSpeech() {
        // Señalamos que ya hemos dejado de hablar
        _state.update {
            it.copy(
                estaHablando = false
            )
        }
    }

    override fun onError(error: Int) {
        if (error == SpeechRecognizer.ERROR_CLIENT) {
            return
        }
        // Guardamos el error!
        _state.update {
            it.copy(
                error = "Error: $error"
            )
        }
    }

    override fun onResults(results: Bundle?) {
        // Aquí lo que hamos es pasar estos resultados a un string.
        results
            ?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            ?.getOrNull(0)
            ?.let { text ->
                _state.update {
                    it.copy(
                        textoHablado = text
                    )
                }
            }
    }

    // Llamad a estas funciones si las necesitariais para algo
    override fun onBeginningOfSpeech() {
        TODO("Not yet implemented")
    }

    override fun onRmsChanged(p0: Float) {
        TODO("Not yet implemented")
    }

    override fun onBufferReceived(p0: ByteArray?) {
        TODO("Not yet implemented")
    }

    override fun onPartialResults(p0: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun onEvent(p0: Int, p1: Bundle?) {
        TODO("Not yet implemented")
    }
}
