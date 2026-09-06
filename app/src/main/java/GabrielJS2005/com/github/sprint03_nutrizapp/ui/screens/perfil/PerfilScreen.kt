package GabrielJS2005.com.github.sprint03_nutrizapp.ui.screens.perfil

import GabrielJS2005.com.github.sprint03_nutrizapp.data.MockData
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

private data class ProfileMenuItem(
    val label: String,
    val icon: ImageVector,
    val route: String? = null,
    val onClick: (() -> Unit)? = null
)

@Composable
fun PerfilScreen(navController: NavController) {
    val doadora = MockData.doadoraMock
    val initial = doadora.nome.firstOrNull()?.uppercaseChar()?.toString() ?: "M"

    val menuItems = listOf(
        ProfileMenuItem("Dados pessoais",           Icons.Filled.Person,          route = "dados_pessoais"),
        ProfileMenuItem("Meus documentos",          Icons.Filled.Article,         route = "meus_documentos"),
        ProfileMenuItem("Preferências de contato",  Icons.Filled.NotificationsNone),
        ProfileMenuItem("Segurança e privacidade",  Icons.Filled.Security),
        ProfileMenuItem("Acessibilidade",           Icons.Filled.Accessibility),
        ProfileMenuItem("Ajuda e suporte",          Icons.Filled.HelpOutline,     route = "suporte"),
    )

    val nutrizStatus = when (doadora.status) {
        StatusDoacao.EM_ANALISE -> NutrizStatus.REVIEW
        StatusDoacao.ELEGIVEL   -> NutrizStatus.ELIGIBLE
        StatusDoacao.AGENDADA   -> NutrizStatus.SCHEDULED
        StatusDoacao.CONCLUIDA  -> NutrizStatus.DONE
    }

    Scaffold(
        bottomBar = { BottomNavBar(navController = navController) },
        containerColor = OffWhite
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            item {
                PageHeader(
                    title = "Perfil",
                    onBack = null
                )
            }

            item {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .background(
                            Brush.linearGradient(
                                listOf(BluePetroleumSoft, GreenWaterSoft, CoralSoftBg)
                            )
                        )
                        .padding(20.dp)
                ) {
                    Column {

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {

                            Box(
                                modifier = Modifier
                                    .size(64.dp)
                                    .clip(CircleShape)
                                    .background(SurfaceWhite),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    initial,
                                    fontFamily = QuicksandFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 28.sp,
                                    color = BluePetroleum
                                )
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    doadora.nome,
                                    fontFamily = QuicksandFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    color = TextDark
                                )
                                Text(
                                    "nutriz · São Paulo, SP",
                                    style = MaterialTheme.typography.bodySmall.copy(color = TextGrayLight)
                                )
                                Spacer(Modifier.height(6.dp))
                                NutrizStatusBadge(status = nutrizStatus)
                            }
                        }

                        Spacer(Modifier.height(16.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            listOf(
                                Triple(Icons.Filled.Edit,         "Editar",     { navController.navigate("dados_pessoais") }),
                                Triple(Icons.Filled.Article,      "Documentos", { navController.navigate("documentos") }),
                                Triple(Icons.Filled.Notifications,"Notificar",  { navController.navigate("notificacoes") })
                            ).forEach { (icon, label, action) ->
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(SurfaceWhite.copy(alpha = 0.80f))
                                        .clickable(onClick = action)
                                        .padding(vertical = 10.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(icon, contentDescription = label, tint = BluePetroleum, modifier = Modifier.size(18.dp))
                                    Text(label, fontFamily = NunitoFontFamily, fontWeight = FontWeight.SemiBold, fontSize = 11.sp, color = TextDark)
                                }
                            }
                        }
                    }
                }
                Spacer(Modifier.height(24.dp))
            }
            item {
                Text(
                    "Minha conta",
                    fontFamily = QuicksandFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = TextDark,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
                )
                Spacer(Modifier.height(8.dp))

                Column(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(SurfaceWhite)
                ) {
                    menuItems.forEachIndexed { index, item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    if (item.route != null) navController.navigate(item.route)
                                    item.onClick?.invoke()
                                }
                                .padding(horizontal = 16.dp, vertical = 14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(BluePetroleumSoft),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(item.icon, contentDescription = null, tint = BluePetroleum, modifier = Modifier.size(18.dp))
                            }
                            Text(
                                item.label,
                                modifier = Modifier.weight(1f),
                                fontFamily = NunitoFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp,
                                color = TextDark
                            )
                            Icon(Icons.Filled.ChevronRight, contentDescription = null, tint = TextGrayLight, modifier = Modifier.size(18.dp))
                        }
                        if (index < menuItems.size - 1) {
                            Divider(modifier = Modifier.padding(horizontal = 16.dp), color = BorderColor, thickness = 0.5.dp)
                        }
                    }
                }
                Spacer(Modifier.height(16.dp))
            }
            item {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(SurfaceWhite)
                        .clickable { /* TODO: Logout */ }
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Filled.ExitToApp, contentDescription = "Sair", tint = CoralSoft, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Sair da conta", fontFamily = NunitoFontFamily, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = CoralSoft)
                }
                Spacer(Modifier.height(8.dp))
                Text(
                    "Nutriz+ · v1.0",
                    style = MaterialTheme.typography.labelSmall.copy(color = TextGrayLight.copy(alpha = 0.6f)),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
                Spacer(Modifier.height(24.dp))
            }
        }
    }
}