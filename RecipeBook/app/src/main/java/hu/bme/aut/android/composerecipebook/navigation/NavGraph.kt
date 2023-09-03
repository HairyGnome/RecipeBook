package hu.bme.aut.android.composerecipebook.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import hu.bme.aut.android.composerecipebook.feature.ListScreen
import hu.bme.aut.android.composerecipebook.feature.camera.CameraScreen
import hu.bme.aut.android.composerecipebook.feature.details.DetailsScreen

@androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screen.List.route
    ) {
        composable(
            route = Screen.List.route,
            arguments = listOf(
                navArgument("scannedRecipe") {
                    defaultValue = ""
                    type = NavType.StringType
                }
            )
        ) {
            ListScreen(
                onItemOpenButtonClick = { navController.navigate(Screen.Details.passId(it)) },
                onScannerButtonClick = { navController.navigate(Screen.Camera.route) }
            )
        }
        composable(
            route = Screen.Details.route,
            arguments = listOf(
                navArgument("id") {
                    defaultValue = 0
                    type = NavType.IntType
                }
            )
        ) {
            DetailsScreen(onNavigateBack = { navController.navigate(Screen.List.route) })
        }
        composable(
            route = Screen.Camera.route
        ) {
            CameraScreen(
                onSuccess = { recipe ->
                    navController.navigate(Screen.List.passRecipe(recipe))
                },
                onNavigateBack = { navController.navigate(Screen.List.route) }
            )
        }
    }
}