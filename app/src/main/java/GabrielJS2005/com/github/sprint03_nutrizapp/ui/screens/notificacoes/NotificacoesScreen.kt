package GabrielJS2005.com.github.sprint03_nutrizapp.ui.screens.notificacoes

import GabrielJS2005.com.github.sprint03_nutrizapp.ui.components.PageHeader
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.theme.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.navigation.NavController

private data class Notificacao(
    val titulo: String,
    val descricao: String,
    val hora: String,
    val icon: ImageVector,
    val iconBg: Color,
    val iconTint: Color,
    val lida: Boolean = false
)

private val notificacoes = mapOf(
    "Hoje" to listOf(
        Notificacao(
            "Cadastro em análise",
            "Nossa equipe está revisando seus dados. Entraremos em contato em até 2 dias úteis.",
            "10:30",
            Icons.Filled.Refresh,
            InfoBlueSoft, InfoBlue,
            lida = false
        ),
        Notificacao(
            "Novo conteúdo disponível",
            "\"Como armazenar o leite corretamente\" — aprenda em 2 minutos.",
            "08:15",
            Icons.Filled.MenuBook,
            GreenWaterSoft, GreenWater,
            lida = false
        ),
    ),
    "Ontem" to listOf(
        Notificacao(
            "Coleta confirmada!",
            "Sua coleta de amanhã às 14h foi confirmada. Prepare os frascos.",
            "18:45",
            Icons.Filled.CalendarToday,
            BluePetroleumSoft, BluePetroleum,
            lida = true
        ),
    ),
    "Esta semana" to listOf(
        Notificacao(
            "Doação concluída 🎉",
            "Sua doação de 180ml foi processada com sucesso. Obrigada!",
            "Qui, 14:00",
            Icons.Filled.CheckCircle,
            SuccessGreenSoft, SuccessGreen,
            lida = true
        ),
        Notificacao(
            "Exame pendente",
            "Ainda precisamos do resultado do seu exame para liberar o cadastro.",
            "Ter, 09:30",
            Icons.Filled.Warning,
            CoralSoftBg, CoralSoft,
            lida = true
        ),
    )
)

@Composable
fun NotificacoesScreen(navController: NavController) {
    Scaffold(
        containerColor = OffWhite
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            item {
                PageHeader(
                    title = "Notificações",
                    onBack = { navController.popBackStack() }
                )
            }

            notificacoes.forEach { (grupo, items) ->
                item {
                    Text(
                        grupo.uppercase(),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = TextGrayLight
                        ),
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
                    )
                }
                items.forEachIndexed { index, notif ->
                    item {
                        NotifCard(
                            notificacao = notif,
                            isLast = index == items.size - 1,
                            modifier = Modifier.padding(horizontal = 20.dp)
                        )
                    }
                }
                item { Spacer(Modifier.height(8.dp)) }
            }

            item { Spacer(Modifier.height(24.dp)) }
        }
    }
}

@Composable
private fun NotifCard(
    notificacao: Notificacao,
    isLast: Boolean,
    modifier: Modifier = Modifier
) {
    val shape = when {
        isLast -> RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
        else   -> RoundedCornerShape(0.dp)
    }
    val topShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(SurfaceWhite)
            .clickable { }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        // Indicador de não lida
        Box(
            modifier = Modifier
                .padding(top = 18.dp)
                .size(8.dp)
                .clip(CircleShape)
                .background(if (!notificacao.lida) CoralSoft else Color.Transparent)
        )

        // Ícone
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(notificacao.iconBg),
            contentAlignment = Alignment.Center
        ) {
            Icon(notificacao.icon, null, tint = notificacao.iconTint, modifier = Modifier.size(20.dp))
        }

        // Conteúdo
        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    notificacao.titulo,
                    fontFamily = NunitoFontFamily,
                    fontWeight = if (!notificacao.lida) FontWeight.Bold else FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = TextDark,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    notificacao.hora,
                    style = MaterialTheme.typography.labelSmall.copy(color = TextGrayLight)
                )
            }
            Spacer(Modifier.height(2.dp))
            Text(
                notificacao.descricao,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = TextGrayLight,
                    lineHeight = 18.sp
                )
            )
        }
    }
    if (!isLast) {
        Divider(modifier = Modifier.padding(horizontal = 20.dp), color = BorderColor, thickness = 0.5.dp)
    }
}
