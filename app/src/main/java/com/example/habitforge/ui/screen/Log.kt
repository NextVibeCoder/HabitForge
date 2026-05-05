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

// Colores consistentes con el diseño de la app
private val BackgroundDark = Color(0xFF0A0E1A)
private val CardBackground = Color(0xFF161B2E)
private val PrimaryBlue = Color(0xFF4A89F3)
private val TextSecondary = Color(0xFF94A3B8)
private val BorderColor = Color.White.copy(alpha = 0.1f)
private val AccentOrange = Color(0xFFF59E0B)
private val AccentGreen = Color(0xFF10B981)

@Composable
fun LogScreen(
    onNavigateToHome: () -> Unit = {},
    onNavigateToSquad: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {}
) {
    Scaffold(
        containerColor = BackgroundDark,
        topBar = { LogTopBar() },
        bottomBar = {
            LogBottomNavigationBar(
                onHomeClick = onNavigateToHome,
                onLogClick = {}, // Ya estamos en REGISTRO
                onSquadClick = onNavigateToSquad,
                onBaseClick = onNavigateToProfile
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

            // Card Principal: Progreso de XP
            item {
                XpOverviewCard()
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            // Cuadrícula de Bonificaciones
            item {
                BonusGrid()
            }

            item { Spacer(modifier = Modifier.height(32.dp)) }

            // Sección: Historial de Niveles
            item {
                Text(
                    text = "HISTORIAL DE NIVELES",
                    color = TextSecondary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                LevelHistoryCard()
            }

            item { Spacer(modifier = Modifier.height(20.dp)) }
        }
    }
}

@Composable
fun LogTopBar() {
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
                .background(Color.Gray.copy(alpha = 0.3f))
        )

        Text(
            text = "HABITFORGE",
            color = PrimaryBlue,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )

        Icon(
            imageVector = Icons.Default.MilitaryTech,
            contentDescription = null,
            tint = PrimaryBlue,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun XpOverviewCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(CardBackground, RoundedCornerShape(24.dp))
            .border(1.dp, BorderColor, RoundedCornerShape(24.dp))
            .padding(24.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Lv. 12",
                color = Color.White,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "IRON SAGE",
                color = PrimaryBlue,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            
            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Progreso de XP", color = TextSecondary, fontSize = 12.sp)
                Text(text = "70%", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { 0.7f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = PrimaryBlue,
                trackColor = Color.White.copy(alpha = 0.1f)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Límite Diario", color = TextSecondary, fontSize = 12.sp)
                Text(text = "145 / 200 XP hoy", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun BonusGrid() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            BonusCard(
                label = "Base XP",
                value = "+10",
                icon = Icons.Default.AddCircleOutline,
                iconColor = PrimaryBlue,
                modifier = Modifier.weight(1f)
            )
            BonusCard(
                label = "Bono Racha",
                value = "+15",
                icon = Icons.Default.Whatshot,
                iconColor = AccentOrange,
                modifier = Modifier.weight(1f)
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            BonusCard(
                label = "Día Perfecto",
                value = "+15",
                icon = Icons.Default.StarOutline,
                iconColor = AccentGreen,
                modifier = Modifier.weight(1f)
            )
            BonusCard(
                label = "Sincro Compartida",
                value = "+5",
                icon = Icons.Default.Link,
                iconColor = PrimaryBlue,
                modifier = Modifier.weight(1f),
                badgeText = "DOM x2"
            )
        }
    }
}

@Composable
fun BonusCard(
    label: String,
    value: String,
    icon: ImageVector,
    iconColor: Color,
    modifier: Modifier = Modifier,
    badgeText: String? = null
) {
    Box(
        modifier = modifier
            .background(CardBackground, RoundedCornerShape(20.dp))
            .border(1.dp, BorderColor, RoundedCornerShape(20.dp))
            .padding(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(20.dp))
                if (badgeText != null) {
                    Surface(
                        color = AccentOrange,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = badgeText,
                            color = Color.Black,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = label, color = TextSecondary, fontSize = 12.sp)
            Text(
                text = value,
                color = if (label == "Bono Racha") AccentOrange else if (label == "Día Perfecto") AccentGreen else PrimaryBlue,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun LevelHistoryCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(CardBackground, RoundedCornerShape(20.dp))
            .border(1.dp, BorderColor, RoundedCornerShape(20.dp))
            .padding(20.dp)
    ) {
        Column {
            HistoryItem(
                level = "Lv. 11",
                title = "Steel Novice",
                time = "Alcanzado hace 3 días",
                isFirst = true
            )
            HistoryItem(
                level = "Lv. 10",
                title = "Bronze Walker",
                time = "Alcanzado hace 14 días",
                isLast = true
            )
        }
    }
}

@Composable
fun HistoryItem(
    level: String,
    title: String,
    time: String,
    isFirst: Boolean = false,
    isLast: Boolean = false
) {
    Row(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(1.dp, TextSecondary.copy(alpha = 0.3f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(text = level, color = TextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            if (!isLast) {
                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .weight(1f)
                        .background(TextSecondary.copy(alpha = 0.3f))
                )
            }
        }
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column(modifier = Modifier.padding(vertical = 4.dp)) {
            Text(text = title, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(text = time, color = TextSecondary, fontSize = 12.sp)
            if (!isLast) Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun LogBottomNavigationBar(
    onHomeClick: () -> Unit,
    onLogClick: () -> Unit,
    onSquadClick: () -> Unit,
    onBaseClick: () -> Unit
) {
    Surface(
        color = BackgroundDark,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            HorizontalDivider(color = BorderColor, thickness = 0.5.dp)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LogNavItem(icon = Icons.Default.RocketLaunch, label = "HOME", onClick = onHomeClick)
                LogNavItem(icon = Icons.AutoMirrored.Filled.Assignment, label = "REGISTRO", isSelected = true, onClick = onLogClick)
                LogNavItem(icon = Icons.Default.Groups, label = "CÍRCULO", onClick = onSquadClick)
                LogNavItem(icon = Icons.Default.Person, label = "PERFIL", onClick = onBaseClick)
            }
        }
    }
}

@Composable
fun LogNavItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    val color = if (isSelected) PrimaryBlue else TextSecondary
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(4.dp)
    ) {
        Icon(imageVector = icon, contentDescription = label, tint = color, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = label, color = color, fontSize = 10.sp, fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true)
@Composable
fun LogScreenPreview() {
    LogScreen()
}
