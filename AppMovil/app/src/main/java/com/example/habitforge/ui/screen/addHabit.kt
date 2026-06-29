package com.example.habitforge.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.habitforge.ui.model.enums.DiaSemana
import com.example.habitforge.ui.model.enums.FrecuenciaTipo
import com.example.habitforge.ui.viewmodel.AddHabitViewModel
import com.example.habitforge.ui.viewmodel.AppViewModelProvider


private val BackgroundDark = Color(0xFF020617)
private val CardBackground = Color(0xFF1E293B)
private val PrimaryBlue = Color(0xFF4D8AFF)
private val SecondaryBlue = Color(0xFF818CF8)
private val TextSecondary = Color(0xFF94A3B8)
private val BorderColor = Color.White.copy(alpha = 0.1f)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddHabitScreen(
    habitId: Long? = null,
    onNavigateBack: () -> Unit,
    onInitializeMission: () -> Unit,
    viewModel: AddHabitViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(habitId) {
        if (habitId != null) {
            viewModel.setEditMode(habitId)
        }
    }

    LaunchedEffect(uiState.habitCreated) {
        if (uiState.habitCreated) {
            onInitializeMission()
            viewModel.resetHabitCreated()
        }
    }

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
                    text = "Preparación del Habito",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        bottomBar = {
            Box(modifier = Modifier.padding(24.dp)) {
                Button(
                    onClick = { viewModel.guardarHabito() },
                    enabled = !uiState.isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (uiState.isLoading) {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier.size(20.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (uiState.isEditMode) "Actualizar Hábito" else "Iniciar Hábito",
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
            uiState.error?.let {
                item {
                    Text(
                        text = it,
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Red.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                            .padding(12.dp)
                    )
                }
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(CardBackground.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                        .padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        BasicTextField(
                            value = uiState.nombre,
                            onValueChange = { viewModel.onNombreChange(it) },
                            textStyle = TextStyle(
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            ),
                            cursorBrush = SolidColor(Color.White),
                            modifier = Modifier.weight(1f),
                            decorationBox = { innerTextField ->
                                if (uiState.nombre.isEmpty()) {
                                    Text("Nombre del hábito", color = TextSecondary, fontSize = 20.sp)
                                }
                                innerTextField()
                            }
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

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(CardBackground.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                        .padding(20.dp)
                ) {
                    BasicTextField(
                        value = uiState.descripcion,
                        onValueChange = { viewModel.onDescripcionChange(it) },
                        textStyle = TextStyle(
                            color = TextSecondary,
                            fontSize = 14.sp,
                            lineHeight = 22.sp
                        ),
                        cursorBrush = SolidColor(Color.White),
                        modifier = Modifier.fillMaxWidth(),
                        decorationBox = { innerTextField ->
                            if (uiState.descripcion.isEmpty()) {
                                Text("Descripción del hábito", color = TextSecondary.copy(alpha = 0.5f), fontSize = 14.sp)
                            }
                            innerTextField()
                        }
                    )
                }
            }


            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(70.dp)
                            .background(CardBackground.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                            .border(1.dp, BorderColor, RoundedCornerShape(16.dp))
                            .clickable {  },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = uiState.icon, fontSize = 32.sp)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Toca para elegir emoji",
                        color = TextSecondary,
                        fontSize = 12.sp
                    )
                }
            }

            item {
                SectionHeader("Frecuencia")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val cadences = listOf(
                        "Diario" to FrecuenciaTipo.DIARIA,
                        "Semanal" to FrecuenciaTipo.SEMANAL
                    )
                    cadences.forEach { (text, type) ->
                        SelectableButton(
                            text = text,
                            isSelected = uiState.frecuencia == type,
                            onClick = { viewModel.onFrecuenciaChange(type) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            if (uiState.frecuencia == FrecuenciaTipo.SEMANAL) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(CardBackground.copy(alpha = 0.3f), RoundedCornerShape(16.dp))
                            .padding(16.dp)
                    ) {
                        Column {
                            SectionHeader("DÍAS DE LA SEMANA")
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                val days = listOf(
                                    "L" to DiaSemana.LUNES,
                                    "Ma" to DiaSemana.MARTES,
                                    "M" to DiaSemana.MIERCOLES,
                                    "J" to DiaSemana.JUEVES,
                                    "V" to DiaSemana.VIERNES,
                                    "S" to DiaSemana.SABADO,
                                    "D" to DiaSemana.DOMINGO
                                )
                                days.forEach { (text, day) ->
                                    DayToggleButton(
                                        text = text,
                                        isSelected = uiState.diasSemana.contains(day),
                                        onClick = { viewModel.toggleDiaSemana(day) },
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            item {
                SectionHeader("TIPO DE HÁBITO")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val types = listOf(
                        "Individual" to false,
                        "Compartido" to true
                    )
                    types.forEach { (text, isCompartido) ->
                        SelectableButton(
                            text = text,
                            isSelected = uiState.esCompartido == isCompartido,
                            onClick = { viewModel.onTipoChange(isCompartido) },
                            modifier = Modifier.weight(1f),
                            large = true
                        )
                    }
                }
            }


            if (uiState.esCompartido) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(CardBackground.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                            .padding(16.dp)
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                            Text(
                                text = "INVITAR AMIGOS",
                                color = TextSecondary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )

                            OutlinedTextField(
                                value = uiState.searchQuery,
                                onValueChange = { viewModel.onSearchQueryChange(it) },
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = { Text("Email del amigo...", color = TextSecondary) },
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


                            if (uiState.searchQuery.isNotBlank()) {
                                Button(
                                    onClick = { 
                                        viewModel.addAmigo(uiState.searchQuery)
                                        viewModel.onSearchQueryChange("")
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue.copy(alpha = 0.2f)),
                                    contentPadding = PaddingValues(0.dp)
                                ) {
                                    Text("Agregar a la lista", color = PrimaryBlue)
                                }
                            }


                            FlowRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                uiState.amigosInvitados.forEach { friend ->
                                    FriendChip(name = friend, onRemove = { viewModel.removeAmigo(friend) })
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
    val backgroundColor = if (isSelected) SecondaryBlue.copy(alpha = 0.3f) else CardBackground.copy(alpha = 0.5f)
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
fun DayToggleButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isSelected) SecondaryBlue.copy(alpha = 0.4f) else Color.Transparent
    val contentColor = if (isSelected) Color.White else TextSecondary
    val borderColor = if (isSelected) SecondaryBlue else BorderColor
    
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(CircleShape)
            .background(backgroundColor)
            .border(1.dp, borderColor, CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = contentColor,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
fun FriendChip(name: String, onRemove: () -> Unit) {
    Surface(
        color = SecondaryBlue.copy(alpha = 0.2f),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, SecondaryBlue.copy(alpha = 0.5f))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(SecondaryBlue.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = name.take(1).uppercase(), color = Color.White, fontSize = 10.sp)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = name, color = Color.White, fontSize = 14.sp)
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Eliminar",
                tint = Color.White.copy(alpha = 0.7f),
                modifier = Modifier
                    .size(16.dp)
                    .clickable { onRemove() }
            )
        }
    }
}

@Preview
@Composable
fun AddHabitScreenPreview() {
    AddHabitScreen(onNavigateBack = {}, onInitializeMission = {})
}
