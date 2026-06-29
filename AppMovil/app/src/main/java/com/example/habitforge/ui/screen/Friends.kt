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

            // Sección de Invitaciones
            if (uiState.pendingInvitations.isNotEmpty()) {
                Spacer(modifier = Modifier.height(32.dp))
                Text("Invitaciones Pendientes", color = textColor, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(16.dp))
                uiState.pendingInvitations.forEach { habito ->
                    FriendInvitationCard(
                        habito = habito,
                        onAccept = { viewModel.aceptarInvitacion(habito.id) },
                        onReject = { viewModel.rechazarInvitacion(habito.id) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
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
                    SharedHabitCard(
                        title = habito.nombre,
                        status = if (habito.activo) "Sincronizado" else "Inactivo",
                        iconText = habito.icon,
                        streak = habito.rachaGrupalActual,
                        isCompleted = false, // Puedes manejar el estado real si el modelo lo permite
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
fun FriendInvitationCard(habito: Habito, onAccept: () -> Unit, onReject: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B).copy(alpha = 0.6f))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(44.dp).background(Color(0xFF0F172A), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(habito.icon, fontSize = 22.sp)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(habito.nombre, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text("Invitación recibida", color = Color(0xFF94A3B8), fontSize = 12.sp)
            }
            Row {
                IconButton(onClick = onAccept) {
                    Icon(Icons.Default.Check, contentDescription = "Aceptar", tint = Color(0xFF10B981))
                }
                IconButton(onClick = onReject) {
                    Icon(Icons.Default.Close, contentDescription = "Rechazar", tint = Color(0xFFEF4444))
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
    onToggleComplete: () -> Unit = {},
    onClick: () -> Unit = {}
) {
    val primaryBlue = Color(0xFF4D8AFF)
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B).copy(alpha = 0.4f))
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                Box(
                    modifier = Modifier.size(44.dp).background(Color(0xFF0F172A), RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(iconText, fontSize = 22.sp)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Sync, null, tint = statusColor, modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(status, color = statusColor, fontSize = 11.sp)
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
                            color = if (isCompleted) Color.Transparent else Color(0xFF334155),
                            shape = CircleShape
                        )
                        .clickable { onToggleComplete() },
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
    }
}
