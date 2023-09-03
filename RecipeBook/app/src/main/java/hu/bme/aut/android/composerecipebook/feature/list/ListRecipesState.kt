package hu.bme.aut.android.composerecipebook.feature.list

import hu.bme.aut.android.composerecipebook.domain.model.Recipe

data class ListRecipesState(
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val isError: Boolean = error != null,
    val recipes: List<Recipe> = emptyList()
)
