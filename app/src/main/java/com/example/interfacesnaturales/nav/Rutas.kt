package com.example.interfacesnaturales.nav

sealed class Rutas(val ruta : String) {
    object Gestos : Rutas("Gestos")
    object Zoom : Rutas("Zoom")
    object SwipeLista : Rutas("SwipeLista")
    object Pager : Rutas("Pager")
    object TTS : Rutas("TTS")
    object Menu : Rutas("Menu")

}