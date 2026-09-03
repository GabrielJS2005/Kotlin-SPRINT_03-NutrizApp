package GabrielJS2005.com.github.sprint03_nutrizapp.ui.components

import GabrielJS2005.com.github.sprint03_nutrizapp.ui.theme.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class JourneyStatus {
    REVIEW,
    READY,
    SCHEDULED
}

@Composable
fun JourneyCard(
    status: JourneyStatus,
    onDetailsClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val gradientColors = when (status) {
        JourneyStatus.REVIEW    -> listOf(BluePetroleum, GreenWater)
        JourneyStatus.READY     -> listOf(Mint, GreenWater)
        JourneyStatus.SCHEDULED -> listOf(CoralSoft, Color(0xFFD9614E))
    }
    val chipBg = when (status) {
        JourneyStatus.REVIEW    -> Color.White.copy(alpha = 0.20f)
        JourneyStatus.READY     -> Color.White.copy(alpha = 0.25f)
        JourneyStatus.SCHEDULED -> Color.White.copy(alpha = 0.22f)
    }
    val chipLabel = when (status) {
        JourneyStatus.REVIEW    -> "Em análise"
        JourneyStatus.READY     -> "Apta ✓"
        JourneyStatus.SCHEDULED -> "Coleta agendada"
    }
    val title = when (status) {
        JourneyStatus.REVIEW    -> "Seu cadastro está em análise"
        JourneyStatus.READY     -> "Você está apta para doar 💛"
        JourneyStatus.SCHEDULED -> "Você tem uma coleta agendada"
    }
    val subtitle = when (status) {
        JourneyStatus.REVIEW    -> "Nossa equipe está revisando seus dados.\nEm breve te avisamos por aqui e pelo WhatsApp."
        JourneyStatus.READY     -> "Tudo certo! Agende sua próxima coleta quando se sentir confortável."
        JourneyStatus.SCHEDULED -> "Terça-feira, 14h — em casa. Nossa equipe levará tudo o que precisar."
    }

    val infiniteTransition = rememberInfiniteTransition(label = "chipSpin")
    val spinRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue  = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = LinearEasing)
        ),
        label = "spin"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(Brush.linearGradient(gradientColors))
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .align(Alignment.TopEnd)
                .offset(x = 30.dp, y = (-30).dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.10f))
        )

        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(chipBg)
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                if (status == JourneyStatus.REVIEW) {
                    Icon(
                        Icons.Filled.Refresh,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(12.dp).rotate(spinRotation)
                    )
                } else {
                    Icon(
                        Icons.Filled.CheckCircle,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(12.dp)
                    )
                }
                Text(
                    text = chipLabel,
                    color = Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = NunitoFontFamily
                )
            }

            Spacer(Modifier.height(12.dp))

            Text(
                text = title,
                fontFamily = QuicksandFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White,
                lineHeight = 26.sp
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color.White.copy(alpha = 0.90f),
                    fontFamily = NunitoFontFamily
                )
            )

            Spacer(Modifier.height(16.dp))

            TextButton(
                onClick = onDetailsClick,
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(Color.White.copy(alpha = 0.20f)),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "Ver detalhes",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = NunitoFontFamily
                )
                Spacer(Modifier.width(4.dp))
                Icon(Icons.Filled.ChevronRight, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
            }
        }
    }
}