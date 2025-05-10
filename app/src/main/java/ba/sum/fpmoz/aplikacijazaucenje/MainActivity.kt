package ba.sum.fpmoz.aplikacijazaucenje

import android.os.Bundle
import android.content.Intent
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import ba.sum.fpmoz.aplikacijazaucenje.ui.theme.AplikacijaZaUcenjeTheme

class MainActivity : ComponentActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ Inicijalizacija Firebase
        FirebaseApp.initializeApp(this)
        auth = FirebaseAuth.getInstance()

        // ✅ Google Sign-In konfiguracija
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))  // moraš imati ovo u strings.xml
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // ✅ Launcher za rezultat Google prijave
        val googleSignInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            if (task.isSuccessful) {
                val account: GoogleSignInAccount? = task.result
                val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
                auth.signInWithCredential(credential).addOnCompleteListener { signInTask ->
                    if (signInTask.isSuccessful) {
                        Log.d("GOOGLE_LOGIN", "✅ Google prijava uspješna: ${auth.currentUser?.email}")
                    } else {
                        Log.e("GOOGLE_LOGIN", "❌ Neuspješna prijava", signInTask.exception)
                    }
                }
            } else {
                Log.e("GOOGLE_LOGIN", "❌ Google Sign-In nije uspio", task.exception)
            }
        }

        // ✅ UI
        enableEdgeToEdge()
        setContent {
            AplikacijaZaUcenjeTheme {
                AppNavigation(
                    onGoogleLogin = {
                        val signInIntent = googleSignInClient.signInIntent
                        googleSignInLauncher.launch(signInIntent)
                    }
                )
            }
        }
    }
}
