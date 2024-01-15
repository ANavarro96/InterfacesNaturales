package com.example.interfacesnaturales.screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.launch

var lista =
    mutableStateListOf<String>().apply { addAll(arrayListOf("Lebron James","Anthony Davis",
        "Austin Reaves")) }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BorradoSwipe(){
        LazyColumn() {
            items(lista) {
                ElementoSwipeable(it)
            }
        }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ElementoSwipeable(valor: String) {
        val dismissState = rememberSwipeToDismissBoxState()
        if (dismissState.currentValue == SwipeToDismissBoxValue.EndToStart) {
            rememberCoroutineScope().launch {
                dismissState.snapTo(SwipeToDismissBoxValue.Settled)
                lista.remove(valor)
            }
        }
        SwipeToDismissBox(
            state = dismissState,
            backgroundContent = {
                val color by animateColorAsState(
                    if(dismissState.dismissDirection ==  SwipeToDismissBoxValue.StartToEnd){
                        Color.Green
                    }
                    else if(dismissState.dismissDirection ==  SwipeToDismissBoxValue.EndToStart){
                        Color.Red
                    }
                else{
                        Color.White
                    }
                )
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(color))
            },
        ) {
            Card {
                ListItem(
                    headlineContent = {
                        Text(valor)
                    },
                    supportingContent = { Text("Arrastreme hacia la derecha o la izquierda!") }
                )
                HorizontalDivider()
            }
        }
    }