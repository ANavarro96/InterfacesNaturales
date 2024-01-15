package com.example.interfacesnaturales.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.interfacesnaturales.R

@Composable
fun PantallaZoom() {

// Variable que indica si el contenido está en zoom
    var zoom by remember { mutableStateOf(false) }

// Offset que representa el desplazamiento del contenido debido al zoom o arrastre
    var zoomOffset by remember { mutableStateOf(Offset.Zero) }

// Offsets para el desplazamiento del contenido
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

// Columna que ocupa toda la pantalla y detecta gestos de toque y arrastre
    Column(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                // Detecta gestos de doble toque
                detectTapGestures(
                    onDoubleTap = { tapOffset ->
                        // Calcula el offset de zoom en función de si está en zoom o no
                        zoomOffset = if (zoom) Offset.Zero else
                            calculateOffset(tapOffset, size)

                        // Cambia el estado de zoom
                        zoom = !zoom

                        // Reinicia los offsets si no está zoom, para ponerlo en la posición
                        // original
                        if (!zoom) {
                            offsetX = 0.0f
                            offsetY = 0.0f
                        }
                    }
                )
            }
            .pointerInput(Unit) {
                // Detecta gestos de arrastre
                detectDragGestures { change, dragAmount ->
                    if (zoom) {
                        // Consumir el evento de arrastre
                        change.consume()

                        // Calcula el desplazamiento adicional para limitar el zoom
                        val extraWidth = size.width.toFloat() / 2
                        val extraHeight = size.height.toFloat() / 2

                        // Actualiza el offset de zoom
                        zoomOffset += Offset(dragAmount.x, dragAmount.y)

                        // Limita el offset de zoom dentro de los límites
                        zoomOffset = Offset(
                            zoomOffset.x.coerceIn(-extraWidth, extraWidth),
                            zoomOffset.y.coerceIn(-extraHeight, extraHeight)
                        )
                    }
                }
            }
            .graphicsLayer {

                // Escala en el eje X (horizontal)
                scaleX = if (zoom) 2f else 1f

                // Escala en el eje Y (vertical)
                scaleY = if (zoom) 2f else 1f

                // Desplazamiento horizontal
                translationX = zoomOffset.x

                // Desplazamiento vertical
                translationY = zoomOffset.y
            }
    ) {
        // Contenido de la columna (Texto e Imagen)
        Text(stringResource(id = R.string.descripcion_donkey))
        Image(
            painter = painterResource(id = R.drawable.dk),
            contentDescription = null,
        )
    }

}



private fun calculateOffset(tapOffset: Offset, size: IntSize): Offset {
    val offsetX = (-(tapOffset.x - (size.width / 2f)) * 2f)
        .coerceIn(-size.width / 2f, size.width / 2f)
    return Offset(offsetX, 0f)
}