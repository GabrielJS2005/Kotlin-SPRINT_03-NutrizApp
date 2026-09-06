package GabrielJS2005.com.github.sprint03_nutrizapp.ui.screens.cadastro

import GabrielJS2005.com.github.sprint03_nutrizapp.ui.components.NutrizButton
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.components.NutrizButtonVariant
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.components.NutrizProgressSteps
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.components.SoftField
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.theme.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

private const val TOTAL_STEPS = 4

@Composable
fun CadastroScreen(navController: NavController) {
    var step by remember { mutableStateOf(1) }
    var nome by remember { mutableStateOf("") }
    var dataNascimento by remember { mutableStateOf("") }
    var cidade by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var dataBebe by remember { mutableStateOf("") }

    val stepTitle = when (step) {
        1 -> "Vamos te conhecer"
        2 -> "Sobre o bebê"
        3 -> "Seus documentos"
        else -> "Revisar e enviar"
    }
    val stepSubtitle = when (step) {
        1 -> "Comece com algumas informações básicas. Leva só 1 minuto."
        2 -> "O cadastro respeita a exaustão do puerpério. Preencha o essencial agora."
        3 -> "Precisamos de alguns documentos para validar seu cadastro."
        else -> "Confirme suas informações antes de enviar."
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(OffWhite)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(top = 16.dp, bottom = 8.dp),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            IconButton(
                onClick = {
                    if (step > 1) step-- else navController.popBackStack()
                },
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(50))
                    .background(SurfaceWhite)
            ) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Voltar", tint = TextDark, modifier = Modifier.size(20.dp))
            }
            NutrizProgressSteps(
                currentStep = step,
                totalSteps = TOTAL_STEPS,
                modifier = Modifier.weight(1f)
            )
        }

        Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)) {
            Text(
                stepTitle,
                fontFamily = QuicksandFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = TextDark
            )
            Text(
                stepSubtitle,
                style = MaterialTheme.typography.bodyMedium.copy(color = TextGrayLight),
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when (step) {
                1 -> {
                    SoftField(
                        label = "Nome completo",
                        value = nome,
                        onValueChange = { nome = it },
                        placeholder = "Como está no documento",
                        leadingIcon = { Icon(Icons.Filled.Person, null, Modifier.size(18.dp)) }
                    )
                    SoftField(
                        label = "Data de nascimento",
                        value = dataNascimento,
                        onValueChange = { dataNascimento = it },
                        placeholder = "dd/mm/aaaa",
                        keyboardType = KeyboardType.Number,
                        leadingIcon = { Icon(Icons.Filled.CalendarToday, null, Modifier.size(18.dp)) }
                    )
                    SoftField(
                        label = "Cidade / Bairro",
                        value = cidade,
                        onValueChange = { cidade = it },
                        placeholder = "Ex: São Paulo / Pinheiros",
                        leadingIcon = { Icon(Icons.Filled.LocationOn, null, Modifier.size(18.dp)) }
                    )
                    SoftField(
                        label = "Telefone",
                        value = telefone,
                        onValueChange = { telefone = it },
                        placeholder = "(11) 90000-0000",
                        keyboardType = KeyboardType.Phone,
                        leadingIcon = { Icon(Icons.Filled.Phone, null, Modifier.size(18.dp)) }
                    )
                    SoftField(
                        label = "E-mail (opcional)",
                        value = email,
                        onValueChange = { email = it },
                        placeholder = "seu@email.com",
                        keyboardType = KeyboardType.Email,
                        leadingIcon = { Icon(Icons.Filled.Email, null, Modifier.size(18.dp)) }
                    )
                }
                2 -> {
                    SoftField(
                        label = "Data de nascimento do bebê",
                        value = dataBebe,
                        onValueChange = { dataBebe = it },
                        placeholder = "dd/mm/aaaa",
                        keyboardType = KeyboardType.Number,
                        leadingIcon = { Icon(Icons.Filled.ChildCare, null, Modifier.size(18.dp)) }
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(InfoBlueSoft)
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Filled.Info, null, tint = InfoBlue, modifier = Modifier.size(16.dp).padding(top = 2.dp))
                        Text(
                            "O cadastro respeita a exaustão do puerpério. Você pode salvar e continuar depois.",
                            style = MaterialTheme.typography.bodySmall.copy(color = TextGray)
                        )
                    }
                }
                3 -> {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        DocumentUploadCard(
                            label = "RG ou CNH",
                            description = "Frente e verso",
                            icon = Icons.Filled.CreditCard
                        )
                        DocumentUploadCard(
                            label = "Resultado de exame",
                            description = "Exames do pré-natal ou puerpério",
                            icon = Icons.Filled.Biotech
                        )
                    }
                }
                4 -> {
                    // Revisão
                    ReviewSection(title = "Dados pessoais") {
                        ReviewRow("Nome", nome.ifEmpty { "—" })
                        ReviewRow("Data de nasc.", dataNascimento.ifEmpty { "—" })
                        ReviewRow("Cidade", cidade.ifEmpty { "—" })
                        ReviewRow("Telefone", telefone.ifEmpty { "—" })
                    }
                    ReviewSection(title = "Dados do bebê") {
                        ReviewRow("Nasc. do bebê", dataBebe.ifEmpty { "—" })
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(SuccessGreenSoft)
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Filled.CheckCircle, null, tint = SuccessGreen, modifier = Modifier.size(16.dp))
                        Text(
                            "Ao enviar, seus dados serão analisados pela equipe do banco de leite. Você receberá um retorno pelo WhatsApp.",
                            style = MaterialTheme.typography.bodySmall.copy(color = TextGray)
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(OffWhite)
                .padding(horizontal = 20.dp)
                .padding(bottom = 24.dp, top = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Divider(color = BorderColor, thickness = 0.5.dp, modifier = Modifier.padding(bottom = 4.dp))
            NutrizButton(
                text = if (step < TOTAL_STEPS) "Continuar" else "Enviar cadastro",
                onClick = {
                    if (step < TOTAL_STEPS) step++
                    else navController.popBackStack()
                }
            )
            NutrizButton(
                text = "Salvar e continuar depois",
                onClick = { navController.popBackStack() },
                variant = NutrizButtonVariant.GHOST
            )
        }
    }
}

@Composable
private fun DocumentUploadCard(
    label: String,
    description: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceWhite)
            .padding(16.dp),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(BluePetroleumSoft),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            Icon(icon, null, tint = BluePetroleum, modifier = Modifier.size(24.dp))
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(label, fontFamily = NunitoFontFamily, fontWeight = FontWeight.SemiBold, fontSize = 15.sp, color = TextDark)
            Text(description, style = MaterialTheme.typography.bodySmall.copy(color = TextGrayLight))
        }
        Button(
            onClick = { /* TODO: File picker */ },
            modifier = Modifier.height(36.dp),
            shape = RoundedCornerShape(12.dp),
            contentPadding = PaddingValues(horizontal = 12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = BluePetroleumSoft, contentColor = BluePetroleum)
        ) {
            Icon(Icons.Filled.Upload, null, modifier = Modifier.size(14.dp))
            Spacer(Modifier.width(4.dp))
            Text("Enviar", fontFamily = NunitoFontFamily, fontWeight = FontWeight.Bold, fontSize = 12.sp)
        }
    }
}

@Composable
private fun ReviewSection(title: String, content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceWhite)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(title, fontFamily = QuicksandFontFamily, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = BluePetroleum)
        Divider(color = BorderColor, thickness = 0.5.dp)
        content()
    }
}

@Composable
private fun ReviewRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, style = MaterialTheme.typography.bodySmall.copy(color = TextGrayLight))
        Text(value, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold, color = TextDark))
    }
}