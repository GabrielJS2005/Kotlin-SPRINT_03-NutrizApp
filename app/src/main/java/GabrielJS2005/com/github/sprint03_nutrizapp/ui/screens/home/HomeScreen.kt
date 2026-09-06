package GabrielJS2005.com.github.sprint03_nutrizapp.ui.screens.home

import GabrielJS2005.com.github.sprint03_nutrizapp.data.MockData
import GabrielJS2005.com.github.sprint03_nutrizapp.model.StatusDoacao
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.components.AppHeader
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.components.BottomNavBar
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.components.ImpactCard
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.components.JourneyCard
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.components.JourneyStatus
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.components.ShortcutData
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.components.ShortcutGrid
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.theme.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    val doadora = MockData.doadoraMock

    val journeyStatus = when (doadora.status) {
        StatusDoacao.EM_ANALISE -> JourneyStatus.REVIEW
        StatusDoacao.ELEGIVEL   -> JourneyStatus.READY
        StatusDoacao.AGENDADA   -> JourneyStatus.SCHEDULED
        StatusDoacao.CONCLUIDA  -> JourneyStatus.READY
    }

    Scaffold(
        bottomBar = { BottomNavBar(navController = navController) },
        containerColor = OffWhite
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            item {
                AppHeader(
                    name = doadora.nome.split(" ").first(),
                    greeting = "Olá",
                    notificationCount = 2,
                    onNotificationsClick = { navController.navigate("notificacoes") }
                )
            }

            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    JourneyCard(
                        status = journeyStatus,
                        onDetailsClick = { navController.navigate("triagem") }
                    )
                }
                Spacer(Modifier.height(20.dp))
            }



            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    ImpactCard(
                        babiesHelped = doadora.bebesAjudados,
                        mlDonated = (doadora.litrosDoados * 1000).toInt(),
                        donationsCount = MockData.historicoDoacoes.size
                    )
                }
                Spacer(Modifier.height(20.dp))
            }

            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    SectionLabel("Atalhos")
                    ShortcutGrid(
                        shortcuts = listOf(
                            ShortcutData(
                                icon = Icons.Filled.CalendarToday,
                                label = "Agendar coleta",
                                iconBackground = CoralSoftBg,
                                iconTint = CoralSoft,
                                onClick = { navController.navigate("agendamento") }
                            ),
                            ShortcutData(
                                icon = Icons.Filled.ChatBubble,
                                label = "Falar com suporte",
                                iconBackground = MintSoft,
                                iconTint = Mint,
                                onClick = {
                                    navController.navigate("suporte") {
                                        popUpTo("home") { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            ),
                            ShortcutData(
                                icon = Icons.Filled.MenuBook,
                                label = "Conteúdos",
                                iconBackground = GreenWaterSoft,
                                iconTint = GreenWater,
                                onClick = {
                                    navController.navigate("educativo") {
                                        popUpTo("home") { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            ),
                            ShortcutData(
                                icon = Icons.Filled.Assignment,
                                label = "Meu cadastro",
                                iconBackground = BluePetroleumSoft,
                                iconTint = BluePetroleum,
                                onClick = { navController.navigate("dados_pessoais") }
                            )
                        )
                    )
                }
                Spacer(Modifier.height(20.dp))
            }

            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    SectionLabel("Aprenda em 2 minutos")
                    MicroLearningCard(
                        title = "Como ordenhar e armazenar o leite com segurança",
                        onClick = {
                            navController.navigate("educativo") {
                                popUpTo("home") { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
                Spacer(Modifier.height(28.dp))
            }
        }
    }
}


@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text.uppercase(),
        style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = FontWeight.ExtraBold,
            color = TextGrayLight
        ),
        modifier = Modifier.padding(bottom = 10.dp, start = 2.dp)
    )
}



@Composable
private fun MicroLearningCard(
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(
                Brush.linearGradient(listOf(GreenWaterSoft, BluePetroleumSoft, MintSoft))
            )
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(SurfaceWhite.copy(alpha = 0.85f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Filled.PlayCircle,
                contentDescription = null,
                tint = BluePetroleum,
                modifier = Modifier.size(36.dp)
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(SurfaceWhite.copy(alpha = 0.80f))
                    .padding(horizontal = 8.dp, vertical = 3.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(Icons.Filled.AutoAwesome, contentDescription = null, tint = BluePetroleum, modifier = Modifier.size(10.dp))
                Text(
                    "Aprenda em 2 minutos",
                    fontFamily = NunitoFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp,
                    color = BluePetroleum
                )
            }
            Spacer(Modifier.height(6.dp))
            Text(
                title,
                fontFamily = QuicksandFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                color = TextDark,
                lineHeight = 20.sp
            )
        }
    }
}
