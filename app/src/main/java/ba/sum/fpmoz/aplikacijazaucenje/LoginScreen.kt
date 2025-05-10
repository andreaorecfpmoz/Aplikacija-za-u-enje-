package ba.sum.fpmoz.aplikacijazaucenje

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onGoToRegister: () -> Unit,
    onGoogleLogin: () -> Unit // Dodano za Google login
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Lozinka") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (email.isBlank() || password.isBlank()) {
                    errorMessage = "Unesite email i lozinku."
                } else {
                    FirebaseAuthService.loginUser(email, password) { success, error ->
                        if (success) {
                            onLoginSuccess()
                        } else {
                            errorMessage = error ?: "Greška pri prijavi"
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Prijavi se")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onGoogleLogin,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Prijava putem Googlea")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Nemaš račun? Registriraj se",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable { onGoToRegister() }
        )

        errorMessage?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }
    }
}
