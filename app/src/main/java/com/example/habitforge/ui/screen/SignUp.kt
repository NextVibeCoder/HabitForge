package com.example.habitforge.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Colores personalizados basados en el diseño
private val BackgroundDark = Color(0xFF0A0E1A)
private val CardBackground = Color(0xFF161B2E)
private val PrimaryBlue = Color(0xFF4A89F3)
private val ErrorRed = Color(0xFFE57373)
private val TextSecondary = Color(0xFF94A3B8)

@Composable
fun SignUpScreen(
    onNavigateToSignIn: () -> Unit,
    onSignUpSuccess: () -> Unit
) {
    // Estados para los campos (en un escenario real, esto iría en un ViewModel)
    var email by remember { mutableStateOf("pilot@command.com") }
    var securityCode by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("password") }
    var showSecurityCode by remember { mutableStateOf(false) }
    var showPassword by remember { mutableStateOf(false) }

    // Simulación de error de validación como se ve en la imagen
    val hasPasswordError = true

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF1A237E), BackgroundDark),
                    startY = 0f,
                    endY = 1000f
                )
            )
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        // Icono superior (Medalla/Trofeo)
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(CardBackground, RoundedCornerShape(20.dp))
                .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Lock, // Sustituir por icono de medalla si está disponible
                contentDescription = null,
                tint = PrimaryBlue,
                modifier = Modifier.size(32.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Crear Cuenta",
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Crea hábitos. Sube de nivel.",
            color = TextSecondary,
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Campo de Email
        CustomTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = "Correo electrónico",
            leadingIcon = Icons.Default.Email
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de Código de Seguridad
        CustomTextField(
            value = securityCode,
            onValueChange = { securityCode = it },
            placeholder = "Código de Seguridad",
            leadingIcon = Icons.Default.Lock,
            isPassword = true,
            isPasswordVisible = showSecurityCode,
            onVisibilityToggle = { showSecurityCode = !showSecurityCode }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de Contraseña (con error visual)
        CustomTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = "Contraseña",
            leadingIcon = Icons.Default.Lock,
            isPassword = true,
            isPasswordVisible = showPassword,
            onVisibilityToggle = { showPassword = !showPassword },
            isError = hasPasswordError
        )

        if (hasPasswordError) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Lock, // Sustituir por icono de advertencia
                    contentDescription = null,
                    tint = ErrorRed,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Las contraseñas no coinciden",
                    color = ErrorRed,
                    fontSize = 12.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Botón principal
        Button(
            onClick = onSignUpSuccess,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "Crear Cuenta",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Footer: Navegación a Login
        TextButton(onClick = onNavigateToSignIn) {
            Row {
                Text(text = "¿Ya tienes una cuenta? ", color = TextSecondary)
                Text(text = "Inicia sesión", color = PrimaryBlue, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    leadingIcon: ImageVector,
    isPassword: Boolean = false,
    isPasswordVisible: Boolean = false,
    onVisibilityToggle: (() -> Unit)? = null,
    isError: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = placeholder, color = TextSecondary) },
        leadingIcon = { Icon(imageVector = leadingIcon, contentDescription = null, tint = if (isError) ErrorRed else TextSecondary) },
        trailingIcon = {
            if (isPassword && onVisibilityToggle != null) {
                IconButton(onClick = onVisibilityToggle) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = null,
                        tint = TextSecondary
                    )
                }
            }
        },
        visualTransformation = if (isPassword && !isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = if (isPassword) KeyboardType.Password else KeyboardType.Email),
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = CardBackground,
            unfocusedContainerColor = CardBackground,
            focusedBorderColor = if (isError) ErrorRed else PrimaryBlue,
            unfocusedBorderColor = if (isError) ErrorRed else Color.White.copy(alpha = 0.1f),
            cursorColor = PrimaryBlue,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
        ),
        isError = isError
    )
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen(onNavigateToSignIn = {}, onSignUpSuccess = {})
}
