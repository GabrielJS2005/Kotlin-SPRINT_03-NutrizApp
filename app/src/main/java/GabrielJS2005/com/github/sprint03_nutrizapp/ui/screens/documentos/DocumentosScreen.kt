package GabrielJS2005.com.github.sprint03_nutrizapp.ui.screens.documentos

import GabrielJS2005.com.github.sprint03_nutrizapp.ui.components.NutrizButton
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.components.NutrizButtonVariant
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.components.NutrizProgressSteps
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.theme.*
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

private data class DocumentoItem(
    val id: String,
    val label: String,
    val descricao: String,
    val icon: ImageVector,
    val obrigatorio: Boolean = true,
    val dica: String = "",
    var enviado: Boolean = false
)

private val documentosObrigatorios = listOf(
    DocumentoItem(
        id = "prenatal",
        label = "Cartão de Pré-Natal",
        descricao = "Frente e verso completos",
        icon = Icons.Filled.CardMembership,
        obrigatorio = true,
        dica = "Substitui a maioria dos exames individuais."
    ),
    DocumentoItem(
        id = "hiv",
        label = "Sorologia HIV (Anti-HIV 1 e 2)",
        descricao = "Resultado não reagente",
        icon = Icons.Filled.Biotech,
        obrigatorio = true,
        dica = "O vírus HIV contraindica a doação e a amamentação."
    ),
    DocumentoItem(
        id = "sifilis",
        label = "Sorologia Sífilis (VDRL)",
        descricao = "Não reagente ou cura documentada",
        icon = Icons.Filled.Science,
        obrigatorio = true,
        dica = "Caso tratada, comprove o tratamento completo e cura."
    ),
    DocumentoItem(
        id = "hepatite_b",
        label = "Sorologia Hepatite B (HBsAg)",
        descricao = "Resultado não reagente",
        icon = Icons.Filled.LocalHospital,
        obrigatorio = true,
        dica = "Exame obrigatório conforme ANVISA RDC nº 171/2006."
    ),
    DocumentoItem(
        id = "hepatite_c",
        label = "Sorologia Hepatite C (Anti-HCV)",
        descricao = "Resultado não reagente",
        icon = Icons.Filled.MedicalServices,
        obrigatorio = true,
        dica = "Exame obrigatório conforme ANVISA RDC nº 171/2006."
    ),
    DocumentoItem(
        id = "hemograma",
        label = "Hemograma Completo",
        descricao = "Ou Hematócrito / Hemoglobina",
        icon = Icons.Filled.Bloodtype,
        obrigatorio = true,
        dica = "Verifica saúde geral e descarta anemia grave ou infecções ativas."
    )
)

private val documentosComplementares = listOf(
    DocumentoItem(
        id = "htlv",
        label = "Sorologia HTLV I/II",
        descricao = "Conforme perfil epidemiológico",
        icon = Icons.Filled.Science,
        obrigatorio = false,
        dica = "Solicitado conforme histórico ou região de origem."
    ),
    DocumentoItem(
        id = "chagas",
        label = "Sorologia Doença de Chagas",
        descricao = "Conforme histórico clínico",
        icon = Icons.Filled.Biotech,
        obrigatorio = false,
        dica = "Solicitado pelo banco de leite conforme necessidade."
    ),
    DocumentoItem(
        id = "outros",
        label = "Outros documentos",
        descricao = "Laudos, receitas ou prescrições relevantes",
        icon = Icons.Filled.AttachFile,
        obrigatorio = false,
        dica = "Qualquer outro documento que julgue pertinente informar."
    )
)

@Composable
fun DocumentosScreen(
    navController: NavController,
    fromTriagem: Boolean = false
) {
    val TOTAL_STEPS = 2
    var step by remember { mutableStateOf(1) }

    val statusObrig = remember {
        mutableStateMapOf<String, Boolean>().also { map ->
            documentosObrigatorios.forEach { map[it.id] = false }
        }
    }
    val statusComp = remember {
        mutableStateMapOf<String, Boolean>().also { map ->
            documentosComplementares.forEach { map[it.id] = false }
        }
    }

    val totalObrigatoriosEnviados = statusObrig.values.count { it }
    val totalObrigatorios = documentosObrigatorios.size

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(OffWhite)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(listOf(BluePetroleumSoft, GreenWaterSoft))
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 16.dp, bottom = 12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    IconButton(
                        onClick = {
                            if (step > 1) step-- else navController.popBackStack()
                        },
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(SurfaceWhite.copy(alpha = 0.85f))
                    ) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = BluePetroleum,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    NutrizProgressSteps(
                        currentStep = step,
                        totalSteps = TOTAL_STEPS,
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(Modifier.height(12.dp))
                Text(
                    if (step == 1) "Seus documentos" else "Revisar e enviar",
                    fontFamily = QuicksandFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = TextDark
                )
                Text(
                    if (step == 1)
                        "Envie os documentos exigidos pela ANVISA (RDC nº 171/2006) e rBLH/Fiocruz."
                    else
                        "Confira tudo antes de enviar para análise do banco de leite.",
                    style = MaterialTheme.typography.bodySmall.copy(color = TextGrayLight),
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }

        AnimatedContent(
            targetState = step,
            transitionSpec = {
                if (targetState > initialState) {
                    (slideInHorizontally { it } + fadeIn()) togetherWith
                            (slideOutHorizontally { -it } + fadeOut())
                } else {
                    (slideInHorizontally { -it } + fadeIn()) togetherWith
                            (slideOutHorizontally { it } + fadeOut())
                }
            },
            modifier = Modifier.weight(1f),
            label = "StepContent"
        ) { currentStep ->
            when (currentStep) {
                1 -> StepDocumentos(
                    statusObrig = statusObrig,
                    statusComp = statusComp,
                    totalEnviados = totalObrigatoriosEnviados,
                    total = totalObrigatorios
                )
                2 -> StepRevisao(
                    statusObrig = statusObrig,
                    statusComp = statusComp
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(OffWhite)
                .padding(horizontal = 20.dp)
                .padding(bottom = 28.dp, top = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            HorizontalDivider(
                color = BorderColor,
                thickness = 0.5.dp,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            if (step == 1) {
                // Badge de progresso
                if (totalObrigatoriosEnviados > 0) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(SuccessGreenSoft)
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Filled.CheckCircle,
                            null,
                            tint = SuccessGreen,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            "$totalObrigatoriosEnviados de $totalObrigatorios documentos obrigatórios enviados.",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = SuccessGreen,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }
                NutrizButton(
                    text = "Revisar e continuar",
                    onClick = { step = 2 }
                )
            } else {
                NutrizButton(
                    text = if (fromTriagem) "Agendar coleta" else "Enviar documentos",
                    onClick = {
                        if (fromTriagem) {
                            navController.navigate("agendamento") {
                                popUpTo("triagem") { inclusive = true }
                            }
                        } else {
                            navController.popBackStack()
                        }
                    }
                )
                NutrizButton(
                    text = "Voltar e editar",
                    onClick = { step = 1 },
                    variant = NutrizButtonVariant.GHOST
                )
            }
        }
    }
}

@Composable
private fun StepDocumentos(
    statusObrig: MutableMap<String, Boolean>,
    statusComp: MutableMap<String, Boolean>,
    totalEnviados: Int,
    total: Int
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(
                    Brush.linearGradient(listOf(BluePetroleumSoft, GreenWaterSoft))
                )
                .padding(14.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(SurfaceWhite.copy(alpha = 0.8f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Filled.VerifiedUser,
                    null,
                    tint = BluePetroleum,
                    modifier = Modifier.size(20.dp)
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "Documentação exigida pela ANVISA",
                    fontFamily = QuicksandFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = TextDark
                )
                Text(
                    "RDC nº 171/2006 · rBLH/Fiocruz. O cartão de pré-natal substitui a maioria dos exames individuais.",
                    style = MaterialTheme.typography.bodySmall.copy(color = TextGrayLight),
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }

        SectionHeader(
            title = "Exames obrigatórios",
            subtitle = "$totalEnviados de $total enviados",
            color = BluePetroleum
        )

        documentosObrigatorios.forEach { doc ->
            val enviado = statusObrig[doc.id] == true
            DocumentUploadCard(
                doc = doc,
                enviado = enviado,
                onToggle = { statusObrig[doc.id] = !enviado }
            )
        }

        SectionHeader(
            title = "Exames complementares",
            subtitle = "Conforme perfil epidemiológico ou histórico clínico",
            color = GreenWater
        )

        documentosComplementares.forEach { doc ->
            val enviado = statusComp[doc.id] == true
            DocumentUploadCard(
                doc = doc,
                enviado = enviado,
                onToggle = { statusComp[doc.id] = !enviado }
            )
        }

        Spacer(Modifier.height(8.dp))
    }
}

@Composable
private fun StepRevisao(
    statusObrig: Map<String, Boolean>,
    statusComp: Map<String, Boolean>
) {
    val obrigOk = documentosObrigatorios.filter { statusObrig[it.id] == true }
    val obrigPend = documentosObrigatorios.filter { statusObrig[it.id] != true }
    val compOk = documentosComplementares.filter { statusComp[it.id] == true }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val allObrigOk = obrigPend.isEmpty()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(if (allObrigOk) SuccessGreenSoft else WarningAmberSoft)
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                if (allObrigOk) Icons.Filled.CheckCircle else Icons.Filled.Warning,
                null,
                tint = if (allObrigOk) SuccessGreen else WarningAmber,
                modifier = Modifier.size(24.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    if (allObrigOk) "Documentação completa!" else "Documentação incompleta",
                    fontFamily = QuicksandFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = if (allObrigOk) SuccessGreen else WarningAmber
                )
                Text(
                    if (allObrigOk)
                        "Todos os documentos obrigatórios foram enviados."
                    else
                        "${obrigPend.size} documento(s) obrigatório(s) pendente(s). Você pode continuar, mas a análise poderá ser solicitada depois.",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = if (allObrigOk) SuccessGreen else WarningAmber
                    )
                )
            }
        }

        if (obrigOk.isNotEmpty()) {
            ReviewSection(title = "Obrigatórios enviados ✅") {
                obrigOk.forEach { doc ->
                    ReviewDocRow(doc.label, true)
                }
            }
        }

        if (obrigPend.isNotEmpty()) {
            ReviewSection(title = "Obrigatórios pendentes ⚠️") {
                obrigPend.forEach { doc ->
                    ReviewDocRow(doc.label, false)
                }
            }
        }

        if (compOk.isNotEmpty()) {
            ReviewSection(title = "Complementares enviados") {
                compOk.forEach { doc ->
                    ReviewDocRow(doc.label, true)
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(InfoBlueSoft)
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                Icons.Filled.Info,
                null,
                tint = InfoBlue,
                modifier = Modifier.size(16.dp).padding(top = 2.dp)
            )
            Text(
                "Ao enviar, seus documentos serão analisados pela equipe do banco de leite. Você receberá um retorno pelo WhatsApp.",
                style = MaterialTheme.typography.bodySmall.copy(color = TextGray)
            )
        }

        Spacer(Modifier.height(8.dp))
    }
}

@Composable
private fun SectionHeader(title: String, subtitle: String, color: Color) {
    Column(modifier = Modifier.padding(start = 2.dp)) {
        Text(
            title.uppercase(),
            fontFamily = QuicksandFontFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 11.sp,
            color = color
        )
        Text(
            subtitle,
            style = MaterialTheme.typography.bodySmall.copy(color = TextGrayLight)
        )
    }
}

@Composable
private fun DocumentUploadCard(
    doc: DocumentoItem,
    enviado: Boolean,
    onToggle: () -> Unit
) {
    val cardBg = if (enviado) SuccessGreenSoft else SurfaceWhite
    val borderColor = if (enviado) SuccessGreen.copy(alpha = 0.4f) else BorderColor
    val iconBg = if (enviado) SuccessGreenSoft else BluePetroleumSoft
    val iconTint = if (enviado) SuccessGreen else BluePetroleum

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(cardBg)
            .border(1.dp, borderColor, RoundedCornerShape(16.dp))
            .padding(14.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(iconBg),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                if (enviado) Icons.Filled.CheckCircle else doc.icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(22.dp)
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    doc.label,
                    fontFamily = NunitoFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = TextDark,
                    modifier = Modifier.weight(1f)
                )
                if (!doc.obrigatorio) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(GreenWaterSoft)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            "opcional",
                            fontFamily = NunitoFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 10.sp,
                            color = GreenWater
                        )
                    }
                }
            }
            Text(
                doc.descricao,
                style = MaterialTheme.typography.bodySmall.copy(color = TextGrayLight),
                modifier = Modifier.padding(top = 1.dp)
            )
            if (doc.dica.isNotEmpty()) {
                Spacer(Modifier.height(4.dp))
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (enviado) SuccessGreenSoft else BluePetroleumSoft)
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Filled.Info,
                        null,
                        tint = if (enviado) SuccessGreen else BluePetroleum,
                        modifier = Modifier.size(10.dp)
                    )
                    Text(
                        doc.dica,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = if (enviado) SuccessGreen else TextGray,
                            fontWeight = FontWeight.Normal
                        )
                    )
                }
            }
            Spacer(Modifier.height(10.dp))
            Button(
                onClick = onToggle,
                modifier = Modifier.height(34.dp),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(horizontal = 14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (enviado) SuccessGreen else BluePetroleum,
                    contentColor = SurfaceWhite
                )
            ) {
                Icon(
                    if (enviado) Icons.Filled.CheckCircle else Icons.Filled.Upload,
                    null,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(Modifier.width(5.dp))
                Text(
                    if (enviado) "Enviado ✓" else "Enviar arquivo",
                    fontFamily = NunitoFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
private fun ReviewSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceWhite)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            title,
            fontFamily = QuicksandFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            color = TextDark
        )
        HorizontalDivider(color = BorderColor, thickness = 0.5.dp)
        content()
    }
}

@Composable
private fun ReviewDocRow(label: String, enviado: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            label,
            style = MaterialTheme.typography.bodySmall.copy(color = TextGray),
            modifier = Modifier.weight(1f).padding(end = 8.dp)
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                if (enviado) Icons.Filled.CheckCircle else Icons.Filled.AccessTime,
                null,
                tint = if (enviado) SuccessGreen else WarningAmber,
                modifier = Modifier.size(14.dp)
            )
            Text(
                if (enviado) "Enviado" else "Pendente",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = if (enviado) SuccessGreen else WarningAmber
                )
            )
        }
    }
}
