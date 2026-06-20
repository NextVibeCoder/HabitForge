package com.example.habitforge.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendsScreen(
    onNavigateToHome: () -> Unit = {},
    onNavigateToLog: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    onNavigateToSquad: () -> Unit = {}
) {
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
            Text("Círculo", style = MaterialTheme.typography.headlineMedium.copy(color = textColor, fontWeight = FontWeight.Bold))
            Text("Sigue misiones compartidas con tu equipo.", color = secondaryTextColor, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(32.dp))

            SquadHabitCard(
                title = "Leer 20 min",
                status = "Sincronizado",
                icon = Icons.AutoMirrored.Filled.MenuBook,
                members = listOf("AL" to 0.7f to 14, "S" to 0.5f to 12)
            )
            Spacer(modifier = Modifier.height(16.dp))
            SquadHabitCard(
                title = "Carrera matutina 3km",
                status = "Retrasado",
                statusColor = Color(0xFFFB923C),
                icon = Icons.AutoMirrored.Filled.DirectionsRun,
                members = listOf("AL" to 0.3f to 4, "M" to 0.8f to 8)
            )
        }
    }
}

@Composable
fun SquadHabitCard(
    title: String,
    status: String,
    statusColor: Color = Color(0xFF10B981),
    icon: ImageVector,
    members: List<Pair<Pair<String, Float>, Int>>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B).copy(alpha = 0.4f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(40.dp).background(Color(0xFF0F172A), RoundedCornerShape(8.dp)), contentAlignment = Alignment.Center) {
                    Icon(icon, null, tint = Color(0xFF4D8AFF), modifier = Modifier.size(20.dp))
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(title, color = Color.White, fontWeight = FontWeight.Bold)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Sync, null, tint = statusColor, modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(status, color = statusColor, fontSize = 11.sp)
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            members.forEach { (memberInfo, streak) ->
                val (name, progress) = memberInfo
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 4.dp)) {
                    Surface(modifier = Modifier.size(24.dp), shape = CircleShape, color = if (name == "AL") Color(0xFF4D8AFF) else Color(0xFF1E293B)) {
                        Box(contentAlignment = Alignment.Center) { Text(name, color = Color.White, fontSize = 8.sp, fontWeight = FontWeight.Bold) }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    LinearProgressIndicator(progress = { progress }, modifier = Modifier.weight(1f).height(6.dp).clip(CircleShape), color = Color(0xFF4D8AFF), trackColor = Color(0xFF0F172A))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("$streak", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Icon(Icons.Default.Whatshot, null, tint = Color(0xFFFB923C), modifier = Modifier.size(14.dp))
                }
            }
        }
    }
}
