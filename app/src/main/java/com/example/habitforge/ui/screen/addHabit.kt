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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Colores consistentes con el diseño oscuro
private val BackgroundDark = Color(0xFF0A0E1A)
private val CardBackground = Color(0xFF161B2E)
private val PrimaryBlue = Color(0xFF4A89F3)
private val SecondaryBlue = Color(0xFF818CF8) // Para botones secundarios o acentos
private val TextSecondary = Color(0xFF94A3B8)
private val BorderColor = Color.White.copy(alpha = 0.1f)

@Composable
fun AddHabitScreen(
    onNavigateBack: () -> Unit,
    onInitializeMission: () -> Unit
) {
    // Estados (En una app real vendrían del ViewModel)
    var habitName by remember { mutableStateOf("Carrera Matutina") }
    var habitDescription by remember { mutableStateOf("Carrera de 5km por senderos enfocada en el ritmo y control de la respiración. Zona de frecuencia cardíaca objetivo 2.") }
    var selectedCadence by remember { mutableStateOf("Diario") }
    var habitType by remember { mutableStateOf("Compartido") }
    var searchQuery by remember { mutableStateOf("") }
    
    val selectedFriends = listOf("Alex", "Sarah")
    val friendList = listOf("Mike Johnson")

    Scaffold(
        containerColor = BackgroundDark,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(CardBackground)
                        .clickable { onNavigateBack() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Preparación de Misión",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        bottomBar = {
            Box(modifier = Modifier.padding(24.dp)) {
                Button(
                    onClick = onInitializeMission,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Iniciar Misión",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Nombre del Hábito
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(CardBackground, RoundedCornerShape(12.dp))
                        .padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        BasicTextField(
                            value = habitName,
                            onValueChange = { habitName = it },
                            textStyle = MaterialTheme.typography.headlineSmall.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.weight(1f)
                        )
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(SecondaryBlue)
                        )
                    }
                }
            }

            // Descripción
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(CardBackground, RoundedCornerShape(12.dp))
                        .padding(20.dp)
                ) {
                    BasicTextField(
                        value = habitDescription,
                        onValueChange = { habitDescription = it },
                        textStyle = MaterialTheme.typography.bodyMedium.copy(
                            color = TextSecondary,
                            lineHeight = 22.sp
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // Selector de Emoji
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(70.dp)
                            .background(CardBackground, RoundedCornerShape(16.dp))
                            .border(1.dp, BorderColor, RoundedCornerShape(16.dp))
                            .clickable { /* Acción para elegir emoji */ },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "🎯", fontSize = 32.sp)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Toca para elegir emoji",
                        color = TextSecondary,
                        fontSize = 12.sp
                    )
                }
            }

            // Cadencia
            item {
                SectionHeader("CADENCIA")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("Diario", "Semanal", "Personalizado").forEach { cadence ->
                        SelectableButton(
                            text = cadence,
                            isSelected = selectedCadence == cadence,
                            onClick = { selectedCadence = cadence },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // Tipo de Hábito
            item {
                SectionHeader("TIPO DE HÁBITO")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("Individual", "Compartido").forEach { type ->
                        SelectableButton(
                            text = type,
                            isSelected = habitType == type,
                            onClick = { habitType = type },
                            modifier = Modifier.weight(1f),
                            large = true
                        )
                    }
                }
            }

            // Invitar Amigos
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(CardBackground, RoundedCornerShape(16.dp))
                        .padding(16.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        Text(
                            text = "INVITAR AMIGOS",
                            color = TextSecondary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        
                        // Barra de búsqueda
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("Buscar amigos...", color = TextSecondary) },
                            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = TextSecondary) },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = BorderColor,
                                unfocusedBorderColor = BorderColor,
                                focusedContainerColor = BackgroundDark.copy(alpha = 0.5f),
                                unfocusedContainerColor = BackgroundDark.copy(alpha = 0.5f),
                                focusedTextColor = Color.White
                            ),
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true
                        )

                        // Chips de amigos seleccionados
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            selectedFriends.forEach { friend ->
                                FriendChip(name = friend)
                            }
                        }

                        // Lista de amigos (Mike Johnson)
                        friendList.forEach { friend ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(CircleShape)
                                            .background(SecondaryBlue.copy(alpha = 0.2f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(text = "MJ", color = SecondaryBlue, fontSize = 14.sp)
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(text = friend, color = Color.White, fontSize = 16.sp)
                                }
                                Button(
                                    onClick = { /* Invitar */ },
                                    colors = ButtonDefaults.buttonColors(containerColor = BackgroundDark),
                                    modifier = Modifier.height(36.dp),
                                    contentPadding = PaddingValues(horizontal = 16.dp),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Text(text = "Invitar", color = SecondaryBlue, fontSize = 14.sp)
                                }
                            }
                        }
                    }
                }
            }
            
            item { Spacer(modifier = Modifier.height(20.dp)) }
        }
    }
}

@Composable
fun SectionHeader(text: String) {
    Text(
        text = text,
        color = TextSecondary,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
fun SelectableButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    large: Boolean = false
) {
    val backgroundColor = if (isSelected) SecondaryBlue.copy(alpha = 0.6f) else CardBackground
    val contentColor = if (isSelected) Color.White else TextSecondary
    val height = if (large) 48.dp else 40.dp
    
    Box(
        modifier = modifier
            .height(height)
            .clip(RoundedCornerShape(if (large) 14.dp else 12.dp))
            .background(backgroundColor)
            .border(
                1.dp, 
                if (isSelected) SecondaryBlue else BorderColor, 
                RoundedCornerShape(if (large) 14.dp else 12.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = contentColor,
            fontSize = 14.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
fun FriendChip(name: String) {
    Surface(
        color = SecondaryBlue.copy(alpha = 0.6f),
        shape = RoundedCornerShape(12.dp),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(Color.Red.copy(alpha = 0.5f)) // Avatar genérico
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = name, color = Color.White, fontSize = 14.sp)
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.7f),
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
fun BasicTextField(
    value: String,
    onValueChange: (String) -> Unit,
    textStyle: androidx.compose.ui.text.TextStyle,
    modifier: Modifier = Modifier
) {
    androidx.compose.foundation.text.BasicTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = textStyle,
        modifier = modifier,
        cursorBrush = androidx.compose.ui.graphics.SolidColor(Color.White)
    )
}

@Preview
@Composable
fun AddHabitScreenPreview() {
    AddHabitScreen(onNavigateBack = {}, onInitializeMission = {})
}
