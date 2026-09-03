package GabrielJS2005.com.github.sprint03_nutrizapp.ui.components

import GabrielJS2005.com.github.sprint03_nutrizapp.ui.theme.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class NutrizStatus {
    REVIEW,
    ELIGIBLE,
    SCHEDULED,
    DONE,
    PENDING,
    WARNING
}

private data class StatusConfig(
    val label: String,
    val icon: ImageVector,
    val containerColor: Color,
    val contentColor: Color,
    val spinning: Boolean = false
)

private val statusConfigs = mapOf(
    NutrizStatus.REVIEW    to StatusConfig("Em análise",   Icons.Filled.Refresh,        InfoBlueSoft, InfoBlue,    spinning = true),
    NutrizStatus.ELIGIBLE  to StatusConfig("Apta",         Icons.Filled.CheckCircle,     SuccessGreenSoft, SuccessGreen),
    NutrizStatus.SCHEDULED to StatusConfig("Agendada",     Icons.Filled.CalendarToday,   BluePetroleumSoft, BluePetroleum),
    NutrizStatus.DONE      to StatusConfig("Concluída",    Icons.Filled.CheckCircle,     SuccessGreenSoft, SuccessGreen),
    NutrizStatus.PENDING   to StatusConfig("Pendente",     Icons.Filled.AccessTime,      WarningAmberSoft, WarningAmber),
    NutrizStatus.WARNING   to StatusConfig("Atenção",      Icons.Filled.Warning,         CoralSoftBg, CoralSoft),
)

@Composable
fun NutrizStatusBadge(
    status: NutrizStatus,
    label: String? = null,
    modifier: Modifier = Modifier
) {
    val config = statusConfigs[status] ?: statusConfigs[NutrizStatus.PENDING]!!

    val infiniteTransition = rememberInfiniteTransition(label = "spinnerRotation")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue  = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(config.containerColor)
            .padding(horizontal = 10.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Icon(
            imageVector = config.icon,
            contentDescription = null,
            tint = config.contentColor,
            modifier = Modifier
                .size(14.dp)
                .then(if (config.spinning) Modifier.rotate(rotation) else Modifier)
        )
        Text(
            text = label ?: config.label,
            color = config.contentColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = NunitoFontFamily
        )
    }
}

@Composable
fun StatusBadge(
    status: GabrielJS2005.com.github.sprint03_nutrizapp.model.StatusDoacao,
    modifier: Modifier = Modifier
) {
    val nutrizStatus = when (status) {
        GabrielJS2005.com.github.sprint03_nutrizapp.model.StatusDoacao.ELEGIVEL   -> NutrizStatus.ELIGIBLE
        GabrielJS2005.com.github.sprint03_nutrizapp.model.StatusDoacao.AGENDADA   -> NutrizStatus.SCHEDULED
        GabrielJS2005.com.github.sprint03_nutrizapp.model.StatusDoacao.EM_ANALISE -> NutrizStatus.REVIEW
        GabrielJS2005.com.github.sprint03_nutrizapp.model.StatusDoacao.CONCLUIDA  -> NutrizStatus.DONE
    }
    NutrizStatusBadge(status = nutrizStatus, modifier = modifier)
}