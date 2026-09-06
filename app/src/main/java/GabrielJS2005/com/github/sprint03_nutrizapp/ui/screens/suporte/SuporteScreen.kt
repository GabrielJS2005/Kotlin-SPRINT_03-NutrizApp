package GabrielJS2005.com.github.sprint03_nutrizapp.ui.screens.suporte

import GabrielJS2005.com.github.sprint03_nutrizapp.ui.components.BottomNavBar
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

private val faqs = listOf(
    "Posso doar leite mesmo tomando vitaminas?" to
            "Sim! Vitaminas e suplementos comuns não impedem a doação. Nossa equipe avaliará no seu cadastro.",
    "Como devo higienizar os frascos?" to
            "Lave com água e sabão neutro, enxágue bem e deixe secar em suporte limpo.",
    "Posso congelar o leite antes de agendar?" to
            "Sim! O leite congelado pode ser coletado normalmente. Guarde por até 15 dias.",
    "O que acontece se eu tiver febre no dia da coleta?" to
            "Adie a coleta e avise pelo WhatsApp. Sua segurança e a do bebê são prioridade."
)

@Composable
fun SuporteScreen(navController: NavController) {
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
                    title = "Suporte",
                    subtitle = "Nossa equipe está aqui para ajudar você.",
                    onBack = null
                )
            }

            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(24.dp))
                            .background(
                                androidx.compose.ui.graphics.Brush.linearGradient(
                                    listOf(Color(0xFF25D366), Color(0xFF128C7E))
                                )
                            )
                            .padding(20.dp)
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(CircleShape)
                                        .background(Color.White.copy(alpha = 0.2f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("📱", fontSize = 24.sp)
                                }
                                Column {
                                    Text(
                                        "Equipe Nutriz+",
                                        fontFamily = QuicksandFontFamily,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp,
                                        color = Color.White
                                    )
                                    Text(
                                        "Atendimento de seg. a sex., 8h às 18h",
                                        style = MaterialTheme.typography.bodySmall.copy(color = Color.White.copy(alpha = 0.85f))
                                    )
                                }
                            }
                            Button(
                                onClick = { /* TODO: WhatsApp intent */ },
                                modifier = Modifier.fillMaxWidth().height(48.dp),
                                shape = RoundedCornerShape(14.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.White,
                                    contentColor = Color(0xFF128C7E)
                                )
                            ) {
                                Icon(Icons.Filled.ChatBubble, null, modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    "Iniciar conversa no WhatsApp",
                                    fontFamily = NunitoFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                            }
                        }
                    }
                }
                Spacer(Modifier.height(20.dp))
            }

            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Text(
                        "OUTRAS FORMAS DE CONTATO",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = TextGrayLight
                        ),
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    Column(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(SurfaceWhite),
                        verticalArrangement = Arrangement.spacedBy(0.dp)
                    ) {
                        ContactRow(Icons.Filled.Phone, "Ligar", "(11) 3456-7890", BluePetroleum)
                        Divider(modifier = Modifier.padding(horizontal = 16.dp), color = BorderColor, thickness = 0.5.dp)
                        ContactRow(Icons.Filled.Email, "E-mail", "lactare@saude.sp.gov.br", GreenWater)
                        Divider(modifier = Modifier.padding(horizontal = 16.dp), color = BorderColor, thickness = 0.5.dp)
                        ContactRow(Icons.Filled.LocationOn, "Endereço", "Av. Albert Einstein, 627 — Morumbi", Mint)
                    }
                }
                Spacer(Modifier.height(20.dp))
            }

            
            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Text(
                        "PERGUNTAS FREQUENTES",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = TextGrayLight
                        ),
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        faqs.forEach { (pergunta, resposta) ->
                            FaqCard(pergunta = pergunta, resposta = resposta)
                        }
                    }
                }
                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun ContactRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String,
    tint: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(tint.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, null, tint = tint, modifier = Modifier.size(18.dp))
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(label, style = MaterialTheme.typography.labelSmall.copy(color = TextGrayLight, fontWeight = FontWeight.Bold))
            Text(value, fontFamily = NunitoFontFamily, fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = TextDark)
        }
        Icon(Icons.Filled.ChevronRight, null, tint = TextGrayLight, modifier = Modifier.size(16.dp))
    }
}

@Composable
private fun FaqCard(pergunta: String, resposta: String) {
    var expanded by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceWhite)
            .clickable { expanded = !expanded }
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                pergunta,
                fontFamily = NunitoFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = TextDark,
                modifier = Modifier.weight(1f)
            )
            Icon(
                if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                null,
                tint = TextGrayLight,
                modifier = Modifier.size(18.dp)
            )
        }
        if (expanded) {
            Text(
                resposta,
                style = MaterialTheme.typography.bodySmall.copy(color = TextGrayLight, lineHeight = 18.sp)
            )
        }
    }
}
