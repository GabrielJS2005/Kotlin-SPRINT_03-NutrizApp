package GabrielJS2005.com.github.sprint03_nutrizapp.ui.screens.detalhes

import GabrielJS2005.com.github.sprint03_nutrizapp.data.MockData
import GabrielJS2005.com.github.sprint03_nutrizapp.model.StatusDoacao
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.components.NutrizButton
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.components.NutrizButtonVariant
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.components.NutrizStatus
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.components.NutrizStatusBadge
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.components.PageHeader
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.components.StateScreen
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.components.StateVariant
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.theme.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun DetalhesAgendamentoScreen(navController: NavController, agendamentoId: String) {
    val doacao = MockData.historicoDoacoes.find { it.id == agendamentoId }

    val nutrizStatus = doacao?.let {
        when (it.status) {
            StatusDoacao.EM_ANALISE -> NutrizStatus.REVIEW
            StatusDoacao.ELEGIVEL   -> NutrizStatus.ELIGIBLE
            StatusDoacao.AGENDADA   -> NutrizStatus.SCHEDULED
            StatusDoacao.CONCLUIDA  -> NutrizStatus.DONE
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(OffWhite)
    ) {
        PageHeader(
            title = "Detalhes da doação",
            onBack = { navController.popBackStack() }
        )

        if (doacao != null && nutrizStatus != null) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(SurfaceWhite)
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            doacao.data,
                            fontFamily = QuicksandFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = TextDark
                        )
                        NutrizStatusBadge(status = nutrizStatus)
                    }

                    Divider(color = BorderColor, thickness = 0.5.dp)

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Filled.WaterDrop, null, tint = InfoBlue, modifier = Modifier.size(18.dp))
                        Text("${doacao.volumeMl} ml doados", fontFamily = NunitoFontFamily, fontWeight = FontWeight.SemiBold, fontSize = 15.sp, color = TextDark)
                    }

                    if (doacao.pontoColeta != null) {
                        Row(
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(Icons.Filled.LocationOn, null, tint = Mint, modifier = Modifier.size(18.dp))
                            Column {
                                Text(doacao.pontoColeta.nome, fontFamily = NunitoFontFamily, fontWeight = FontWeight.SemiBold, fontSize = 15.sp, color = TextDark)
                                Text(doacao.pontoColeta.endereco, style = MaterialTheme.typography.bodySmall.copy(color = TextGrayLight))
                            }
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(BorderColor),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(Icons.Filled.Map, null, tint = TextGrayLight, modifier = Modifier.size(36.dp))
                        Text("Mapa em breve", style = MaterialTheme.typography.bodySmall.copy(color = TextGrayLight))
                    }
                }

                Spacer(Modifier.weight(1f))

                if (doacao.status == StatusDoacao.AGENDADA) {
                    NutrizButton(
                        text = "Reagendar coleta",
                        onClick = { navController.navigate("agendamento") },
                        variant = NutrizButtonVariant.OUTLINE
                    )
                    NutrizButton(
                        text = "Cancelar coleta",
                        onClick = { navController.popBackStack() },
                        variant = NutrizButtonVariant.CORAL
                    )
                    Spacer(Modifier.height(16.dp))
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                StateScreen(
                    variant = StateVariant.EMPTY,
                    title = "Doação não encontrada",
                    description = "Não conseguimos encontrar os dados desta doação.",
                    primaryAction = {
                        NutrizButton(text = "Voltar", onClick = { navController.popBackStack() })
                    }
                )
            }
        }
    }
}