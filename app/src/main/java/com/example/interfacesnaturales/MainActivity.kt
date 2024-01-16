package com.example.interfacesnaturales

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.interfacesnaturales.screen.BasicExample
import com.example.interfacesnaturales.screen.BorradoSwipe
import com.example.interfacesnaturales.screen.PantallaZoom
import com.example.interfacesnaturales.screent.SpeechToText
import com.example.interfacesnaturales.ui.theme.InterfacesNaturalesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InterfacesNaturalesTheme {
                // TODO: Add NavigationGraph
                SpeechToText(application)
            }
        }
    }
}

