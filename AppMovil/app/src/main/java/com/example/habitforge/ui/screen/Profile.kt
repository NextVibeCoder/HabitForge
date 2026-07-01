package com.example.habitforge.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitforge.ui.model.dto.HistorialCumplimiento
import com.example.habitforge.ui.viewmodel.ProfileViewModel
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

private val BackgroundDark = Color(0xFF0A0E1A)
private val CardBackground = Color(0xFF161B2E)
private val PrimaryBlue = Color(0xFF4A89F3)
private val TextSecondary = Color(0xFF94A3B8)
private val BorderColor = Color.White.copy(alpha = 0.05f)
private val AccentOrange = Color(0xFFF59E0B)

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    onNavigateToHome: () -> Unit = {},
    onNavigateToLog: () -> Unit = {},
    onNavigateToSquad: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val profile = uiState.profile

    LaunchedEffect(Unit) {
        viewModel.cargarPerfil()
    }

    Scaffold(
        containerColor = BackgroundDark,
        topBar = { ProfileTopBar(profile?.username ?: "", onNavigateToSettings) },
        bottomBar = {
            HabitForgeBottomBar(
                selectedTab = "PERFIL",
                onNavigateToHome = onNavigateToHome,
                onNavigateToLog = onNavigateToLog,
                onNavigateToSquad = onNavigateToSquad,
                onNavigateToProfile = onNavigateToProfile
            )
        }
    ) { padding ->
        if (uiState.isLoading && profile == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = PrimaryBlue)
            }
        } else if (uiState.error != null && profile == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = uiState.error!!, color = Color.Red)
            }
        } else if (profile != null) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item { Spacer(modifier = Modifier.height(20.dp)) }
                item { ProfileHeader(profile.username, profile.email) }
                item { Spacer(modifier = Modifier.height(32.dp)) }
                
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        StatRow("Active Streak", profile.cantidadHabitosActivos.toString(), Icons.Default.Whatshot)
                        StatRow("Best Streak", profile.rachaMasLarga.toString(), Icons.Default.EmojiEvents)
                        StatRow("Joined", formatSimpleDate(profile.fechaRegistro), Icons.Default.CalendarMonth)
                    }
                }
                
                item { Spacer(modifier = Modifier.height(40.dp)) }
                
                item {
                    Text(
                        text = "COMPLETION HISTORY",
                        color = TextSecondary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                    )
                }
                
                items(profile.historial) { item ->
                    HistoryItem(item)
                    Spacer(modifier = Modifier.height(12.dp))
                }
                
                item { Spacer(modifier = Modifier.height(32.dp)) }

                item {
                    Button(
                        onClick = onLogout,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = "Cerrar Sesión",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "CERRAR SESIÓN",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    }
                }

                item { Spacer(modifier = Modifier.height(20.dp)) }
            }
        }
    }
}

private fun formatSimpleDate(dateString: String): String {
    if (dateString.isBlank()) return ""
    return try {
        val date = if (dateString.contains("T")) {
            OffsetDateTime.parse(dateString).toLocalDate()
        } else {
            LocalDate.parse(dateString)
        }
        val formatterOutput = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        date.format(formatterOutput)
    } catch (e: Exception) {
        try {
            val simpleDate = dateString.split("T")[0]
            val date = LocalDate.parse(simpleDate)
            val formatterOutput = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            date.format(formatterOutput)
        } catch (e2: Exception) {
            dateString
        }
    }
}

@Composable
fun ProfileTopBar(username: String, onSettingsClick: () -> Unit) {
    val initials = if (username.length >= 2) username.take(2).uppercase() else "HF"
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(PrimaryBlue.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = initials, color = PrimaryBlue, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
        Text(
            text = "HABITFORGE",
            color = PrimaryBlue,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.MilitaryTech, null, tint = PrimaryBlue, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Icon(
                Icons.Default.Settings, "Ajustes",
                tint = TextSecondary,
                modifier = Modifier.size(24.dp).clickable { onSettingsClick() }
            )
        }
    }
}

@Composable
fun ProfileHeader(name: String, email: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = name, color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Text(text = email, color = TextSecondary, fontSize = 14.sp, modifier = Modifier.padding(top = 4.dp))
    }
}

@Composable
fun StatRow(label: String, value: String, icon: ImageVector) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(CardBackground, RoundedCornerShape(16.dp))
            .border(1.dp, BorderColor, RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, null, tint = AccentOrange, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = label, color = TextSecondary, fontSize = 16.sp, fontWeight = FontWeight.Medium)
        }
        Text(text = value, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun HistoryItem(history: HistorialCumplimiento) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(CardBackground, RoundedCornerShape(16.dp))
            .border(1.dp, BorderColor, RoundedCornerShape(16.dp))
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = history.nombreHabito, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = formatSimpleDate(history.fecha), color = TextSecondary, fontSize = 14.sp)
        }
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = PrimaryBlue,
            modifier = Modifier.size(24.dp)
        )
    }
}
