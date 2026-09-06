package GabrielJS2005.com.github.sprint03_nutrizapp.ui.screens.triagem

import GabrielJS2005.com.github.sprint03_nutrizapp.ui.components.NutrizButton
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.components.NutrizButtonVariant
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.components.NutrizProgressSteps
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.components.PageHeader
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.components.StateScreen
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.components.StateVariant
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.theme.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

private data class PerguntaTriagem(
    val pergunta: String,
    val dica: String,
    val respostaEsperada: Boolean
)

private val perguntas = listOf(
    PerguntaTriagem(
        pergunta = "Você está saudável, não tendo apresentado quadros de doenças infectocontagiosas ativas ou sintomas gripais/febris nos últimos dias?",
        dica = "Sua saúde e a dos bebês são prioridade.",
        respostaEsperada = true
    ),
    PerguntaTriagem(
        pergunta = "Você acredita produzir um volume de leite excedente às necessidades básicas do seu próprio filho?",
        dica = "A prioridade número um deve ser sempre o seu bebê.",
        respostaEsperada = true
    ),
    PerguntaTriagem(
        pergunta = "Você consome álcool com frequência, fuma mais de 10 cigarros ao dia ou consumiu drogas ilícitas nos últimos dias?",
        dica = "Substâncias no organismo impedem a doação temporariamente.",
        respostaEsperada = false
    )
)

@Composable
fun TriagemScreen(navController: NavController) {
    var perguntaAtual by remember { mutableStateOf(0) }
    var respostas by remember { mutableStateOf(listOf<Boolean>()) }
    var finalizado by remember { mutableStateOf(false) }
    var aprovado by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(OffWhite)
    ) {

        PageHeader(
            title = "Triagem rápida",
            subtitle = "Responda para verificar sua elegibilidade.",
            onBack = { navController.popBackStack() }
        )

        if (!finalizado) {
            NutrizProgressSteps(
                currentStep = perguntaAtual + 1,
                totalSteps = perguntas.size,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
            )
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            if (!finalizado) {
                val perguntaData = perguntas[perguntaAtual]

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(24.dp))
                            .background(
                                Brush.linearGradient(listOf(BluePetroleumSoft, OffWhite))
                            )
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(BluePetroleumSoft),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("❓", fontSize = 30.sp, textAlign = TextAlign.Center)
                        }
                        Text(
                            text = "${perguntaAtual + 1} de ${perguntas.size}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = TextGrayLight,
                                fontWeight = FontWeight.ExtraBold
                            )
                        )
                        Text(
                            text = perguntaData.pergunta,
                            fontFamily = QuicksandFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            color = TextDark,
                            textAlign = TextAlign.Center,
                            lineHeight = 28.sp
                        )
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(BluePetroleumSoft)
                                .padding(horizontal = 10.dp, vertical = 6.dp),
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Filled.Info, null, tint = BluePetroleum, modifier = Modifier.size(12.dp))
                            Text(
                                perguntaData.dica,
                                style = MaterialTheme.typography.bodySmall.copy(color = TextGray)
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(20.dp))
                                .background(SuccessGreenSoft)
                                .clickable {
                                    val novasRespostas = respostas + true
                                    respostas = novasRespostas
                                    if (perguntaAtual < perguntas.size - 1) {
                                        perguntaAtual++
                                    } else {
                                        aprovado = novasRespostas.mapIndexed { index, r -> r == perguntas[index].respostaEsperada }.all { it }
                                        finalizado = true
                                    }
                                }
                                .padding(vertical = 20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text("✅", fontSize = 32.sp, textAlign = TextAlign.Center)
                            Text(
                                "Sim",
                                fontFamily = NunitoFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = SuccessGreen
                            )
                        }
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(20.dp))
                                .background(CoralSoftBg)
                                .clickable {
                                    val novasRespostas = respostas + false
                                    respostas = novasRespostas
                                    if (perguntaAtual < perguntas.size - 1) {
                                        perguntaAtual++
                                    } else {
                                        aprovado = novasRespostas.mapIndexed { index, r -> r == perguntas[index].respostaEsperada }.all { it }
                                        finalizado = true
                                    }
                                }
                                .padding(vertical = 20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text("❌", fontSize = 32.sp, textAlign = TextAlign.Center)
                            Text(
                                "Não",
                                fontFamily = NunitoFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = CoralSoft
                            )
                        }
                    }
                }
            } else {
                StateScreen(
                    variant = if (aprovado) StateVariant.SUCCESS else StateVariant.ERROR,
                    title = if (aprovado) "Triagem concluída!" else "Atenção necessária",
                    description = if (aprovado)
                        "Com base nas suas respostas, você está elegível para doação! O próximo passo é enviar seus documentos (exames ANVISA/rBLH). Após a análise, você poderá agendar a coleta."
                    else
                        "Poxa, muito obrigada pela sua iniciativa, mas infelizmente no momento você não atende a todos os requisitos mínimos para doação. Sua saúde e a dos bebês são nossa prioridade. Continue se cuidando e, futuramente, tente novamente!",
                    eyebrow = if (aprovado) "Tudo certo" else "Quase lá",
                    primaryAction = {
                        NutrizButton(
                            text = if (aprovado) "Enviar documentos" else "Falar com suporte",
                            onClick = {
                                if (aprovado) {
                                    navController.navigate("documentos_triagem")
                                } else {
                                    navController.popBackStack()
                                    navController.navigate("suporte")
                                }
                            }
                        )
                    },
                    secondaryAction = {
                        NutrizButton(
                            text = "Voltar ao início",
                            onClick = { navController.navigate("home") { popUpTo("home") { inclusive = true } } },
                            variant = NutrizButtonVariant.GHOST
                        )
                    }
                )
            }
        }
    }
}