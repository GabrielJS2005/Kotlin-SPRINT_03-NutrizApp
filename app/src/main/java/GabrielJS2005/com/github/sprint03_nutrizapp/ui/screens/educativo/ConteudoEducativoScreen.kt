package GabrielJS2005.com.github.sprint03_nutrizapp.ui.screens.educativo

import GabrielJS2005.com.github.sprint03_nutrizapp.data.MockData
import GabrielJS2005.com.github.sprint03_nutrizapp.model.ConteudoEducativo
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.components.AppHeader
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.components.BottomNavBar
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.theme.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

private data class Categoria(val id: String, val label: String, val icon: ImageVector, val iconBg: Color, val iconTint: Color)

private val categorias = listOf(
    Categoria("ordenha",       "Ordenha",          Icons.Filled.WaterDrop,  InfoBlueSoft,      InfoBlue),
    Categoria("armazenamento", "Armazenamento",    Icons.Filled.Inventory2, BluePetroleumSoft, BluePetroleum),
    Categoria("mitos",         "Mitos & verdades", Icons.Filled.Shield,     CoralSoftBg,       CoralSoft),
    Categoria("cuidados",      "Cuidados",         Icons.Filled.Favorite,   MintSoft,          Mint),
    Categoria("faq",           "Perguntas freq.",  Icons.Filled.QuestionAnswer, GreenWaterSoft, GreenWater),
)

@Composable
fun ConteudoEducativoScreen(navController: NavController) {
    val conteudos = MockData.conteudosEducativos
    val totalLidos = conteudos.count { it.lido }
    val pct = if (conteudos.isNotEmpty()) (totalLidos.toFloat() / conteudos.size * 100).toInt() else 0
    var categoriaAtiva by remember { mutableStateOf("all") }

    Scaffold(
        containerColor = OffWhite,
        bottomBar = { BottomNavBar(navController = navController) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            item {
                AppHeader(
                    name = MockData.doadoraMock.nome.split(" ").first(),
                    greeting = "Olá",
                    notificationCount = 0,
                    onNotificationsClick = { navController.navigate("notificacoes") }
                )
            }

            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(CoralSoftBg)
                            .padding(horizontal = 12.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(Icons.Filled.AutoAwesome, null, tint = CoralSoft, modifier = Modifier.size(12.dp))
                        Text("APRENDER", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.ExtraBold, color = CoralSoft))
                    }
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Aprenda rapidinho",
                        fontFamily = QuicksandFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = TextDark
                    )
                    Text(
                        "Conteúdos curtos para apoiar sua doação com segurança.",
                        style = MaterialTheme.typography.bodyMedium.copy(color = TextGrayLight),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
                Spacer(Modifier.height(16.dp))
            }

            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(SurfaceWhite)
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                "SEU PROGRESSO",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.ExtraBold, color = BluePetroleum)
                            )
                            Text(
                                "$totalLidos de ${conteudos.size} conteúdos",
                                fontFamily = QuicksandFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 15.sp,
                                color = TextDark
                            )
                        }
                        Text(
                            "$pct%",
                            fontFamily = QuicksandFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            color = BluePetroleum
                        )
                    }
                    Spacer(Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(50))
                            .background(BorderColor)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(pct / 100f)
                                .fillMaxHeight()
                                .clip(RoundedCornerShape(50))
                                .background(
                                    androidx.compose.ui.graphics.Brush.linearGradient(
                                        listOf(BluePetroleum, GreenWater)
                                    )
                                )
                        )
                    }
                }
                Spacer(Modifier.height(16.dp))
            }

            item {
                Column {
                    Text(
                        "CATEGORIAS",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.ExtraBold, color = TextGrayLight),
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
                    )
                    Row(
                        modifier = Modifier
                            .horizontalScroll(rememberScrollState())
                            .padding(horizontal = 20.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilterChipItem(
                            label = "Todos",
                            selected = categoriaAtiva == "all",
                            onClick = { categoriaAtiva = "all" },
                            icon = Icons.Filled.GridView,
                            iconBg = BluePetroleumSoft,
                            iconTint = BluePetroleum
                        )
                        categorias.forEach { cat ->
                            FilterChipItem(
                                label = cat.label,
                                selected = categoriaAtiva == cat.id,
                                onClick = { categoriaAtiva = cat.id },
                                icon = cat.icon,
                                iconBg = cat.iconBg,
                                iconTint = cat.iconTint
                            )
                        }
                    }
                }
            }

            items(conteudos) { conteudo ->
                ConteudoCard(
                    conteudo = conteudo,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp)
                )
            }

            item { Spacer(Modifier.height(16.dp)) }
        }
    }
}

@Composable
private fun FilterChipItem(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    icon: ImageVector,
    iconBg: Color,
    iconTint: Color
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(if (selected) BluePetroleumSoft else SurfaceWhite)
            .border(
                width = if (selected) 2.dp else 1.dp,
                color = if (selected) BluePetroleum else BorderColor,
                shape = RoundedCornerShape(16.dp),
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Icon(icon, null, tint = if (selected) BluePetroleum else iconTint, modifier = Modifier.size(14.dp))
        Text(
            label,
            fontFamily = NunitoFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 13.sp,
            color = if (selected) BluePetroleum else TextDark
        )
    }
}

@Composable
private fun ConteudoCard(
    conteudo: ConteudoEducativo,
    modifier: Modifier = Modifier
) {
    val (iconBg, iconTint) = if (conteudo.lido)
        SuccessGreenSoft to SuccessGreen
    else
        BluePetroleumSoft to BluePetroleum

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceWhite)
            .clickable { /* TODO: Abrir conteúdo */ }
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(iconBg),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                if (conteudo.lido) Icons.Filled.CheckCircle else Icons.Filled.MenuBook,
                null,
                tint = iconTint,
                modifier = Modifier.size(26.dp)
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Leitura",
                    style = MaterialTheme.typography.labelSmall.copy(color = TextGrayLight)
                )
                Text("·", style = MaterialTheme.typography.labelSmall.copy(color = TextGrayLight))
                Text(
                    "${conteudo.tempoLeituraMin} min",
                    style = MaterialTheme.typography.labelSmall.copy(color = TextGrayLight)
                )
            }
            Text(
                conteudo.titulo,
                fontFamily = QuicksandFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                color = TextDark,
                lineHeight = 20.sp
            )
            if (conteudo.lido) {
                Spacer(Modifier.height(4.dp))
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(SuccessGreenSoft)
                        .padding(horizontal = 8.dp, vertical = 3.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Filled.Check, null, tint = SuccessGreen, modifier = Modifier.size(10.dp))
                    Text("Concluído", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = SuccessGreen))
                }
            }
        }
        Icon(Icons.Filled.ChevronRight, null, tint = TextGrayLight, modifier = Modifier.size(18.dp))
    }
}
