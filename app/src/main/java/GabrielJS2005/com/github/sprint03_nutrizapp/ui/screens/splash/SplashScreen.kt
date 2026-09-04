package GabrielJS2005.com.github.sprint03_nutrizapp.ui.screens.splash

import GabrielJS2005.com.github.sprint03_nutrizapp.ui.theme.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    var visible by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(800, easing = EaseOut),
        label = "splashAlpha"
    )
    val scale by animateFloatAsState(
        targetValue = if (visible) 1f else 0.85f,
        animationSpec = tween(800, easing = EaseOut),
        label = "splashScale"
    )

    LaunchedEffect(key1 = true) {
        visible = true
        delay(2200)
        navController.navigate("onboarding") {
            popUpTo("splash") { inclusive = true }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        BluePetroleum.copy(alpha = 0.12f),
                        OffWhite,
                        BluePetroleumSoft
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .alpha(alpha)
                .scale(scale),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Nutriz+",
                fontFamily = QuicksandFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 52.sp,
                color = BluePetroleum,
                letterSpacing = (-1).sp
            )
            Text(
                text = "Conectando vidas 💛",
                fontFamily = NunitoFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = TextGrayLight,
                textAlign = TextAlign.Center
            )
        }

        Text(
            text = "Banco de Leite Lactare",
            fontFamily = NunitoFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = TextGrayLight.copy(alpha = 0.6f),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
                .alpha(alpha)
        )
    }
}
