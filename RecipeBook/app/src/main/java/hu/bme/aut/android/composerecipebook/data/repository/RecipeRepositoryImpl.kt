package hu.bme.aut.android.composerecipebook.data.repository

import hu.bme.aut.android.composerecipebook.data.dao.RecipeDao
import hu.bme.aut.android.composerecipebook.data.entities.RecipeEntity
import kotlinx.coroutines.flow.Flow

class RecipeRepositoryImpl(private val dao: RecipeDao) : RecipeRepository {
    override fun getAllRecipes(): Flow<List<RecipeEntity>> = dao.getAllRecipes()

    override fun getRecipeById(id: Int): Flow<RecipeEntity> = dao.getRecipeById(id)

    override fun findRecipesByName(recipeName: String): Flow<List<RecipeEntity>> = dao.findRecipesByName(recipeName)

    override suspend fun insertRecipe(recipe: RecipeEntity) = dao.insertRecipe(recipe)

    override suspend fun updateRecipe(recipe: RecipeEntity) = dao.updateRecipe(recipe)

    override suspend fun deleteRecipe(id: Int)  = dao.deleteRecipe(id)
}