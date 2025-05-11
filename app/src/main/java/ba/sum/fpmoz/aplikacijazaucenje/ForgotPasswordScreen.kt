package ba.sum.fpmoz.aplikacijazaucenje

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ForgotPasswordScreen(onBack: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var message by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Zaboravljena lozinka", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email adresa") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (email.isNotBlank()) {
                    FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                        .addOnCompleteListener { task ->
                            message = if (task.isSuccessful)
                                "📧 Poslan je email za reset lozinke."
                            else
                                "❌ Greška: ${task.exception?.localizedMessage}"
                        }
                } else {
                    message = "⚠️ Unesi email adresu."
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Pošalji")
        }

        Spacer(modifier = Modifier.height(16.dp))

        message?.let {
            Text(it)
        }

        Spacer(modifier = Modifier.height(32.dp))

        TextButton(onClick = onBack) {
            Text("⬅️ Natrag na prijavu")
        }
    }
}
