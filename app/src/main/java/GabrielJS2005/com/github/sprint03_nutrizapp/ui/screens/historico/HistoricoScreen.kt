package GabrielJS2005.com.github.sprint03_nutrizapp.ui.screens.historico

import GabrielJS2005.com.github.sprint03_nutrizapp.data.MockData
import GabrielJS2005.com.github.sprint03_nutrizapp.model.Doacao
import GabrielJS2005.com.github.sprint03_nutrizapp.model.StatusDoacao
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.components.BottomNavBar
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.components.NutrizStatus
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.components.NutrizStatusBadge
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.components.PageHeader
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.theme.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun HistoricoScreen(navController: NavController) {
    val doacoes = MockData.historicoDoacoes
    val totalMl = doacoes.sumOf { it.volumeMl }
    val concluidas = doacoes.count { it.status == StatusDoacao.CONCLUIDA }

    Scaffold(
        containerColor = OffWhite,
        bottomBar = { BottomNavBar(navController = navController) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            item {
                PageHeader(
                    title = "Minhas Doações",
                    subtitle = "Acompanhe seu histórico de doações.",
                    onBack = null
                )
            }

            item {
                Row(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    MetricChip(
                        icon = Icons.Filled.WaterDrop,
                        value = "${totalMl} ml",
                        label = "Total doado",
                        color = InfoBlue,
                        modifier = Modifier.weight(1f)
                    )
                    MetricChip(
                        icon = Icons.Filled.CheckCircle,
                        value = "$concluidas",
                        label = "Concluídas",
                        color = SuccessGreen,
                        modifier = Modifier.weight(1f)
                    )
                    MetricChip(
                        icon = Icons.Filled.Favorite,
                        value = "${MockData.doadoraMock.bebesAjudados}",
                        label = "Bebês",
                        color = CoralSoft,
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(Modifier.height(16.dp))
            }

            item {
                Text(
                    "HISTÓRICO",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = TextGrayLight
                    ),
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
                )
            }

            items(doacoes) { doacao ->
                DoacaoCard(
                    doacao = doacao,
                    onClick = { navController.navigate("detalhes_agendamento/${doacao.id}") },
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
                )
            }

            item { Spacer(Modifier.height(12.dp)) }
        }
    }
}

@Composable
private fun MetricChip(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    value: String,
    label: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceWhite)
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(icon, null, tint = color, modifier = Modifier.size(20.dp))
        Text(
            value,
            fontFamily = QuicksandFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = TextDark,
            textAlign = TextAlign.Center
        )
        Text(
            label,
            style = MaterialTheme.typography.labelSmall.copy(color = TextGrayLight),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun DoacaoCard(
    doacao: Doacao,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val nutrizStatus = when (doacao.status) {
        StatusDoacao.EM_ANALISE -> NutrizStatus.REVIEW
        StatusDoacao.ELEGIVEL   -> NutrizStatus.ELIGIBLE
        StatusDoacao.AGENDADA   -> NutrizStatus.SCHEDULED
        StatusDoacao.CONCLUIDA  -> NutrizStatus.DONE
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceWhite)
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Ícone de status
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(
                    when (doacao.status) {
                        StatusDoacao.CONCLUIDA -> SuccessGreenSoft
                        StatusDoacao.AGENDADA  -> BluePetroleumSoft
                        else                   -> WarningAmberSoft
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                when (doacao.status) {
                    StatusDoacao.CONCLUIDA -> Icons.Filled.CheckCircle
                    StatusDoacao.AGENDADA  -> Icons.Filled.CalendarToday
                    else                   -> Icons.Filled.AccessTime
                },
                null,
                tint = when (doacao.status) {
                    StatusDoacao.CONCLUIDA -> SuccessGreen
                    StatusDoacao.AGENDADA  -> BluePetroleum
                    else                   -> WarningAmber
                },
                modifier = Modifier.size(24.dp)
            )
        }

        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    doacao.data,
                    fontFamily = QuicksandFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    color = TextDark
                )
                NutrizStatusBadge(status = nutrizStatus)
            }
            Spacer(Modifier.height(4.dp))
            Text(
                "${doacao.volumeMl} ml doados${if (doacao.pontoColeta != null) " · ${doacao.pontoColeta.nome}" else ""}",
                style = MaterialTheme.typography.bodySmall.copy(color = TextGrayLight)
            )
        }

        Icon(Icons.Filled.ChevronRight, null, tint = TextGrayLight, modifier = Modifier.size(18.dp))
    }
}