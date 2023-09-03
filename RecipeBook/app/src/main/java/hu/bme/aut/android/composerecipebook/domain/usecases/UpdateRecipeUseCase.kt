package hu.bme.aut.android.composerecipebook.domain.usecases

import hu.bme.aut.android.composerecipebook.data.repository.RecipeRepository
import hu.bme.aut.android.composerecipebook.domain.model.Recipe
import hu.bme.aut.android.composerecipebook.domain.model.asRecipeEntity

class UpdateRecipeUseCase(private val repository: RecipeRepository) {

    suspend operator fun invoke(recipe: Recipe) {
        repository.updateRecipe(recipe.asRecipeEntity())
    }
}