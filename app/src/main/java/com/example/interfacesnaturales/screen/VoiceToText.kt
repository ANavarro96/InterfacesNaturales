package com.example.interfacesnaturales.screent

import android.Manifest
import android.app.Application
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Mic
import androidx.compose.material.icons.rounded.Stop
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.*
import com.example.interfacesnaturales.shared.VoiceToTextRecognizer


@Composable
fun SpeechToText(app: Application) {
    var tienePermiso by remember {
        mutableStateOf(false)
    }

    // Creamos el launcher con el contract asociado para pedir permiso
    val recordAudioLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { permisoOK ->
            // Este booleano como vimos en el power de los permisos se llama una vez el
            // user haya interactuado con el dialogo.
            tienePermiso = permisoOK
        }
    )

    LaunchedEffect(key1 = recordAudioLauncher) {
        // Lo lanzamos
        recordAudioLauncher.launch(Manifest.permission.RECORD_AUDIO)
    }
    // TODO: INTENTAR CAMBIAR ESTO
    val voiceToText by lazy {
        VoiceToTextRecognizer(app)
    }

    // Establecemos nuestro estado
    val state by voiceToText.state.collectAsState()

    Scaffold(
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (tienePermiso) {
                        if (!state.estaHablando) {
                            voiceToText.startListening("es")
                        } else {
                            voiceToText.stopListening()
                        }
                    }
                }
            ) {
                // Un ejemplo de animación sencillete, que se lanzará siempre que la variable
                // dentro de targetState se recomponga.
                AnimatedContent(targetState = state.estaHablando) { estaHablando ->
                    if (estaHablando) {
                        Icon(
                            imageVector = Icons.Rounded.Stop,
                            contentDescription = null
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Rounded.Mic,
                            contentDescription = null
                        )
                    }
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedContent(targetState = state.estaHablando) { estaHablando ->
                if (estaHablando) {
                    Text(
                        text = "Dale habla jefe...",
                        style = MaterialTheme.typography.titleMedium)
                } else {
                    Text(
                        text = state.textoHablado.ifEmpty { "Pulsa el botón para grabar" },
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}

