package com.example.habitforge.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.habitforge.ui.viewmodel.AppViewModelProvider
import com.example.habitforge.ui.viewmodel.LogViewModel
import com.example.habitforge.ui.model.Habito

// Colores consistentes con el diseño de la app
private val BackgroundDark = Color(0xFF020617)
private val CardBackground = Color(0xFF1E293B)
private val PrimaryBlue = Color(0xFF4D8AFF)
private val TextSecondary = Color(0xFF94A3B8)
private val BorderColor = Color.White.copy(alpha = 0.1f)
private val AccentOrange = Color(0xFFF59E0B)
private val AccentGreen = Color(0xFF10B981)
private val ErrorRed = Color(0xFFEF4444)

@Composable
fun LogScreen(
    onNavigateToHome: () -> Unit = {},
    onNavigateToSquad: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    viewModel: LogViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()

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
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = PrimaryBlue
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item { Spacer(modifier = Modifier.height(24.dp)) }

                item {
                    Text(
                        text = "INVITACIONES PENDIENTES",
                        color = TextSecondary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }

                if (uiState.invitations.isEmpty() && !uiState.isLoading) {
                    item {
                        EmptyInvitationsState()
                    }
                } else {
                    items(uiState.invitations) { habito ->
                        InvitationCard(
                            habito = habito,
                            onAccept = { viewModel.aceptarInvitacion(habito.id) },
                            onReject = { viewModel.rechazarInvitacion(habito.id) }
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }

                uiState.error?.let {
                    item {
                        Text(
                            text = it,
                            color = ErrorRed,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(vertical = 16.dp)
                        )
                    }
                }

                item { Spacer(modifier = Modifier.height(24.dp)) }
            }
        }
    }
}

@Composable
fun EmptyInvitationsState() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.MailOutline,
            contentDescription = null,
            tint = TextSecondary.copy(alpha = 0.3f),
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No tienes invitaciones pendientes por ahora.",
            color = TextSecondary,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun InvitationCard(
    habito: Habito,
    onAccept: () -> Unit,
    onReject: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground.copy(alpha = 0.5f)),
        border = BorderStroke(1.dp, BorderColor)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(BackgroundDark, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = habito.icon, fontSize = 24.sp)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = habito.nombre,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Invitación a hábito compartido",
                        color = TextSecondary,
                        fontSize = 12.sp
                    )
                }
            }
            
            if (habito.descripcion.isNotBlank()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = habito.descripcion,
                    color = TextSecondary,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = onAccept,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("Aceptar", fontWeight = FontWeight.Bold)
                }
                
                OutlinedButton(
                    onClick = onReject,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, ErrorRed.copy(alpha = 0.5f)),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = ErrorRed),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("Rechazar", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun LogTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "REGISTRO",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )
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
