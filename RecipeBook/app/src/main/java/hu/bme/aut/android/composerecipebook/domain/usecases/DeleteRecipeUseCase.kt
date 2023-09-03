package hu.bme.aut.android.composerecipebook.domain.usecases

import hu.bme.aut.android.composerecipebook.data.repository.RecipeRepository

class DeleteRecipeUseCase(private val repository: RecipeRepository) {

    suspend operator fun invoke(id: Int) {
        repository.deleteRecipe(id)
    }
}