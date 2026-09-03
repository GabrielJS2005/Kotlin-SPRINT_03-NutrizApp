package GabrielJS2005.com.github.sprint03_nutrizapp.ui.components

import GabrielJS2005.com.github.sprint03_nutrizapp.ui.theme.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class StateVariant {
    SUCCESS, ERROR, PENDING, ANALYSIS, EMPTY
}

private data class StateConfig(
    val icon: ImageVector,
    val bubbleColor: Color,
    val iconTint: Color,
    val eyebrow: String
)

private val stateConfigs = mapOf(
    StateVariant.SUCCESS  to StateConfig(Icons.Filled.CheckCircle,  SuccessGreenSoft,  SuccessGreen, "Tudo certo"),
    StateVariant.ERROR    to StateConfig(Icons.Filled.Cancel,        CoralSoftBg,       CoralSoft,    "Algo deu errado"),
    StateVariant.PENDING  to StateConfig(Icons.Filled.AccessTime,    WarningAmberSoft,  WarningAmber, "Quase lá"),
    StateVariant.ANALYSIS to StateConfig(Icons.Filled.Refresh,       InfoBlueSoft,      InfoBlue,     "Em análise"),
    StateVariant.EMPTY    to StateConfig(Icons.Filled.Inbox,         BluePetroleumSoft, BluePetroleum,"Sem novidades por aqui"),
)

@Composable
fun StateScreen(
    variant: StateVariant,
    title: String,
    description: String? = null,
    eyebrow: String? = null,
    primaryAction: @Composable (() -> Unit)? = null,
    secondaryAction: @Composable (() -> Unit)? = null,
    footnote: String? = null,
    modifier: Modifier = Modifier
) {
    val config = stateConfigs[variant] ?: stateConfigs[StateVariant.EMPTY]!!

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(SurfaceWhite)
            .padding(28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(config.bubbleColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = config.icon,
                contentDescription = null,
                tint = config.iconTint,
                modifier = Modifier.size(40.dp)
            )
        }

        Text(
            text = (eyebrow ?: config.eyebrow).uppercase(),
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.ExtraBold,
                color = TextGrayLight
            )
        )

        Text(
            text = title,
            fontFamily = QuicksandFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            color = TextDark,
            textAlign = TextAlign.Center,
            lineHeight = 28.sp
        )

        if (description != null) {
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = TextGrayLight,
                    textAlign = TextAlign.Center
                )
            )
        }

        Spacer(Modifier.height(4.dp))

        if (primaryAction != null || secondaryAction != null) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                primaryAction?.invoke()
                secondaryAction?.invoke()
            }
        }

        if (footnote != null) {
            Text(
                text = footnote,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = TextGrayLight,
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}