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
    onNavigateToProfile: () -> Unit = {},
    onNavigateToSquad: () -> Unit = {}
) {
    val backgroundColor = Color(0xFF020617)
    val primaryBlue = Color(0xFF4D8AFF)
    val textColor = Color.White
    val secondaryTextColor = Color(0xFF94A3B8)
    val cardColor = Color(0xFF1E293B).copy(alpha = 0.5f)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(
                            "HABITFORGE",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = primaryBlue,
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 2.sp
                            )
                        )
                    }
                },
                navigationIcon = {
                    Surface(
                        modifier = Modifier.padding(start = 16.dp).size(32.dp),
                        shape = CircleShape,
                        color = primaryBlue.copy(alpha = 0.2f)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text("AL", color = primaryBlue, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.MilitaryTech, contentDescription = null, tint = secondaryTextColor)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = backgroundColor)
            )
        },
        bottomBar = {
            HabitForgeBottomBar(
                selectedTab = "SQUAD",
                onNavigateToHome = onNavigateToHome,
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
            Text(
                "Círculo",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = textColor,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                "Sigue misiones compartidas con tu equipo.",
                color = secondaryTextColor,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            // COMUNICACIONES PENDIENTES
            Text(
                "COMUNICACIONES PENDIENTES",
                color = secondaryTextColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = cardColor)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        modifier = Modifier.size(40.dp),
                        shape = CircleShape,
                        color = primaryBlue.copy(alpha = 0.1f)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text("J", color = primaryBlue, fontWeight = FontWeight.Bold)
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Jordan", color = textColor, fontWeight = FontWeight.Bold)
                        Text("Quiere unirse al círculo", color = secondaryTextColor, fontSize = 12.sp)
                    }
                    Row {
                        IconButton(
                            onClick = {},
                            modifier = Modifier.size(32.dp).background(Color(0xFF451A1A), CircleShape)
                        ) {
                            Icon(Icons.Default.Close, contentDescription = null, tint = Color(0xFFEF4444), modifier = Modifier.size(16.dp))
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(
                            onClick = {},
                            modifier = Modifier.size(32.dp).background(Color(0xFF064E3B), CircleShape)
                        ) {
                            Icon(Icons.Default.Check, contentDescription = null, tint = Color(0xFF10B981), modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // OPERACIONES CONJUNTAS
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "OPERACIONES CONJUNTAS",
                    color = secondaryTextColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Button(
                    onClick = {},
                    modifier = Modifier.height(32.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    border = BorderStroke(1.dp, primaryBlue),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(14.dp), tint = textColor)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Invitar", color = textColor, fontSize = 12.sp)
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))

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

            Spacer(modifier = Modifier.height(32.dp))

            // PERSONAL ACTIVO
            Text(
                "PERSONAL ACTIVO",
                color = secondaryTextColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                repeat(4) { i ->
                    val initial = listOf("S", "M", "A", "L")[i]
                    Surface(
                        modifier = Modifier.size(48.dp),
                        shape = CircleShape,
                        color = Color(0xFF1E293B),
                        border = if (i == 0) BorderStroke(2.dp, primaryBlue) else null
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(initial, color = secondaryTextColor, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
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
                Box(
                    modifier = Modifier.size(40.dp).background(Color(0xFF0F172A), RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(icon, contentDescription = null, tint = Color(0xFF4D8AFF), modifier = Modifier.size(20.dp))
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(title, color = Color.White, fontWeight = FontWeight.Bold)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Sync, contentDescription = null, tint = statusColor, modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(status, color = statusColor, fontSize = 11.sp)
                    }
                }
                Icon(Icons.Default.MoreHoriz, contentDescription = null, tint = Color(0xFF64748B))
            }
            
            Spacer(modifier = Modifier.height(20.dp))

            members.forEach { (memberInfo, streak) ->
                val (name, progress) = memberInfo
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 4.dp)) {
                    Surface(
                        modifier = Modifier.size(24.dp),
                        shape = CircleShape,
                        color = if (name == "AL") Color(0xFF4D8AFF) else Color(0xFF1E293B)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(name, color = Color.White, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier.weight(1f).height(6.dp).clip(CircleShape),
                        color = Color(0xFF4D8AFF),
                        trackColor = Color(0xFF0F172A)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("$streak", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Icon(Icons.Default.Whatshot, contentDescription = null, tint = Color(0xFFFB923C), modifier = Modifier.size(14.dp))
                }
            }
        }
    }
}
