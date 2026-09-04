package GabrielJS2005.com.github.sprint03_nutrizapp.ui.screens.onboarding

import GabrielJS2005.com.github.sprint03_nutrizapp.ui.components.NutrizButton
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.theme.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

private data class OnboardingSlide(
    val emoji: String,
    val pillColor: Color,
    val title: String,
    val description: String
)

private val slides = listOf(
    OnboardingSlide(
        emoji = "🍼",
        pillColor = BluePetroleumSoft,
        title = "Bem-vinda ao Nutriz+",
        description = "A plataforma que conecta mães doadoras ao Banco de Leite Lactare de forma fácil e sem burocracia."
    ),
    OnboardingSlide(
        emoji = "🏠",
        pillColor = CoralSoftBg,
        title = "Doe de Casa",
        description = "Agende a coleta domiciliar. Nós buscamos seu leite com toda a segurança e comodidade para você e seu bebê."
    ),
    OnboardingSlide(
        emoji = "💛",
        pillColor = SuccessGreenSoft,
        title = "Acompanhe seu Impacto",
        description = "Veja quantas vidas você já ajudou a salvar e conquiste reconhecimentos pela sua generosidade."
    )
)

@Composable
fun OnboardingScreen(navController: NavController) {
    var currentStep by remember { mutableStateOf(0) }
    val slide = slides[currentStep]

    val alpha by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(400),
        label = "slideAlpha"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(OffWhite)
    ) {
        if (currentStep < slides.size - 1) {
            androidx.compose.material3.TextButton(
                onClick = {
                    navController.navigate("home") {
                        popUpTo("onboarding") { inclusive = true }
                    }
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 16.dp, end = 16.dp)
            ) {
                Text(
                    "Pular",
                    fontFamily = NunitoFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = TextGrayLight
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .size(180.dp)
                    .clip(CircleShape)
                    .background(slide.pillColor),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = slide.emoji,
                    fontSize = 80.sp,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(Modifier.height(48.dp))

            Text(
                text = slide.title,
                fontFamily = QuicksandFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp,
                color = TextDark,
                textAlign = TextAlign.Center,
                lineHeight = 32.sp
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = slide.description,
                fontFamily = NunitoFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = TextGrayLight,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp
            )

            Spacer(Modifier.weight(1f))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(bottom = 24.dp)
            ) {
                repeat(slides.size) { index ->
                    Box(
                        modifier = Modifier
                            .height(8.dp)
                            .width(if (index == currentStep) 24.dp else 8.dp)
                            .clip(CircleShape)
                            .background(
                                if (index == currentStep) BluePetroleum
                                else BorderColor
                            )
                    )
                }
            }

            NutrizButton(
                text = if (currentStep < slides.size - 1) "Próximo" else "Começar",
                onClick = {
                    if (currentStep < slides.size - 1) {
                        currentStep++
                    } else {
                        navController.navigate("home") {
                            popUpTo("onboarding") { inclusive = true }
                        }
                    }
                }
            )

            Spacer(Modifier.height(32.dp))
        }
    }
}