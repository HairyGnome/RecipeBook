package hu.bme.aut.android.composerecipebook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import hu.bme.aut.android.composerecipebook.navigation.NavGraph
import hu.bme.aut.android.composerecipebook.ui.theme.RecipeBookTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecipeBookTheme {
                NavGraph()
            }
        }
    }
}