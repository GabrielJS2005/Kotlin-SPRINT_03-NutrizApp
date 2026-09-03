package GabrielJS2005.com.github.sprint03_nutrizapp.ui.components

import GabrielJS2005.com.github.sprint03_nutrizapp.ui.theme.BluePetroleum
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.theme.CoralSoft
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.theme.GreenWater
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.theme.NunitoFontFamily
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.theme.QuicksandFontFamily
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.theme.SurfaceWhite
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.theme.TextDark
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.theme.TextGrayLight
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * AppHeader — Cabeçalho principal das telas com BottomNav.
 * Exibe avatar com inicial do nome em gradiente, saudação e botão de notificações.
 */
@Composable
fun AppHeader(
    name: String,
    greeting: String = "Olá",
    notificationCount: Int = 0,
    onNotificationsClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val initial = name.firstOrNull()?.uppercaseChar()?.toString() ?: "N"

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Avatar + Saudação
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Avatar circular com gradiente
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(
                            colors = listOf(BluePetroleum, GreenWater)
                        )
                    )
                    .shadow(4.dp, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = initial,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = QuicksandFontFamily
                )
            }

            // Saudação
            Column {
                Text(
                    text = "$greeting, mamãe",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextGrayLight
                )
                Text(
                    text = name,
                    fontFamily = QuicksandFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = TextDark
                )
            }
        }

        // Botão de Notificações
        Box {
            IconButton(
                onClick = onNotificationsClick,
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(SurfaceWhite)
                    .shadow(2.dp, CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Filled.Notifications,
                    contentDescription = "Notificações${if (notificationCount > 0) " ($notificationCount novas)" else ""}",
                    tint = TextDark,
                    modifier = Modifier.size(22.dp)
                )
            }
            // Badge de notificações
            if (notificationCount > 0) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 2.dp, y = (-2).dp)
                        .size(18.dp)
                        .clip(CircleShape)
                        .background(CoralSoft),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (notificationCount > 9) "9+" else notificationCount.toString(),
                        color = Color.White,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = NunitoFontFamily
                    )
                }
            }
        }
    }
}
