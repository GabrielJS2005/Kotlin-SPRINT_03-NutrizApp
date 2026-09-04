package GabrielJS2005.com.github.sprint03_nutrizapp.ui.screens.login

import GabrielJS2005.com.github.sprint03_nutrizapp.ui.components.NutrizButton
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.components.NutrizButtonVariant
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.theme.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun LoginScreen(navController: NavController) {
    var step by remember { mutableStateOf<LoginStep>(LoginStep.Phone) }
    var phone by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(OffWhite)
            .padding(horizontal = 24.dp)
    ) {
        Spacer(Modifier.height(16.dp))
        IconButton(
            onClick = {
                when (step) {
                    is LoginStep.Phone -> navController.popBackStack()
                    is LoginStep.Code  -> step = LoginStep.Phone
                }
            },
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(50))
                .background(SurfaceWhite)
        ) {
            Icon(Icons.Filled.ArrowBack, "Voltar", tint = TextDark, modifier = Modifier.size(20.dp))
        }

        when (step) {
            is LoginStep.Phone -> PhoneStep(
                phone = phone,
                onPhoneChange = { phone = formatPhone(it) },
                onContinue = { step = LoginStep.Code }
            )
            is LoginStep.Code -> CodeStep(
                phone = phone,
                onConfirm = {
                    navController.navigate("onboarding") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
    }
}

@Composable
private fun PhoneStep(
    phone: String,
    onPhoneChange: (String) -> Unit,
    onContinue: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(Modifier.height(28.dp))
        Text(
            "Entrar",
            fontFamily = QuicksandFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            color = TextDark
        )
        Text(
            "Vamos confirmar seu telefone para enviar um código de acesso.",
            style = MaterialTheme.typography.bodyMedium.copy(color = TextGrayLight),
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(Modifier.height(32.dp))

        Text("Telefone com DDD", style = MaterialTheme.typography.labelMedium.copy(color = TextGray), modifier = Modifier.padding(bottom = 6.dp, start = 4.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(InputBackground)
                .border(1.dp, BorderColor, RoundedCornerShape(16.dp))
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(Icons.Filled.Phone, null, tint = TextGrayLight, modifier = Modifier.size(18.dp))
            Text("+55", fontFamily = NunitoFontFamily, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextDark)
            androidx.compose.foundation.text.BasicTextField(
                value = phone,
                onValueChange = onPhoneChange,
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 16.dp),
                textStyle = androidx.compose.ui.text.TextStyle(
                    fontFamily = NunitoFontFamily,
                    fontSize = 16.sp,
                    color = TextDark
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                singleLine = true,
                decorationBox = { innerTextField ->
                    if (phone.isEmpty()) {
                        Text("(11) 90000-0000", style = MaterialTheme.typography.bodyLarge.copy(color = TextGrayLight, fontFamily = NunitoFontFamily))
                    }
                    innerTextField()
                }
            )
        }

        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(InfoBlueSoft)
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.Top
        ) {
            Icon(Icons.Filled.Security, null, tint = InfoBlue, modifier = Modifier.size(16.dp))
            Text(
                "Seus dados são protegidos e usados apenas para a doação. Não compartilhamos com terceiros.",
                style = MaterialTheme.typography.bodySmall.copy(color = TextGray)
            )
        }

        Spacer(Modifier.weight(1f))

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Divider(color = BorderColor, thickness = 0.5.dp)
            NutrizButton(
                text = "Continuar",
                onClick = onContinue,
                enabled = phone.replace(Regex("\\D"), "").length >= 10
            )
            NutrizButton(
                text = "Entrar pelo WhatsApp",
                onClick = { /* TODO: WhatsApp deeplink */ },
                variant = NutrizButtonVariant.OUTLINE,
                leadingIcon = {
                    Text("📱", fontSize = 16.sp)
                }
            )
        }
        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun CodeStep(
    phone: String,
    onConfirm: () -> Unit
) {
    var code by remember { mutableStateOf(List(6) { "" }) }
    val focusRequesters = remember { List(6) { FocusRequester() } }
    val filled = code.all { it.isNotEmpty() }

    LaunchedEffect(Unit) {
        focusRequesters[0].requestFocus()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(Modifier.height(28.dp))
        Text(
            "Digite o código",
            fontFamily = QuicksandFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            color = TextDark
        )
        Text(
            "Enviamos um código de 6 dígitos para +55 $phone",
            style = MaterialTheme.typography.bodyMedium.copy(color = TextGrayLight),
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(Modifier.height(36.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(6) { index ->
                val digit = code[index]
                val isSelected = digit.isNotEmpty()
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .border(
                            width = 2.dp,
                            color = if (isSelected) BluePetroleum else BorderColor,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .background(if (isSelected) BluePetroleumSoft else InputBackground),
                    contentAlignment = Alignment.Center
                ) {
                    androidx.compose.foundation.text.BasicTextField(
                        value = digit,
                        onValueChange = { newVal ->
                            val d = newVal.replace(Regex("\\D"), "").takeLast(1)
                            code = code.toMutableList().also { it[index] = d }
                            if (d.isNotEmpty() && index < 5) focusRequesters[index + 1].requestFocus()
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .focusRequester(focusRequesters[index])
                            .onKeyEvent { event ->
                                if (event.key == Key.Backspace && digit.isEmpty() && index > 0) {
                                    focusRequesters[index - 1].requestFocus()
                                    true
                                } else false
                            },
                        textStyle = androidx.compose.ui.text.TextStyle(
                            fontFamily = QuicksandFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            color = BluePetroleum,
                            textAlign = TextAlign.Center
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        decorationBox = { innerTextField ->
                            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                                innerTextField()
                            }
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))
        TextButton(onClick = { }, contentPadding = PaddingValues(0.dp)) {
            Text(
                "Reenviar código em 30s",
                fontFamily = NunitoFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = BluePetroleum
            )
        }

        Spacer(Modifier.weight(1f))
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Divider(color = BorderColor, thickness = 0.5.dp)
            NutrizButton(
                text = "Confirmar",
                onClick = onConfirm,
                enabled = filled
            )
        }
        Spacer(Modifier.height(24.dp))
    }
}

private sealed class LoginStep {
    object Phone : LoginStep()
    object Code  : LoginStep()
}

private fun formatPhone(input: String): String {
    val digits = input.replace(Regex("\\D"), "").take(11)
    return when {
        digits.length <= 2  -> "(${digits}"
        digits.length <= 7  -> "(${digits.take(2)}) ${digits.drop(2)}"
        else                -> "(${digits.take(2)}) ${digits.drop(2).take(5)}-${digits.drop(7)}"
    }
}