package hu.bme.aut.android.composerecipebook.navigation

sealed class Screen(val route: String) {
    object List: Screen(route = "list/?scannedRecipe={scannedRecipe}") {
        fun passRecipe(recipe: String) = "list/?scannedRecipe=$recipe"
    }
    object Details: Screen(route = "details/{id}")  {
        fun passId(id: Int) = "details/$id"
    }
    object Camera: Screen(route = "scanner")
}
