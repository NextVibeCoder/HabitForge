package com.example.habitforge.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.habitforge.ui.viewmodel.AppViewModelProvider
import com.example.habitforge.ui.viewmodel.HabitDetailViewModel
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle as JavaTextStyle
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitDetailScreen(
    habitId: Long,
    viewModel: HabitDetailViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onNavigateBack: () -> Unit = {},
    onNavigateToEdit: (Long) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    
    val diasMap = mapOf(
        0 to "LUNES",
        1 to "MARTES",
        2 to "MIERCOLES",
        3 to "JUEVES",
        4 to "VIERNES",
        5 to "SABADO",
        6 to "DOMINGO"
    )

    val backgroundColor = Color(0xFF020617)
    val cardColor = Color(0xFF1E293B).copy(alpha = 0.5f)
    val primaryBlue = Color(0xFF4D8AFF)
    val textColor = Color.White
    val secondaryTextColor = Color(0xFF94A3B8)
    val redColor = Color(0xFFEF4444)

    var showInviteDialog by remember { mutableStateOf(false) }
    var inviteEmail by remember { mutableStateOf("") }

    LaunchedEffect(habitId) {
        viewModel.cargarHabito(habitId)
    }

    LaunchedEffect(uiState.isDeleted) {
        if (uiState.isDeleted) {
            onNavigateBack()
        }
    }

    if (showInviteDialog) {
        AlertDialog(
            onDismissRequest = { showInviteDialog = false },
            title = { Text("Invitar Amigo", color = textColor) },
            text = {
                OutlinedTextField(
                    value = inviteEmail,
                    onValueChange = { inviteEmail = it },
                    label = { Text("Correo electrónico") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = textColor,
                        unfocusedTextColor = textColor
                    )
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.invitarAmigo(inviteEmail)
                        showInviteDialog = false
                        inviteEmail = ""
                    }
                ) {
                    Text("Enviar Invitación")
                }
            },
            dismissButton = {
                TextButton(onClick = { showInviteDialog = false }) {
                    Text("Cancelar")
                }
            },
            containerColor = Color(0xFF1E293B)
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Text("Detalle del Hábito", color = textColor, fontSize = 18.sp, fontWeight = FontWeight.Bold) 
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás", tint = textColor)
                    }
                },
                actions = {
                    IconButton(onClick = { onNavigateToEdit(habitId) }) {
                        Icon(Icons.Default.Edit, contentDescription = "Editar", tint = primaryBlue)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = backgroundColor)
            )
        },
        containerColor = backgroundColor
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(color = primaryBlue)
                Spacer(modifier = Modifier.height(20.dp))
            }

            uiState.error?.let {
                Text(text = it, color = redColor)
                Spacer(modifier = Modifier.height(20.dp))
            }

            // Card Principal
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = cardColor)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .background(Color(0xFF0F172A), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = uiState.habit?.icon ?: "🎯", fontSize = 28.sp)
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = uiState.habit?.nombre ?: "Cargando...", color = textColor, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            Surface(
                                color = Color(0xFF334155).copy(alpha = 0.5f),
                                shape = RoundedCornerShape(4.dp),
                                modifier = Modifier.padding(top = 4.dp)
                            ) {
                                Text(
                                    text = uiState.habit?.frecuencia?.name ?: "",
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                    style = TextStyle(color = secondaryTextColor, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = uiState.habit?.descripcion ?: "",
                        color = secondaryTextColor,
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Nueva sección: Días del hábito
                    Text(
                        text = "PROGRAMACIÓN",
                        color = textColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        val dias = listOf("L", "Ma", "M", "J", "V", "S", "D")
                        val diasHabilitados = uiState.habit?.diasSemana?.map { it.name } ?: emptyList()
                        val esDiario = uiState.habit?.frecuencia?.name == "DIARIA"

                        dias.forEachIndexed { index, dia ->
                            val isSelected = esDiario || diasHabilitados.any { it.startsWith(diasMap[index] ?: "") }
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(if (isSelected) primaryBlue else Color(0xFF0F172A))
                                    .border(1.dp, if (isSelected) primaryBlue else Color(0xFF334155), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = dia,
                                    color = if (isSelected) Color.White else secondaryTextColor,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    if (uiState.habit?.esCompartido == true) {
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = "PARTICIPANTES",
                            color = textColor,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        uiState.habit?.participantes?.forEach { p ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clip(CircleShape)
                                        .background(primaryBlue.copy(alpha = 0.2f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = (p.nombreUsuario ?: "?").take(1).uppercase(),
                                        color = primaryBlue,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = p.nombreUsuario ?: "Usuario ${p.usuarioId}",
                                    color = textColor,
                                    fontSize = 14.sp
                                )
                                if (p.completadoHoy) {
                                    Spacer(modifier = Modifier.weight(1f))
                                    Icon(
                                        Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = Color(0xFF10B981),
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { showInviteDialog = true },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = primaryBlue.copy(alpha = 0.2f)),
                            contentPadding = PaddingValues(8.dp)
                        ) {
                            Icon(Icons.Default.PersonAdd, contentDescription = null, tint = primaryBlue, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Agregar participantes", color = primaryBlue, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Stats
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatCard(modifier = Modifier.weight(1f), label = "RACHA ACTUAL", value = uiState.currentStreak.toString(), icon = Icons.Default.Whatshot, iconColor = Color(0xFFFB923C))
                StatCard(modifier = Modifier.weight(1f), label = "MEJOR RACHA", value = uiState.bestStreak.toString(), icon = Icons.Default.EmojiEvents, iconColor = Color(0xFFFFD700))
                StatCard(modifier = Modifier.weight(1f), label = "TOTAL", value = uiState.totalCompletions.toString(), icon = Icons.Default.CheckCircle, iconColor = primaryBlue)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Calendario Funcional
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = cardColor)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    val monthName = uiState.selectedMonth.month.getDisplayName(JavaTextStyle.FULL, Locale.getDefault())
                    val year = uiState.selectedMonth.year
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { viewModel.onMonthChange(uiState.selectedMonth.minusMonths(1)) }) {
                            Icon(Icons.Default.ChevronLeft, contentDescription = null, tint = secondaryTextColor)
                        }
                        Text(text = "${monthName.capitalize()} $year", color = textColor, fontWeight = FontWeight.Bold)
                        IconButton(onClick = { viewModel.onMonthChange(uiState.selectedMonth.plusMonths(1)) }) {
                            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = secondaryTextColor)
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(20.dp))
                    
                    // Días de la semana
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        listOf("D", "L", "M", "M", "J", "V", "S").forEach {
                            Text(text = it, color = secondaryTextColor, fontSize = 12.sp, modifier = Modifier.width(32.dp), textAlign = TextAlign.Center)
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    FunctionalCalendarGrid(
                        yearMonth = uiState.selectedMonth,
                        completions = uiState.completions
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Leyenda
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        LegendItem(color = primaryBlue, label = "Hecho")
                        Spacer(modifier = Modifier.width(16.dp))
                        LegendItem(color = Color.Transparent, label = "Hoy", isBordered = true, borderColor = primaryBlue)
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Botones de Acción
            Button(
                onClick = { onNavigateToEdit(habitId) },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = primaryBlue)
            ) {
                Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Editar Hábito", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = { viewModel.eliminarHabito() },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, redColor.copy(alpha = 0.5f)),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = redColor)
            ) {
                Icon(Icons.Default.Delete, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Eliminar Hábito", fontWeight = FontWeight.Bold)
            }
            
            Text(
                text = "Esto eliminará permanentemente todo el historial y los datos de la racha.",
                color = redColor.copy(alpha = 0.6f),
                fontSize = 11.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 12.dp, bottom = 24.dp)
            )
        }
    }
}

@Composable
fun FunctionalCalendarGrid(yearMonth: YearMonth, completions: List<String>) {
    val daysInMonth = yearMonth.lengthOfMonth()
    val firstDayOfWeek = yearMonth.atDay(1).dayOfWeek.value % 7 // 0 = Sunday, 1 = Monday...
    val today = LocalDate.now()

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        var dayCounter = 1
        for (row in 0..5) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                for (col in 0..6) {
                    if ((row == 0 && col < firstDayOfWeek) || dayCounter > daysInMonth) {
                        Spacer(modifier = Modifier.size(32.dp))
                    } else {
                        val currentDate = yearMonth.atDay(dayCounter)
                        val dateString = currentDate.toString()
                        val isCompleted = completions.contains(dateString)
                        val isToday = currentDate == today
                        
                        CalendarDayDetail(
                            text = dayCounter.toString(),
                            color = if (isCompleted) Color(0xFF4D8AFF) else Color.Transparent,
                            isBordered = isToday,
                            borderColor = if (isToday) Color(0xFF4D8AFF) else Color.Transparent
                        )
                        dayCounter++
                    }
                }
            }
            if (dayCounter > daysInMonth) break
        }
    }
}

@Composable
fun StatCard(modifier: Modifier, label: String, value: String, icon: ImageVector, iconColor: Color) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B).copy(alpha = 0.3f))
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = label, color = Color(0xFF94A3B8), fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(16.dp))
                Text(text = " $value", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun LegendItem(color: Color, label: String, isBordered: Boolean = false, borderColor: Color = Color.Transparent) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(color)
                .then(if (isBordered) Modifier.border(1.dp, borderColor, CircleShape) else Modifier)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = label, color = Color(0xFF94A3B8), fontSize = 12.sp)
    }
}

@Composable
fun CalendarDayDetail(text: String, color: Color, isBordered: Boolean = false, borderColor: Color = Color.Transparent) {
    Box(
        modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(color)
            .then(if (isBordered) Modifier.border(1.dp, borderColor, CircleShape) else Modifier),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text, 
            color = if (color == Color.Transparent && !isBordered) Color(0xFF334155) else Color.White, 
            fontSize = 12.sp
        )
    }
}
