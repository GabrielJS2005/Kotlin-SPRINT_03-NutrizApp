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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ImpactCard(
    babiesHelped: Int,
    mlDonated: Int,
    donationsCount: Int,
    modifier: Modifier = Modifier
) {
    data class MetricData(val icon: ImageVector, val label: String, val value: String, val tint: Color)
    val metrics = listOf(
        MetricData(Icons.Filled.Favorite, "Bebês", babiesHelped.toString(), CoralSoft),
        MetricData(Icons.Filled.WaterDrop, "ml doados", mlDonated.toString(), InfoBlue),
        MetricData(Icons.Filled.People, "Doações", donationsCount.toString(), SuccessGreen),
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(SurfaceWhite)
    ) {

        Box(
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.TopEnd)
                .offset(x = 20.dp, y = (-20).dp)
                .clip(CircleShape)
                .background(CoralSoftBg)
        )

        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "SEU IMPACTO",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = BluePetroleum,
                    fontWeight = FontWeight.ExtraBold
                )
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Você já ajudou $babiesHelped ${if (babiesHelped == 1) "bebê" else "bebês"}",
                fontFamily = QuicksandFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = TextDark
            )

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                metrics.forEach { m ->
                    MetricItem(
                        icon = m.icon,
                        label = m.label,
                        value = m.value,
                        tintColor = m.tint,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(Modifier.height(12.dp))
            Text(
                text = "\"Cada gota conta.\"",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = TextGrayLight,
                    fontWeight = FontWeight.Medium
                ),
                modifier = Modifier.fillMaxWidth(),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

@Composable
private fun MetricItem(
    icon: ImageVector,
    label: String,
    value: String,
    tintColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(OffWhite)
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(icon, contentDescription = null, tint = tintColor, modifier = Modifier.size(20.dp))
        Spacer(Modifier.height(6.dp))
        Text(
            text = value,
            fontFamily = QuicksandFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            color = TextDark
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(color = TextGrayLight),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}