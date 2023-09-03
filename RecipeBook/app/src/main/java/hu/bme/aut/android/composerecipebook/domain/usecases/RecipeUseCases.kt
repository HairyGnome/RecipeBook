package hu.bme.aut.android.composerecipebook.domain.usecases

import hu.bme.aut.android.composerecipebook.data.repository.RecipeRepository

class RecipeUseCases(repository: RecipeRepository) {
    val loadRecipes = LoadRecipesUseCase(repository)
    val loadRecipe = LoadRecipeUseCase(repository)
    val findRecipes = FindRecipesUseCase(repository)
    val saveRecipe = SaveRecipeUseCase(repository)
    val updateRecipe = UpdateRecipeUseCase(repository)
    val deleteRecipe = DeleteRecipeUseCase(repository)
}