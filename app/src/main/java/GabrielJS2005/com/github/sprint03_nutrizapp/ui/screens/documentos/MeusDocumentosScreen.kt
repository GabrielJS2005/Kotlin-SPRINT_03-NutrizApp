package GabrielJS2005.com.github.sprint03_nutrizapp.ui.screens.documentos

import GabrielJS2005.com.github.sprint03_nutrizapp.ui.theme.*
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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

private enum class StatusDocumento {
    APROVADO, EM_ANALISE, PENDENTE, REJEITADO
}

private data class DocumentoEnviado(
    val id: String,
    val label: String,
    val descricao: String,
    val icon: ImageVector,
    val obrigatorio: Boolean = true,
    val status: StatusDocumento,
    val dataEnvio: String = "",
    val observacao: String = ""
)

private val documentosEnviados = listOf(
    DocumentoEnviado(
        id = "prenatal",
        label = "Cartão de Pré-Natal",
        descricao = "Frente e verso completos",
        icon = Icons.Filled.CardMembership,
        obrigatorio = true,
        status = StatusDocumento.APROVADO,
        dataEnvio = "10/07/2026",
        observacao = "Documento verificado e aceito pela equipe."
    ),
    DocumentoEnviado(
        id = "hiv",
        label = "Sorologia HIV (Anti-HIV 1 e 2)",
        descricao = "Resultado não reagente",
        icon = Icons.Filled.Biotech,
        obrigatorio = true,
        status = StatusDocumento.APROVADO,
        dataEnvio = "10/07/2026",
        observacao = "Resultado não reagente confirmado."
    ),
    DocumentoEnviado(
        id = "sifilis",
        label = "Sorologia Sífilis (VDRL)",
        descricao = "Não reagente ou cura documentada",
        icon = Icons.Filled.Science,
        obrigatorio = true,
        status = StatusDocumento.APROVADO,
        dataEnvio = "10/07/2026",
        observacao = "Resultado não reagente confirmado."
    ),
    DocumentoEnviado(
        id = "hepatite_b",
        label = "Sorologia Hepatite B (HBsAg)",
        descricao = "Resultado não reagente",
        icon = Icons.Filled.LocalHospital,
        obrigatorio = true,
        status = StatusDocumento.EM_ANALISE,
        dataEnvio = "22/08/2026",
        observacao = "Em análise pela equipe do banco de leite."
    ),
    DocumentoEnviado(
        id = "hepatite_c",
        label = "Sorologia Hepatite C (Anti-HCV)",
        descricao = "Resultado não reagente",
        icon = Icons.Filled.MedicalServices,
        obrigatorio = true,
        status = StatusDocumento.PENDENTE,
        dataEnvio = "",
        observacao = "Documento ainda não enviado."
    ),
    DocumentoEnviado(
        id = "hemograma",
        label = "Hemograma Completo",
        descricao = "Ou Hematócrito / Hemoglobina",
        icon = Icons.Filled.Bloodtype,
        obrigatorio = true,
        status = StatusDocumento.PENDENTE,
        dataEnvio = "",
        observacao = "Documento ainda não enviado."
    )
)

private val documentosCompEnviados = listOf(
    DocumentoEnviado(
        id = "htlv",
        label = "Sorologia HTLV I/II",
        descricao = "Conforme perfil epidemiológico",
        icon = Icons.Filled.Science,
        obrigatorio = false,
        status = StatusDocumento.APROVADO,
        dataEnvio = "10/07/2026",
        observacao = "Documento aceito."
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeusDocumentosScreen(navController: NavController) {
    val snackbarHostState = remember { SnackbarHostState() }
    var showReplaceSnackbar by remember { mutableStateOf("") }

    LaunchedEffect(showReplaceSnackbar) {
        if (showReplaceSnackbar.isNotEmpty()) {
            snackbarHostState.showSnackbar("Novo arquivo enviado para: $showReplaceSnackbar")
            showReplaceSnackbar = ""
        }
    }

    val aprovados = documentosEnviados.count { it.status == StatusDocumento.APROVADO }
    val emAnalise = documentosEnviados.count { it.status == StatusDocumento.EM_ANALISE }
    val pendentes = documentosEnviados.count { it.status == StatusDocumento.PENDENTE }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = OffWhite
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
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
                        .padding(top = 16.dp, bottom = 16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        IconButton(
                            onClick = { navController.popBackStack() },
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
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                "Meus Documentos",
                                fontFamily = QuicksandFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                color = TextDark
                            )
                            Text(
                                "Visualize e gerencie seus documentos enviados",
                                style = MaterialTheme.typography.bodySmall.copy(color = TextGrayLight)
                            )
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        SummaryChip(
                            count = aprovados,
                            label = "Aprovados",
                            color = SuccessGreen,
                            bg = SuccessGreenSoft,
                            modifier = Modifier.weight(1f)
                        )
                        SummaryChip(
                            count = emAnalise,
                            label = "Em análise",
                            color = WarningAmber,
                            bg = WarningAmberSoft,
                            modifier = Modifier.weight(1f)
                        )
                        SummaryChip(
                            count = pendentes,
                            label = "Pendentes",
                            color = CoralSoft,
                            bg = CoralSoftBg,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(InfoBlueSoft)
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        Icons.Filled.Info,
                        null,
                        tint = InfoBlue,
                        modifier = Modifier.size(16.dp).padding(top = 1.dp)
                    )
                    Text(
                        "Aqui você visualiza o status dos seus documentos. Para substituir um arquivo, toque em \"Reenviar\". Novos documentos devem ser adicionados na tela de Documentos.",
                        style = MaterialTheme.typography.bodySmall.copy(color = TextGray)
                    )
                }

                SectionLabel(
                    title = "Exames obrigatórios",
                    subtitle = "ANVISA RDC nº 171/2006 · rBLH/Fiocruz",
                    color = BluePetroleum
                )

                documentosEnviados.forEach { doc ->
                    DocumentoViewCard(
                        doc = doc,
                        onReenviar = { showReplaceSnackbar = doc.label }
                    )
                }

                if (documentosCompEnviados.isNotEmpty()) {
                    SectionLabel(
                        title = "Exames complementares",
                        subtitle = "Documentos adicionais enviados",
                        color = GreenWater
                    )
                    documentosCompEnviados.forEach { doc ->
                        DocumentoViewCard(
                            doc = doc,
                            onReenviar = { showReplaceSnackbar = doc.label }
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(SurfaceWhite)
                        .border(1.dp, BorderColor, RoundedCornerShape(16.dp))
                        .clickable { navController.navigate("documentos") }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(BluePetroleumSoft),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Filled.AddCircle,
                            null,
                            tint = BluePetroleum,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            "Adicionar novos documentos",
                            fontFamily = NunitoFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = TextDark
                        )
                        Text(
                            "Envie documentos pendentes ou adicionais",
                            style = MaterialTheme.typography.bodySmall.copy(color = TextGrayLight)
                        )
                    }
                    Icon(
                        Icons.Filled.ChevronRight,
                        null,
                        tint = TextGrayLight,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun SummaryChip(
    count: Int,
    label: String,
    color: Color,
    bg: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceWhite.copy(alpha = 0.85f))
            .padding(vertical = 8.dp, horizontal = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(bg),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "$count",
                fontFamily = QuicksandFontFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 14.sp,
                color = color
            )
        }
        Text(
            label,
            fontFamily = NunitoFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 10.sp,
            color = TextGrayLight
        )
    }
}

@Composable
private fun SectionLabel(title: String, subtitle: String, color: Color) {
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
private fun DocumentoViewCard(
    doc: DocumentoEnviado,
    onReenviar: () -> Unit
) {
    var expandido by remember { mutableStateOf(false) }

    val (statusColor, statusBg, statusText, statusIcon) = when (doc.status) {
        StatusDocumento.APROVADO    -> Quadruple(SuccessGreen, SuccessGreenSoft, "Aprovado", Icons.Filled.CheckCircle)
        StatusDocumento.EM_ANALISE  -> Quadruple(WarningAmber, WarningAmberSoft, "Em análise", Icons.Filled.AccessTime)
        StatusDocumento.PENDENTE    -> Quadruple(CoralSoft, CoralSoftBg, "Pendente", Icons.Filled.ErrorOutline)
        StatusDocumento.REJEITADO   -> Quadruple(ErrorRed, ErrorRedSoft, "Rejeitado", Icons.Filled.Cancel)
    }

    val borderColor = when (doc.status) {
        StatusDocumento.APROVADO    -> SuccessGreen.copy(alpha = 0.3f)
        StatusDocumento.EM_ANALISE  -> WarningAmber.copy(alpha = 0.3f)
        StatusDocumento.PENDENTE    -> BorderColor
        StatusDocumento.REJEITADO   -> ErrorRed.copy(alpha = 0.3f)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceWhite)
            .border(1.dp, borderColor, RoundedCornerShape(16.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expandido = !expandido }
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(statusBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    statusIcon,
                    contentDescription = null,
                    tint = statusColor,
                    modifier = Modifier.size(22.dp)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        doc.label,
                        fontFamily = NunitoFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = TextDark,
                        modifier = Modifier.weight(1f)
                    )
                    if (!doc.obrigatorio) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(GreenWaterSoft)
                                .padding(horizontal = 5.dp, vertical = 1.dp)
                        ) {
                            Text(
                                "opcional",
                                fontFamily = NunitoFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 9.sp,
                                color = GreenWater
                            )
                        }
                    }
                }
                Text(
                    doc.descricao,
                    style = MaterialTheme.typography.bodySmall.copy(color = TextGrayLight)
                )
                Spacer(Modifier.height(6.dp))
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(statusBg)
                        .padding(horizontal = 8.dp, vertical = 3.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(statusIcon, null, tint = statusColor, modifier = Modifier.size(11.dp))
                    Text(
                        statusText,
                        fontFamily = NunitoFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        color = statusColor
                    )
                    if (doc.dataEnvio.isNotEmpty()) {
                        Text(
                            "· ${doc.dataEnvio}",
                            style = MaterialTheme.typography.labelSmall.copy(color = TextGrayLight)
                        )
                    }
                }
            }

            Icon(
                if (expandido) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = null,
                tint = TextGrayLight,
                modifier = Modifier.size(20.dp)
            )
        }

        AnimatedVisibility(
            visible = expandido,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        when (doc.status) {
                            StatusDocumento.APROVADO   -> SuccessGreenSoft.copy(alpha = 0.5f)
                            StatusDocumento.EM_ANALISE -> WarningAmberSoft.copy(alpha = 0.5f)
                            StatusDocumento.PENDENTE   -> OffWhite
                            StatusDocumento.REJEITADO  -> ErrorRedSoft.copy(alpha = 0.5f)
                        }
                    )
            ) {
                HorizontalDivider(color = borderColor, thickness = 1.dp)
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    if (doc.observacao.isNotEmpty()) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Icon(
                                Icons.Filled.Comment,
                                null,
                                tint = statusColor,
                                modifier = Modifier.size(14.dp).padding(top = 1.dp)
                            )
                            Text(
                                doc.observacao,
                                style = MaterialTheme.typography.bodySmall.copy(color = TextGray)
                            )
                        }
                    }

                    if (doc.dataEnvio.isNotEmpty()) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Filled.CalendarToday,
                                null,
                                tint = TextGrayLight,
                                modifier = Modifier.size(13.dp)
                            )
                            Text(
                                "Enviado em ${doc.dataEnvio}",
                                style = MaterialTheme.typography.labelSmall.copy(color = TextGrayLight)
                            )
                        }
                    }

                    val showReenviarButton = doc.status != StatusDocumento.APROVADO ||
                            doc.status == StatusDocumento.REJEITADO
                    if (showReenviarButton || doc.status == StatusDocumento.PENDENTE) {
                        Button(
                            onClick = onReenviar,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(38.dp),
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = when (doc.status) {
                                    StatusDocumento.PENDENTE  -> BluePetroleum
                                    StatusDocumento.REJEITADO -> ErrorRed
                                    else                       -> WarningAmber
                                },
                                contentColor = SurfaceWhite
                            )
                        ) {
                            Icon(
                                if (doc.status == StatusDocumento.PENDENTE)
                                    Icons.Filled.Upload
                                else
                                    Icons.Filled.Refresh,
                                null,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(Modifier.width(6.dp))
                            Text(
                                if (doc.status == StatusDocumento.PENDENTE) "Enviar arquivo"
                                else "Reenviar arquivo",
                                fontFamily = NunitoFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

private data class Quadruple<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)
