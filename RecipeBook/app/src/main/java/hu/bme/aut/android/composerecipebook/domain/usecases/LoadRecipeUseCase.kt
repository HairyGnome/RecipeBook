package hu.bme.aut.android.composerecipebook.domain.usecases

import hu.bme.aut.android.composerecipebook.data.repository.RecipeRepository
import hu.bme.aut.android.composerecipebook.domain.model.Recipe
import hu.bme.aut.android.composerecipebook.domain.model.asRecipe
import kotlinx.coroutines.flow.first
import java.io.IOException

class LoadRecipeUseCase(private val repository: RecipeRepository) {

        suspend operator fun invoke(id: Int): Result<Recipe> {
            return try {
                val recipe = repository.getRecipeById(id).first().asRecipe()
                Result.success(recipe)
            } catch(e: IOException) {
                Result.failure(e)
            }
        }
}