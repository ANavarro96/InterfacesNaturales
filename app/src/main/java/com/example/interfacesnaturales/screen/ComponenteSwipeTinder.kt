package com.example.interfacesnaturales.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.example.interfacesnaturales.R
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListaSwipeable(){
    val fotitos = listOf(
        R.drawable.dk,
        R.drawable.luigi,
        R.drawable.mario,
    )
    // Almacenamos el estado del pager
            val pagerState = rememberPagerState(pageCount = { fotitos.size })
            val scope = rememberCoroutineScope()
            Box(modifier = Modifier.fillMaxSize()) {
                // Este es el componente que permite establecer el pager horizontal, al que le paso:
                // - El estado, declarado arriba
                // - La clave que identifica el elemento que se muestra, de forma única.
                // - El tamaño de la página, yo he pueso fill para que se ajuste al padre (la caja)
                HorizontalPager(
                    state = pagerState,
                    key = { fotitos[it] },
                    pageSize = PageSize.Fill
                ) { index ->
                    Image(
                        painter = painterResource(id = fotitos[index]),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize().graphicsLayer {

                            // Calculo el offset entre la página actual y la siguiente
                            val pageOffset = (
                                    (pagerState.currentPage - index) + pagerState
                                        .currentPageOffsetFraction
                                    ).absoluteValue

                            // Animo la trasnparencia. lerp lo que hace es interpolar dos valores,
                            // que irán cambiando poco a poco hasta llegar al máximo, que es 1.
                            // Leed más aquí: https://gamedevbeginner.com/the-right-way-to-lerp-in-unity-with-examples/#how_to_use_lerp_in_unity
                            // Es un tutorial de Unity, pero trata sobre el mismo concepto
                            alpha = lerp(
                                start = 0.5f,
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            )
                        }

                    )
                }
                // En esta caja
                Box(
                    modifier = Modifier
                        .offset(y = -(16).dp)
                        .fillMaxWidth(0.5f)
                        .clip(RoundedCornerShape(100))
                        .background(MaterialTheme.colorScheme.background)
                        .padding(8.dp)
                        .align(Alignment.BottomCenter)
                ) {
                    IconButton(
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(
                                    pagerState.currentPage - 1
                                )
                            }
                        },
                        modifier = Modifier.align(Alignment.CenterStart)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Go back"
                        )
                    }
                    IconButton(
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(
                                    pagerState.currentPage + 1
                                )
                            }
                        },
                        modifier = Modifier.align(Alignment.CenterEnd)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = "Go forward"
                        )
                    }
                }
            }
        }
