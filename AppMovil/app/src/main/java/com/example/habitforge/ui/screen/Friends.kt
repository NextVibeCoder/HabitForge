package com.example.habitforge.ui.screen

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.habitforge.ui.viewmodel.AppViewModelProvider
import com.example.habitforge.ui.viewmodel.FriendsViewModel
import com.example.habitforge.ui.model.Habito
import com.example.habitforge.ui.model.dto.HabitoParticipanteResponse
import androidx.compose.ui.draw.alpha

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendsScreen(
    viewModel: FriendsViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onNavigateToHome: () -> Unit = {},
    onNavigateToLog: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    onNavigateToSquad: () -> Unit = {},
    onNavigateToHabitDetail: (Long) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val backgroundColor = Color(0xFF020617)
    val primaryBlue = Color(0xFF4D8AFF)
    val textColor = Color.White
    val secondaryTextColor = Color(0xFF94A3B8)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("HABITFORGE", color = primaryBlue, fontWeight = FontWeight.ExtraBold, letterSpacing = 2.sp) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = backgroundColor)
            )
        },
        bottomBar = {
            HabitForgeBottomBar(
                selectedTab = "CÍRCULO",
                onNavigateToHome = onNavigateToHome,
                onNavigateToLog = onNavigateToLog,
                onNavigateToProfile = onNavigateToProfile,
                onNavigateToSquad = onNavigateToSquad
            )
        },
        containerColor = backgroundColor
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            Text("Hábitos Compartidos", style = MaterialTheme.typography.headlineMedium.copy(color = textColor, fontWeight = FontWeight.Bold))
            Text("Sigue hábitos compartidos con tus amigos.", color = secondaryTextColor, fontSize = 14.sp)
            
            if (uiState.isLoading) {
                Box(modifier = Modifier.fillMaxWidth().padding(top = 32.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = primaryBlue)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Sección de Hábitos
            if (uiState.sharedHabits.isEmpty() && !uiState.isLoading) {
                Box(modifier = Modifier.fillMaxWidth().padding(vertical = 40.dp), contentAlignment = Alignment.Center) {
                    Text("No tienes hábitos compartidos aún.", color = secondaryTextColor)
                }
            } else {
                uiState.sharedHabits.forEach { habito ->
                    // El check principal se marca solo si TODOS cumplieron hoy
                    val isGroupCompleted = habito.participantes.isNotEmpty() && habito.participantes.all { it.completadoHoy }
                    
                    // Solo permitimos clics si el usuario actual NO ha completado su parte
                    val iHaveCompleted = habito.participantes.any { 
                        it.usuarioId == uiState.userId && it.completadoHoy 
                    }

                    SharedHabitCard(
                        title = habito.nombre,
                        status = if (habito.activo) "Sincronizado" else "Inactivo",
                        iconText = habito.icon,
                        streak = habito.rachaGrupalActual,
                        isCompleted = isGroupCompleted,
                        isEnabled = habito.esDiaObligatorio && !iHaveCompleted,
                        participantes = habito.participantes.filter { it.usuarioId != uiState.userId },
                        onToggleComplete = { viewModel.completarHabito(habito.id) },
                        onClick = { onNavigateToHabitDetail(habito.id) }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun SharedHabitCard(
    title: String,
    status: String,
    statusColor: Color = Color(0xFF10B981),
    iconText: String,
    streak: Int,
    isCompleted: Boolean = false,
    isEnabled: Boolean = true,
    participantes: List<HabitoParticipanteResponse> = emptyList(),
    onToggleComplete: () -> Unit = {},
    onClick: () -> Unit = {}
) {
    val primaryBlue = Color(0xFF4D8AFF)
    val cardColor = if (isEnabled) Color(0xFF1E293B).copy(alpha = 0.4f) else Color(0xFF1E293B).copy(alpha = 0.2f)
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    Box(
                        modifier = Modifier.size(44.dp).background(if (isEnabled) Color(0xFF0F172A) else Color(0xFF020617), RoundedCornerShape(10.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(iconText, fontSize = 22.sp, modifier = if (isEnabled) Modifier else Modifier.alpha(0.5f))
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(title, color = if (isEnabled) Color.White else Color.Gray, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Sync, null, tint = if (isEnabled) statusColor else Color.Gray, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(status, color = if (isEnabled) statusColor else Color.Gray, fontSize = 11.sp)
                        }
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Racha
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .background(Color(0xFF0F172A), RoundedCornerShape(20.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text("$streak", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(Icons.Default.Whatshot, null, tint = Color(0xFFFB923C), modifier = Modifier.size(14.dp))
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    // Círculo de cumplimiento (Estilo Home)
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(if (isCompleted) primaryBlue else Color.Transparent)
                            .border(
                                width = if (isCompleted) 0.dp else 2.dp,
                                color = if (isCompleted) Color.Transparent else if (isEnabled) Color(0xFF334155) else Color(0xFF334155).copy(alpha = 0.5f),
                                shape = CircleShape
                            )
                            .then(if (isEnabled && !isCompleted) Modifier.clickable { onToggleComplete() } else Modifier),
                        contentAlignment = Alignment.Center
                    ) {
                        if (isCompleted) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Completado",
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
            
            if (participantes.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Compañeros: ", color = Color.Gray, fontSize = 12.sp)
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        participantes.forEach { p ->
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .clip(CircleShape)
                                    .background(if (p.completadoHoy) Color(0xFF10B981) else Color(0xFF334155)),
                                contentAlignment = Alignment.Center
                            ) {
                                if (p.completadoHoy) {
                                    Icon(Icons.Default.Check, null, tint = Color.White, modifier = Modifier.size(12.dp))
                                } else {
                                    Text(
                                        text = p.username?.take(1)?.uppercase() ?: "?", 
                                        color = Color.White, 
                                        fontSize = 10.sp, 
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
