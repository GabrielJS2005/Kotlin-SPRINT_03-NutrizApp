package GabrielJS2005.com.github.sprint03_nutrizapp.ui.screens.agendamentos

import GabrielJS2005.com.github.sprint03_nutrizapp.ui.components.BottomNavBar
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.components.NutrizButton
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.components.NutrizButtonVariant
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.components.PageHeader
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.theme.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.util.*

private val MONTHS = listOf("Jan","Fev","Mar","Abr","Mai","Jun","Jul","Ago","Set","Out","Nov","Dez")
private val MONTHS_FULL = listOf("Janeiro","Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro","Outubro","Novembro","Dezembro")
private val WEEKDAYS = listOf("D","S","T","Q","Q","S","S")

private enum class Period(val label: String, val icon: ImageVector) {
    MANHA("Manhã", Icons.Filled.WbSunny),
    TARDE("Tarde", Icons.Filled.WbTwilight),
    NOITE("Noite", Icons.Filled.Bedtime)
}

@Composable
fun AgendamentoScreen(navController: NavController) {
    var selectedDate by remember { mutableStateOf<Calendar?>(null) }
    var selectedPeriod by remember { mutableStateOf<Period?>(null) }
    val today = Calendar.getInstance()
    val calendarRef = remember { mutableStateOf(Calendar.getInstance().apply {
        set(Calendar.DAY_OF_MONTH, 1)
    }) }

    val canConfirm = selectedDate != null && selectedPeriod != null

    Scaffold(
        containerColor = OffWhite,
        bottomBar = { BottomNavBar(navController = navController) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                PageHeader(
                    title = "Agendar coleta",
                    subtitle = "Escolha o melhor dia e período para você.",
                    onBack = { navController.popBackStack() }
                )
            }

            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    SoftCalendar(
                        selectedDate = selectedDate,
                        calendarRef = calendarRef.value,
                        today = today,
                        onDateSelected = { selectedDate = it },
                        onPrevMonth = {
                            calendarRef.value = (calendarRef.value.clone() as Calendar).apply {
                                add(Calendar.MONTH, -1)
                            }
                        },
                        onNextMonth = {
                            calendarRef.value = (calendarRef.value.clone() as Calendar).apply {
                                add(Calendar.MONTH, 1)
                            }
                        }
                    )
                }
            }

            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Text(
                        "Período",
                        fontFamily = QuicksandFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = TextDark,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Period.values().forEach { period ->
                            PeriodButton(
                                period = period,
                                selected = selectedPeriod == period,
                                onClick = { selectedPeriod = period },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }

            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    AddressCard()
                }
            }

            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    OrientationsCard()
                }
            }

            item {
                Column(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    NutrizButton(
                        text = "Confirmar agendamento",
                        onClick = {
                            if (canConfirm) navController.popBackStack()
                        },
                        enabled = canConfirm
                    )
                    NutrizButton(
                        text = "Preciso falar com alguém",
                        onClick = { navController.navigate("suporte") },
                        variant = NutrizButtonVariant.GHOST,
                        leadingIcon = {
                            Icon(Icons.Filled.ChatBubble, null, modifier = Modifier.size(16.dp))
                        }
                    )
                }
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun SoftCalendar(
    selectedDate: Calendar?,
    calendarRef: Calendar,
    today: Calendar,
    onDateSelected: (Calendar) -> Unit,
    onPrevMonth: () -> Unit,
    onNextMonth: () -> Unit
) {
    val year = calendarRef.get(Calendar.YEAR)
    val month = calendarRef.get(Calendar.MONTH)

    val firstDayOfWeek = Calendar.getInstance().apply {
        set(year, month, 1)
    }.get(Calendar.DAY_OF_WEEK) - 1

    val daysInMonth = Calendar.getInstance().apply {
        set(year, month, 1)
        add(Calendar.MONTH, 1)
        add(Calendar.DAY_OF_MONTH, -1)
    }.get(Calendar.DAY_OF_MONTH)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(SurfaceWhite)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onPrevMonth,
                modifier = Modifier.size(36.dp).clip(CircleShape).background(OffWhite)
            ) {
                Icon(Icons.Filled.ChevronLeft, "Mês anterior", tint = TextDark, modifier = Modifier.size(20.dp))
            }
            Text(
                "${MONTHS_FULL[month]} $year",
                fontFamily = QuicksandFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = TextDark
            )
            IconButton(
                onClick = onNextMonth,
                modifier = Modifier.size(36.dp).clip(CircleShape).background(OffWhite)
            ) {
                Icon(Icons.Filled.ChevronRight, "Próximo mês", tint = TextDark, modifier = Modifier.size(20.dp))
            }
        }

        Spacer(Modifier.height(12.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            WEEKDAYS.forEach { day ->
                Text(
                    day,
                    modifier = Modifier.weight(1f),
                    fontFamily = NunitoFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp,
                    color = TextGrayLight,
                    textAlign = TextAlign.Center
                )
            }
        }
        Spacer(Modifier.height(8.dp))

        val totalCells = firstDayOfWeek + daysInMonth
        val rows = (totalCells + 6) / 7

        repeat(rows) { rowIndex ->
            Row(modifier = Modifier.fillMaxWidth()) {
                repeat(7) { colIndex ->
                    val cellIndex = rowIndex * 7 + colIndex
                    val day = cellIndex - firstDayOfWeek + 1

                    if (day < 1 || day > daysInMonth) {
                        Spacer(Modifier.weight(1f).height(40.dp))
                    } else {
                        val dayCal = Calendar.getInstance().apply { set(year, month, day) }
                        val isPast = dayCal.before(today.apply { set(Calendar.HOUR_OF_DAY, 0) })
                        val isSelected = selectedDate?.let {
                            it.get(Calendar.YEAR) == year && it.get(Calendar.MONTH) == month && it.get(Calendar.DAY_OF_MONTH) == day
                        } ?: false
                        val isToday = today.get(Calendar.YEAR) == year && today.get(Calendar.MONTH) == month && today.get(Calendar.DAY_OF_MONTH) == day

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(40.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isSelected) BluePetroleum else Color.Transparent)
                                .then(
                                    if (isToday && !isSelected) Modifier.border(1.5.dp, BluePetroleum, RoundedCornerShape(10.dp))
                                    else Modifier
                                )
                                .then(
                                    if (!isPast) Modifier.clickable { onDateSelected(dayCal) }
                                    else Modifier
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                day.toString(),
                                fontFamily = NunitoFontFamily,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                fontSize = 13.sp,
                                color = when {
                                    isSelected -> Color.White
                                    isPast     -> TextGrayLight.copy(alpha = 0.35f)
                                    else       -> TextDark
                                }
                            )
                        }
                    }
                }
            }
            Spacer(Modifier.height(4.dp))
        }
    }
}

@Composable
private fun PeriodButton(
    period: Period,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .border(2.dp, if (selected) BluePetroleum else BorderColor, RoundedCornerShape(16.dp))
            .background(if (selected) BluePetroleumSoft else SurfaceWhite)
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Icon(
            period.icon,
            contentDescription = period.label,
            tint = if (selected) BluePetroleum else TextGrayLight,
            modifier = Modifier.size(24.dp)
        )
        Text(
            period.label,
            fontFamily = NunitoFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 13.sp,
            color = if (selected) BluePetroleum else TextDark
        )
    }
}

@Composable
private fun AddressCard() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceWhite)
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(MintSoft),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Filled.LocationOn, null, tint = Mint, modifier = Modifier.size(22.dp))
        }
        Column(modifier = Modifier.weight(1f)) {
            Text("ENDEREÇO DE COLETA", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.ExtraBold, color = TextGrayLight))
            Text(
                "Rua das Acácias, 240 — Apt. 32",
                fontFamily = QuicksandFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                color = TextDark,
                modifier = Modifier.padding(top = 2.dp)
            )
            Text("Pinheiros, São Paulo • SP", style = MaterialTheme.typography.bodySmall.copy(color = TextGrayLight))
            TextButton(
                onClick = { },
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.padding(top = 4.dp)
            ) {
                Text("Alterar endereço", fontFamily = NunitoFontFamily, fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = BluePetroleum)
            }
        }
    }
}

@Composable
private fun OrientationsCard() {
    val orientations = listOf(
        "Higienize os frascos conforme orientação.",
        "Mantenha o leite armazenado corretamente no congelador.",
        "Nossa equipe confirmará o horário pelo WhatsApp."
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(GreenWaterSoft)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Filled.AutoAwesome, null, tint = BluePetroleum, modifier = Modifier.size(16.dp))
            Text(
                "Orientações antes da coleta",
                fontFamily = QuicksandFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                color = TextDark
            )
        }
        orientations.forEach { text ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(BluePetroleum),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.Check, null, tint = Color.White, modifier = Modifier.size(12.dp))
                }
                Text(text, style = MaterialTheme.typography.bodySmall.copy(color = TextGray, lineHeight = 18.sp))
            }
        }
    }
}
