package hu.bme.aut.android.composerecipebook.feature.details

import android.graphics.Bitmap
import hu.bme.aut.android.composerecipebook.domain.model.Recipe

data class RecipeDetailsState(
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val isError: Boolean = error != null,
    val recipe: Recipe? = null,
    val qrCode: Bitmap? = null
)