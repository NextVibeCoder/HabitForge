package com.example.habitforge.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.Composable
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitDetailScreen(
    onNavigateBack: () -> Unit = {},
    onNavigateToEdit: () -> Unit = {}
) {
    val backgroundColor = Color(0xFF020617)
    val cardColor = Color(0xFF1E293B).copy(alpha = 0.5f)
    val primaryBlue = Color(0xFF4D8AFF)
    val textColor = Color.White
    val secondaryTextColor = Color(0xFF94A3B8)
    val redColor = Color(0xFFEF4444)

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
                    IconButton(onClick = onNavigateToEdit) {
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
                            Icon(Icons.AutoMirrored.Filled.DirectionsRun, contentDescription = null, tint = primaryBlue, modifier = Modifier.size(28.dp))
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "Carrera matutina", color = textColor, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            Surface(
                                color = Color(0xFF334155).copy(alpha = 0.5f),
                                shape = RoundedCornerShape(4.dp),
                                modifier = Modifier.padding(top = 4.dp)
                            ) {
                                Text(
                                    text = "DIARIO",
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                    style = TextStyle(color = secondaryTextColor, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Corre al menos 20 minutos cada mañana por el parque del barrio para mejorar tu resistencia cardiovascular.",
                        color = secondaryTextColor,
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Stats
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatCard(modifier = Modifier.weight(1f), label = "RACHA ACTUAL", value = "14", icon = Icons.Default.Whatshot, iconColor = Color(0xFFFB923C))
                StatCard(modifier = Modifier.weight(1f), label = "MEJOR RACHA", value = "30", icon = Icons.Default.EmojiEvents, iconColor = Color(0xFFFFD700))
                StatCard(modifier = Modifier.weight(1f), label = "TOTAL", value = "156", icon = Icons.Default.CheckCircle, iconColor = primaryBlue)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Calendario
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = cardColor)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.ChevronLeft, contentDescription = null, tint = secondaryTextColor)
                        Text(text = "Octubre 2023", color = textColor, fontWeight = FontWeight.Bold)
                        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = secondaryTextColor)
                    }
                    
                    Spacer(modifier = Modifier.height(20.dp))
                    
                    // Días de la semana
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        listOf("D", "L", "M", "M", "J", "V", "S").forEach {
                            Text(text = it, color = secondaryTextColor, fontSize = 12.sp, modifier = Modifier.width(32.dp), textAlign = TextAlign.Center)
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    // Ejemplo de cuadrícula de calendario
                    CalendarGridSample()

                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Leyenda
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        LegendItem(color = primaryBlue, label = "Hecho")
                        Spacer(modifier = Modifier.width(16.dp))
                        LegendItem(color = Color(0xFF451A1A), label = "Perdido", isBordered = true, borderColor = Color(0xFF7F1D1D))
                        Spacer(modifier = Modifier.width(16.dp))
                        LegendItem(color = Color.Transparent, label = "Hoy", isBordered = true, borderColor = primaryBlue)
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Botones de Acción
            Button(
                onClick = onNavigateToEdit,
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
                onClick = { /* Eliminar */ },
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
fun CalendarGridSample() {
    val primaryBlue = Color(0xFF4D8AFF)
    val missedColor = Color(0xFF451A1A)
    val missedBorder = Color(0xFF7F1D1D)
    
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Spacer(modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.size(32.dp))
            CalendarDayDetail(text = "1", color = primaryBlue)
            CalendarDayDetail(text = "2", color = primaryBlue)
            CalendarDayDetail(text = "3", color = primaryBlue)
            CalendarDayDetail(text = "4", color = missedColor, isBordered = true, borderColor = missedBorder)
            CalendarDayDetail(text = "5", color = primaryBlue)
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            CalendarDayDetail(text = "6", color = primaryBlue)
            CalendarDayDetail(text = "7", color = primaryBlue)
            CalendarDayDetail(text = "8", color = primaryBlue)
            CalendarDayDetail(text = "9", color = Color(0xFF334155).copy(alpha = 0.3f))
            CalendarDayDetail(text = "10", color = primaryBlue)
            CalendarDayDetail(text = "11", color = missedColor, isBordered = true, borderColor = missedBorder)
            CalendarDayDetail(text = "12", color = primaryBlue)
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            CalendarDayDetail(text = "13", color = primaryBlue)
            CalendarDayDetail(text = "14", color = Color.Transparent, isBordered = true, borderColor = primaryBlue)
            CalendarDayDetail(text = "15", color = Color.Transparent)
            CalendarDayDetail(text = "16", color = Color.Transparent)
            CalendarDayDetail(text = "17", color = Color.Transparent)
            CalendarDayDetail(text = "18", color = Color.Transparent)
            CalendarDayDetail(text = "19", color = Color.Transparent)
        }
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
        Text(text = text, color = if (color == Color.Transparent && !isBordered) Color(0xFF334155) else Color.White, fontSize = 12.sp)
    }
}
