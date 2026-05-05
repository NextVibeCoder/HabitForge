package com.example.habitforge.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val BackgroundDark = Color(0xFF0A0E1A)
private val CardBackground = Color(0xFF161B2E)
private val PrimaryBlue = Color(0xFF4A89F3)
private val TextSecondary = Color(0xFF94A3B8)
private val BorderColor = Color.White.copy(alpha = 0.1f)
private val AccentOrange = Color(0xFFF59E0B)

@Composable
fun ProfileScreen(
    onNavigateToHome: () -> Unit = {},
    onNavigateToLog: () -> Unit = {},
    onNavigateToSquad: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    Scaffold(
        containerColor = BackgroundDark,
        topBar = { ProfileTopBar(onNavigateToSettings) },
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item { Spacer(modifier = Modifier.height(20.dp)) }
            item { ProfileHeader() }
            item { Spacer(modifier = Modifier.height(24.dp)) }
            item { XpProgressBar(currentXp = 18400f, nextLevelXp = 20000f, currentLevel = 12) }
            item { Spacer(modifier = Modifier.height(32.dp)) }
            item { StatsGrid() }
            item { Spacer(modifier = Modifier.height(40.dp)) }
            item {
                Text(
                    text = "Registro de Trayectoria",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
            item {
                JourneyLogItem(
                    title = "Nivel 12 alcanzado",
                    description = "Desbloqueado el rango \"Iron Sage\" y acceso a misiones de escuadrón avanzadas.",
                    time = "Hoy",
                    isFirst = true,
                    isLast = false
                )
                JourneyLogItem(
                    title = "Nivel 11 alcanzado",
                    description = "Mantenida una racha de 14 días en hábitos principales.",
                    time = "Hace 2 semanas",
                    isFirst = false,
                    isLast = true
                )
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

@Composable
fun ProfileTopBar(onSettingsClick: () -> Unit) {
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
            Text(text = "AL", color = PrimaryBlue, fontSize = 12.sp, fontWeight = FontWeight.Bold)
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
fun ProfileHeader() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(contentAlignment = Alignment.BottomEnd) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(PrimaryBlue.copy(alpha = 0.3f))
                    .border(2.dp, PrimaryBlue.copy(alpha = 0.5f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "AL", color = PrimaryBlue, fontSize = 36.sp, fontWeight = FontWeight.Bold)
            }
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(CardBackground)
                    .border(2.dp, BackgroundDark, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.CheckCircle, null, tint = TextSecondary, modifier = Modifier.size(16.dp))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Alex", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 4.dp)) {
            Text(text = "Iron Sage", color = TextSecondary, fontSize = 16.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Box(Modifier.size(4.dp).clip(CircleShape).background(TextSecondary.copy(alpha = 0.5f)))
            Spacer(modifier = Modifier.width(8.dp))
            Surface(color = PrimaryBlue.copy(alpha = 0.2f), shape = RoundedCornerShape(12.dp)) {
                Text(
                    text = "Lvl 12", color = PrimaryBlue, fontSize = 12.sp,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun XpProgressBar(currentXp: Float, nextLevelXp: Float, currentLevel: Int) {
    val progress = currentXp / nextLevelXp
    Column(modifier = Modifier.fillMaxWidth()) {
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)),
            color = PrimaryBlue,
            trackColor = CardBackground
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "18.4k XP", color = TextSecondary, fontSize = 12.sp)
            Text(text = "20k XP para Nivel ${currentLevel + 1}", color = TextSecondary, fontSize = 12.sp)
        }
    }
}

@Composable
fun StatsGrid() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            StatCard("Activos", "8", Icons.Default.Whatshot, modifier = Modifier.weight(1f))
            StatCard("Mejor", "30", Icons.Default.EmojiEvents, modifier = Modifier.weight(1f))
        }
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            StatCard("XP Total", "18.4k", Icons.Default.Storage, modifier = Modifier.weight(1f))
            StatCard("Nivel", "12", Icons.Default.MilitaryTech, modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun StatCard(label: String, value: String, icon: ImageVector, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(CardBackground, RoundedCornerShape(20.dp))
            .border(1.dp, BorderColor, RoundedCornerShape(20.dp))
            .padding(20.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, null, tint = AccentOrange, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = label, color = TextSecondary, fontSize = 14.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = value, color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun JourneyLogItem(title: String, description: String, time: String, isFirst: Boolean, isLast: Boolean) {
    Row(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxHeight()) {
            Box(Modifier.width(2.dp).height(20.dp).background(if (isFirst) Color.Transparent else TextSecondary.copy(alpha = 0.2f)))
            Box(Modifier.size(12.dp).clip(CircleShape).background(if (isFirst) PrimaryBlue else TextSecondary.copy(alpha = 0.4f)).border(2.dp, if (isFirst) PrimaryBlue.copy(alpha = 0.3f) else Color.Transparent, CircleShape))
            Box(Modifier.width(2.dp).fillMaxHeight().weight(1f).background(if (isLast) Color.Transparent else TextSecondary.copy(alpha = 0.2f)))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Box(
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp).background(CardBackground, RoundedCornerShape(16.dp))
                .border(1.dp, BorderColor, RoundedCornerShape(16.dp)).padding(16.dp)
        ) {
            Column {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text(text = title, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text(text = time, color = TextSecondary, fontSize = 12.sp)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = description, color = TextSecondary, fontSize = 14.sp, lineHeight = 20.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}
