package com.example.interfacesnaturales.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
fun BasicExample(){
    var texto by remember { mutableStateOf("") }
    Column {
        Text("Interactua con la caja!")
        Text(texto)
        Box(
            Modifier
                .size(100.dp)
                .background(Color.Red)
                .pointerInput(Unit) {
                    detectTapGestures (onTap = { texto = "Me has pulsado"},
                    onDoubleTap = {texto = "Doble pulsación!" },
                    onLongPress = {texto = "Me has mantenido de una forma larga..."},
                    onPress = {texto = "Me has mantenido..."})
                }
                .pointerInput(Unit) {
                    detectDragGestures (onDrag = { cambio, offset ->
                        texto = "Se esta arrastrando!"
                    }, onDragCancel = {texto = "Drag cancelado"},
                        onDragEnd = {texto = "Se termina el drag"},
                        onDragStart = {offset -> texto = "Se empieza el drag" }

                    )
                }
        )
    }
}