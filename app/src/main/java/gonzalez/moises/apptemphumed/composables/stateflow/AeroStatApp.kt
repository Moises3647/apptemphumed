package gonzalez.moises.apptemphumed.composables.stateflow

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import gonzalez.moises.apptemphumed.composables.stateflow.DashboardScreen
import gonzalez.moises.apptemphumed.composables.stateflow.HumidityScreen
import gonzalez.moises.apptemphumed.composables.stateflow.TemperatureScreen
import gonzalez.moises.apptemphumed.composables.stateflow.LoginScreen

object AeroStatColors {
    val PrimaryBlue  = Color(0xFF1A6EDB)
    val OnPrimary    = Color(0xFFFFFFFF)
    val Background   = Color(0xFFE8EFFE)
    val Surface      = Color(0xFFFFFFFF)
    val OnBackground = Color(0xFF0D1B3E)
    val OnSurface    = Color(0xFF0D1B3E)
    val Secondary    = Color(0xFF8A9BB8)
}

private val AeroStatColorScheme = lightColorScheme(
    primary       = AeroStatColors.PrimaryBlue,
    onPrimary     = AeroStatColors.OnPrimary,
    background    = AeroStatColors.Background,
    surface       = AeroStatColors.Surface,
    onBackground  = AeroStatColors.OnBackground,
    onSurface     = AeroStatColors.OnSurface,
    secondary     = AeroStatColors.Secondary,
)

@Composable
fun AeroStatTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = AeroStatColorScheme, content = content)
}

object Routes {
    const val LOGIN       = "login"
    const val DASHBOARD   = "dashboard"
    const val TEMPERATURE = "temperature"
    const val HUMIDITY    = "humidity"
}

@Composable
fun AeroStatApp() {
    val navController = rememberNavController()
    AeroStatTheme {
        NavHost(navController = navController, startDestination = Routes.LOGIN) {
            composable(Routes.LOGIN) {
                LoginScreen(
                    onLoginClick = { _, _ ->
                        navController.navigate(Routes.DASHBOARD) {
                            popUpTo(Routes.LOGIN) { inclusive = true }
                        }
                    }
                )
            }
            composable(Routes.DASHBOARD) {
                DashboardScreen(
                    onTemperatureClick = { navController.navigate(Routes.TEMPERATURE) },
                    onHumidityClick    = { navController.navigate(Routes.HUMIDITY) }
                )
            }
            composable(Routes.TEMPERATURE) {
                TemperatureScreen(onBack = { navController.popBackStack() },
                    onHumidityClick    = { navController.navigate(Routes.HUMIDITY) },
                    onDashboardClick = {navController.navigate(Routes.DASHBOARD)}
                )
            }
            composable(Routes.HUMIDITY) {
                HumidityScreen(onBack = { navController.popBackStack() },
                    onTemperatureClick = { navController.navigate(Routes.TEMPERATURE) },
                    onDashboardClick = { navController.navigate(Routes.DASHBOARD) }
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 375, heightDp = 820)
@Composable
fun AeroStatApp_prew() {
    AeroStatApp()
}