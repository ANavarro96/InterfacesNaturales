package com.example.interfacesnaturales.nav

import android.app.Application
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.interfacesnaturales.screen.BasicExample
import com.example.interfacesnaturales.screen.BorradoSwipe
import com.example.interfacesnaturales.screen.PagerSwipeable
import com.example.interfacesnaturales.screen.PantallaZoom
import com.example.interfacesnaturales.screent.SpeechToText

@Composable
fun GrafoNavegacion(app: Application){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Rutas.Menu.ruta){
        composable(Rutas.Menu.ruta){
            MenuPrincipal()
        }
        composable(Rutas.TTS.ruta){
            SpeechToText(app)
        }
        composable(Rutas.Zoom.ruta){
            PantallaZoom()
        }
        composable(Rutas.Gestos.ruta){
            BasicExample()
        }
        composable(Rutas.SwipeLista.ruta){
            BorradoSwipe()
        }
        composable(Rutas.Pager.ruta){
            PagerSwipeable()
        }

    }
}

@Composable
fun MenuPrincipal(){
    Column() {
       // TODO
    }
}