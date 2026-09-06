package GabrielJS2005.com.github.sprint03_nutrizapp.ui.screens.perfil

import GabrielJS2005.com.github.sprint03_nutrizapp.data.MockData
import GabrielJS2005.com.github.sprint03_nutrizapp.model.Filho
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.theme.*
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DadosPessoaisScreen(navController: NavController) {
    val doadora = MockData.doadoraMock

    var isEditing by remember { mutableStateOf(false) }
    var showSavedSnackbar by remember { mutableStateOf(false) }

    var nome by remember { mutableStateOf(doadora.nome) }
    var dataNascimento by remember { mutableStateOf(doadora.dataNascimento) }
    var cidade by remember { mutableStateOf(doadora.cidade) }
    var bairro by remember { mutableStateOf(doadora.bairro) }
    var telefone by remember { mutableStateOf(doadora.telefone) }
    var email by remember { mutableStateOf(doadora.email) }
    var possuiFilhos by remember { mutableStateOf(doadora.possuiFilhos) }
    var filhos by remember { mutableStateOf(doadora.filhos.toMutableList()) }

    var nomeBackup by remember { mutableStateOf(doadora.nome) }
    var dataNascimentoBackup by remember { mutableStateOf(doadora.dataNascimento) }
    var cidadeBackup by remember { mutableStateOf(doadora.cidade) }
    var bairroBackup by remember { mutableStateOf(doadora.bairro) }
    var telefoneBackup by remember { mutableStateOf(doadora.telefone) }
    var emailBackup by remember { mutableStateOf(doadora.email) }
    var possuiFilhosBackup by remember { mutableStateOf(doadora.possuiFilhos) }
    var filhosBackup by remember { mutableStateOf(doadora.filhos.toMutableList()) }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(showSavedSnackbar) {
        if (showSavedSnackbar) {
            snackbarHostState.showSnackbar("Dados salvos com sucesso!")
            showSavedSnackbar = false
        }
    }

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
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .align(Alignment.CenterStart)
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

                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Dados Pessoais",
                        fontFamily = QuicksandFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = TextDark
                    )
                    Text(
                        "Meu cadastro",
                        style = MaterialTheme.typography.bodySmall.copy(color = TextGrayLight)
                    )
                }

                IconButton(
                    onClick = {
                        if (isEditing) {
                            nome = nomeBackup
                            dataNascimento = dataNascimentoBackup
                            cidade = cidadeBackup
                            bairro = bairroBackup
                            telefone = telefoneBackup
                            email = emailBackup
                            possuiFilhos = possuiFilhosBackup
                            filhos = filhosBackup.toMutableList()
                        } else {
                            nomeBackup = nome
                            dataNascimentoBackup = dataNascimento
                            cidadeBackup = cidade
                            bairroBackup = bairro
                            telefoneBackup = telefone
                            emailBackup = email
                            possuiFilhosBackup = possuiFilhos
                            filhosBackup = filhos.toMutableList()
                        }
                        isEditing = !isEditing
                    },
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(if (isEditing) CoralSoftBg else SurfaceWhite.copy(alpha = 0.85f))
                ) {
                    Icon(
                        if (isEditing) Icons.Filled.Close else Icons.Filled.Edit,
                        contentDescription = if (isEditing) "Cancelar" else "Editar",
                        tint = if (isEditing) CoralSoft else BluePetroleum,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AnimatedVisibility(
                    visible = isEditing,
                    enter = expandVertically() + fadeIn(),
                    exit = shrinkVertically() + fadeOut()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(InfoBlueSoft)
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Filled.Edit,
                            contentDescription = null,
                            tint = InfoBlue,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            "Modo edição ativo. Altere os campos e toque em Salvar.",
                            style = MaterialTheme.typography.bodySmall.copy(color = TextGray)
                        )
                    }
                }

                DataSection(
                    title = "Identificação",
                    icon = Icons.Filled.Person
                ) {
                    PersonalDataField(
                        label = "Nome completo",
                        value = nome,
                        onValueChange = { nome = it },
                        isEditing = isEditing,
                        icon = Icons.Filled.Badge
                    )
                    DataDivider()
                    PersonalDataField(
                        label = "Data de nascimento",
                        value = dataNascimento,
                        onValueChange = { dataNascimento = it },
                        isEditing = isEditing,
                        icon = Icons.Filled.CalendarToday,
                        keyboardType = KeyboardType.Number,
                        placeholder = "dd/mm/aaaa"
                    )
                }

                DataSection(
                    title = "Localização",
                    icon = Icons.Filled.LocationOn
                ) {
                    PersonalDataField(
                        label = "Cidade",
                        value = cidade,
                        onValueChange = { cidade = it },
                        isEditing = isEditing,
                        icon = Icons.Filled.LocationCity
                    )
                    DataDivider()
                    PersonalDataField(
                        label = "Bairro",
                        value = bairro,
                        onValueChange = { bairro = it },
                        isEditing = isEditing,
                        icon = Icons.Filled.Map
                    )
                }
                DataSection(
                    title = "Contato",
                    icon = Icons.Filled.ContactPhone
                ) {
                    PersonalDataField(
                        label = "Telefone",
                        value = telefone,
                        onValueChange = { telefone = it },
                        isEditing = isEditing,
                        icon = Icons.Filled.Phone,
                        keyboardType = KeyboardType.Phone,
                        placeholder = "(11) 90000-0000"
                    )
                    DataDivider()
                    PersonalDataField(
                        label = "E-mail",
                        value = email,
                        onValueChange = { email = it },
                        isEditing = isEditing,
                        icon = Icons.Filled.Email,
                        keyboardType = KeyboardType.Email,
                        placeholder = "seu@email.com"
                    )
                }
                DataSection(
                    title = "Filhos",
                    icon = Icons.Filled.ChildCare
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(BluePetroleumSoft),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Filled.FamilyRestroom,
                                    contentDescription = null,
                                    tint = BluePetroleum,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            Column {
                                Text(
                                    "Possui filhos?",
                                    fontFamily = NunitoFontFamily,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 14.sp,
                                    color = TextDark
                                )
                                Text(
                                    if (possuiFilhos) "${filhos.size} filho(s) cadastrado(s)"
                                    else "Nenhum filho cadastrado",
                                    style = MaterialTheme.typography.bodySmall.copy(color = TextGrayLight)
                                )
                            }
                        }
                        Switch(
                            checked = possuiFilhos,
                            onCheckedChange = {
                                if (isEditing) {
                                    possuiFilhos = it
                                    if (!it) filhos = mutableListOf()
                                }
                            },
                            enabled = isEditing,
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = SurfaceWhite,
                                checkedTrackColor = BluePetroleum,
                                uncheckedThumbColor = SurfaceWhite,
                                uncheckedTrackColor = BorderColor
                            )
                        )
                    }

                    AnimatedVisibility(
                        visible = possuiFilhos,
                        enter = expandVertically() + fadeIn(),
                        exit = shrinkVertically() + fadeOut()
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(0.dp)) {
                            DataDivider()
                            filhos.forEachIndexed { index, filho ->
                                ChildCard(
                                    index = index,
                                    filho = filho,
                                    isEditing = isEditing,
                                    onNomeChange = { newNome ->
                                        filhos = filhos.toMutableList().also {
                                            it[index] = it[index].copy(nome = newNome)
                                        }
                                    },
                                    onDataChange = { newData ->
                                        filhos = filhos.toMutableList().also {
                                            it[index] = it[index].copy(dataNascimento = newData)
                                        }
                                    },
                                    onRemove = {
                                        filhos = filhos.toMutableList().also { it.removeAt(index) }
                                    }
                                )
                                if (index < filhos.size - 1) DataDivider()
                            }

                            AnimatedVisibility(visible = isEditing) {
                                Column {
                                    if (filhos.isNotEmpty()) DataDivider()
                                    TextButton(
                                        onClick = {
                                            val newId = "f${filhos.size + 1}_new"
                                            filhos = filhos.toMutableList().also {
                                                it.add(Filho(newId, "", ""))
                                            }
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 8.dp, vertical = 4.dp)
                                    ) {
                                        Icon(
                                            Icons.Filled.Add,
                                            contentDescription = null,
                                            tint = BluePetroleum,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(Modifier.width(4.dp))
                                        Text(
                                            "Adicionar filho",
                                            fontFamily = NunitoFontFamily,
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 13.sp,
                                            color = BluePetroleum
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                AnimatedVisibility(
                    visible = isEditing,
                    enter = expandVertically() + fadeIn(),
                    exit = shrinkVertically() + fadeOut()
                ) {
                    Button(
                        onClick = {
                            isEditing = false
                            showSavedSnackbar = true
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BluePetroleum,
                            contentColor = SurfaceWhite
                        )
                    ) {
                        Icon(
                            Icons.Filled.Save,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            "Salvar alterações",
                            fontFamily = QuicksandFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))
            }
        }
    }
}


@Composable
private fun DataSection(
    title: String,
    icon: ImageVector,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(SurfaceWhite)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(BluePetroleumSoft.copy(alpha = 0.5f))
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(icon, contentDescription = null, tint = BluePetroleum, modifier = Modifier.size(16.dp))
            Text(
                title.uppercase(),
                fontFamily = QuicksandFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp,
                color = BluePetroleum
            )
        }
        content()
    }
}

@Composable
private fun PersonalDataField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isEditing: Boolean,
    icon: ImageVector,
    keyboardType: KeyboardType = KeyboardType.Text,
    placeholder: String = ""
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(BluePetroleumSoft),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = BluePetroleum, modifier = Modifier.size(16.dp))
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                label,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = TextGrayLight,
                    fontWeight = FontWeight.SemiBold
                )
            )
            Spacer(Modifier.height(2.dp))
            if (isEditing) {
                BasicEditField(
                    value = value,
                    onValueChange = onValueChange,
                    placeholder = placeholder.ifEmpty { label },
                    keyboardType = keyboardType
                )
            } else {
                Text(
                    text = value.ifEmpty { "—" },
                    fontFamily = NunitoFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    color = if (value.isEmpty()) TextGrayLight else TextDark
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BasicEditField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                placeholder,
                style = MaterialTheme.typography.bodySmall.copy(color = TextGrayLight)
            )
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = BluePetroleum,
            unfocusedBorderColor = BorderColor,
            focusedLabelColor = BluePetroleum,
            cursorColor = BluePetroleum,
            focusedContainerColor = BluePetroleumSoft.copy(alpha = 0.3f),
            unfocusedContainerColor = Color.Transparent
        ),
        textStyle = LocalTextStyle.current.copy(
            fontFamily = NunitoFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            color = TextDark
        )
    )
}

@Composable
private fun ChildCard(
    index: Int,
    filho: Filho,
    isEditing: Boolean,
    onNomeChange: (String) -> Unit,
    onDataChange: (String) -> Unit,
    onRemove: () -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(GreenWaterSoft),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "${index + 1}",
                    fontFamily = QuicksandFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = GreenWater
                )
            }
            Text(
                "Filho ${index + 1}",
                fontFamily = QuicksandFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = TextDark,
                modifier = Modifier.weight(1f)
            )
            if (isEditing) {
                IconButton(
                    onClick = onRemove,
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(CoralSoftBg)
                ) {
                    Icon(
                        Icons.Filled.Remove,
                        contentDescription = "Remover filho",
                        tint = CoralSoft,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        if (isEditing) {
            BasicEditField(
                value = filho.nome,
                onValueChange = onNomeChange,
                placeholder = "Nome completo do filho"
            )
            Spacer(Modifier.height(8.dp))
            BasicEditField(
                value = filho.dataNascimento,
                onValueChange = onDataChange,
                placeholder = "Data de nascimento (dd/mm/aaaa)",
                keyboardType = KeyboardType.Number
            )
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        filho.nome.ifEmpty { "—" },
                        fontFamily = NunitoFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp,
                        color = if (filho.nome.isEmpty()) TextGrayLight else TextDark
                    )
                    Text(
                        "Nasc.: ${filho.dataNascimento.ifEmpty { "—" }}",
                        style = MaterialTheme.typography.bodySmall.copy(color = TextGrayLight)
                    )
                }
            }
        }
    }
}

@Composable
private fun DataDivider() {
    Divider(
        modifier = Modifier.padding(horizontal = 16.dp),
        color = BorderColor,
        thickness = 0.5.dp
    )
}