package ba.sum.fpmoz.aplikacijazaucenje

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

object Routes {
    const val LOGIN = "login"
    const val REGISTER = "register"
}

@Composable
fun AppNavigation(
    onGoogleLogin: () -> Unit = {}  // 🟢 dodan parametar za Google login
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.LOGIN) {

        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    // 🔜 TODO: Kad napraviš HomeScreen ovdje ide navigacija
                    // navController.navigate("home")
                },
                onGoToRegister = {
                    navController.navigate(Routes.REGISTER)
                },
                onGoogleLogin = {
                    onGoogleLogin() // 🟢 pokreni Google login iz MainActivity
                }
            )
        }

        composable(Routes.REGISTER) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.REGISTER) { inclusive = true }
                    }
                },
                onGoToLogin = {
                    navController.popBackStack()
                }
            )
        }
    }
}
