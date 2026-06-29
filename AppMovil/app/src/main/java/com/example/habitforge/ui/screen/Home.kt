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
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.habitforge.ui.viewmodel.AppViewModelProvider
import com.example.habitforge.ui.viewmodel.HomeViewModel
import java.time.LocalDate

@Composable
fun Home(
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onNavigateToCreateHabit: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    onNavigateToSquad: () -> Unit = {},
    onNavigateToLog: () -> Unit = {},
    onNavigateToHabitDetail: (Long) -> Unit = {},
    onNavigateToHome: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsState()

    val backgroundColor = Color(0xFF020617)
    val primaryBlue = Color(0xFF4D8AFF)
    val textColor = Color.White
    val secondaryTextColor = Color(0xFF94A3B8)

    Scaffold(
        bottomBar = {
            HabitForgeBottomBar(
                selectedTab = "HOME",
                onNavigateToHome = onNavigateToHome,
                onNavigateToLog = onNavigateToLog,
                onNavigateToProfile = onNavigateToProfile,
                onNavigateToSquad = onNavigateToSquad
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToCreateHabit,
                containerColor = primaryBlue,
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier.size(64.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Añadir Hábito", modifier = Modifier.size(32.dp))
            }
        },
        containerColor = backgroundColor
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(backgroundColor)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            if (uiState.isLoading) {
                CircularProgressIndicator(color = primaryBlue)
            }
            uiState.error?.let {
                Text(text = it, color = Color.Red)
            }
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Buenos días, ${uiState.userName}",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = textColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                )
                Surface(
                    color = Color(0xFF1E293B).copy(alpha = 0.5f),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, primaryBlue.copy(alpha = 0.3f))
                ) {
                    Text(
                        text = "Nv. ${uiState.nivel} — ${uiState.rango}",
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        style = TextStyle(color = primaryBlue, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Progreso de XP", color = secondaryTextColor, fontSize = 13.sp)
                Text(text = "${uiState.xpActual} / ${uiState.xpTotal} XP", color = secondaryTextColor, fontSize = 13.sp)
            }
            Spacer(modifier = Modifier.height(10.dp))
            LinearProgressIndicator(
                progress = { uiState.xpActual.toFloat() / uiState.xpTotal.toFloat() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(CircleShape),
                color = primaryBlue,
                trackColor = Color(0xFF1E293B)
            )

            Spacer(modifier = Modifier.height(36.dp))

            WeeklyCalendar()

            Spacer(modifier = Modifier.height(36.dp))

            Text(
                text = "Protocolo de hoy",
                style = TextStyle(color = textColor, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(20.dp))

            uiState.habitos.forEach { habito ->
                HabitCard(
                    title = habito.nombre,
                    streak = 0,
                    icon = Icons.AutoMirrored.Filled.DirectionsRun,
                    isCompleted = false,
                    onToggleComplete = {
                        viewModel.completarHabito(habito.id, LocalDate.now().toString())
                    },
                    onClick = { onNavigateToHabitDetail(habito.id) }
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun WeeklyCalendar() {
    val days = listOf("LUN" to 12, "MAR" to 13, "MIE" to 14, "JUE" to 15, "VIE" to 16)
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        days.forEach { (day, num) ->
            val isSelected = num == 14
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .width(56.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(if (isSelected) Color(0xFF4D8AFF).copy(alpha = 0.1f) else Color(0xFF1E293B).copy(alpha = 0.3f))
                    .then(
                        if (isSelected) Modifier.border(1.dp, Color(0xFF4D8AFF), RoundedCornerShape(14.dp))
                        else Modifier
                    )
                    .padding(vertical = 12.dp)
            ) {
                Text(
                    text = day,
                    color = if (isSelected) Color(0xFF4D8AFF) else Color(0xFF64748B),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = num.toString(),
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun HabitCard(
    title: String,
    streak: Int,
    icon: ImageVector,
    isSquad: Boolean = false,
    isCompleted: Boolean = false,
    onToggleComplete: () -> Unit = {},
    onClick: () -> Unit = {}
) {
    val cardColor = Color(0xFF1E293B).copy(alpha = 0.5f)
    val primaryBlue = Color(0xFF4D8AFF)
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 14.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(Color(0xFF0F172A), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(icon, contentDescription = null, tint = primaryBlue, modifier = Modifier.size(22.dp))
                }
                
                Spacer(modifier = Modifier.width(16.dp))
                
                Column {
                    Text(
                        text = title, 
                        color = Color.White, 
                        fontWeight = FontWeight.Bold, 
                        fontSize = 16.sp
                    )
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 4.dp)) {
                        Icon(
                            imageVector = Icons.Default.Whatshot, 
                            contentDescription = null, 
                            tint = Color(0xFFFB923C), 
                            modifier = Modifier.size(16.dp)
                        )
                        Text(text = " $streak", color = Color(0xFFFB923C), fontSize = 14.sp, fontWeight = FontWeight.Medium)
                        
                        if (isSquad) {
                            Spacer(modifier = Modifier.width(12.dp))
                            Surface(
                                color = Color(0xFF4D8AFF).copy(alpha = 0.15f),
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Groups, 
                                        contentDescription = null, 
                                        tint = Color(0xFF818CF8), 
                                        modifier = Modifier.size(12.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(text = "Círculo", color = Color(0xFF818CF8), fontSize = 11.sp, fontWeight = FontWeight.Medium)
                                }
                            }
                        }
                    }
                }
            }
            
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

@Composable
fun HabitForgeBottomBar(
    selectedTab: String = "HOME",
    onNavigateToHome: () -> Unit = {},
    onNavigateToLog: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    onNavigateToSquad: () -> Unit = {}
) {
    val backgroundColor = Color(0xFF020617)
    val primaryBlue = Color(0xFF4D8AFF)
    val inactiveColor = Color(0xFF334155)

    NavigationBar(
        containerColor = backgroundColor,
        tonalElevation = 0.dp
    ) {
        NavigationBarItem(
            selected = selectedTab == "HOME",
            onClick = onNavigateToHome,
            icon = { Icon(Icons.Default.RocketLaunch, contentDescription = "Home") },
            label = { Text("HOME", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = primaryBlue,
                selectedTextColor = primaryBlue,
                unselectedIconColor = inactiveColor,
                unselectedTextColor = inactiveColor,
                indicatorColor = Color.Transparent
            )
        )
        NavigationBarItem(
            selected = selectedTab == "REGISTRO",
            onClick = onNavigateToLog,
            icon = { Icon(Icons.AutoMirrored.Filled.Assignment, contentDescription = "Registro") },
            label = { Text("REGISTRO", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = primaryBlue,
                selectedTextColor = primaryBlue,
                unselectedIconColor = inactiveColor,
                unselectedTextColor = inactiveColor,
                indicatorColor = Color.Transparent
            )
        )
        NavigationBarItem(
            selected = selectedTab == "CÍRCULO",
            onClick = onNavigateToSquad,
            icon = { Icon(Icons.Default.Groups, contentDescription = "Círculo") },
            label = { Text("CÍRCULO", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = primaryBlue,
                selectedTextColor = primaryBlue,
                unselectedIconColor = inactiveColor,
                unselectedTextColor = inactiveColor,
                indicatorColor = Color.Transparent
            )
        )
        NavigationBarItem(
            selected = selectedTab == "PERFIL",
            onClick = onNavigateToProfile,
            icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
            label = { Text("PERFIL", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = primaryBlue,
                selectedTextColor = primaryBlue,
                unselectedIconColor = inactiveColor,
                unselectedTextColor = inactiveColor,
                indicatorColor = Color.Transparent
            )
        )
    }
}
