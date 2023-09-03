package hu.bme.aut.android.composerecipebook.data.repository

import androidx.room.Dao
import hu.bme.aut.android.composerecipebook.data.entities.RecipeEntity
import hu.bme.aut.android.composerecipebook.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeRepository {
    fun getAllRecipes(): Flow<List<RecipeEntity>>

    fun getRecipeById(id: Int): Flow<RecipeEntity>

    fun findRecipesByName(recipeName: String): Flow<List<RecipeEntity>>

    suspend fun insertRecipe(recipe: RecipeEntity)

    suspend fun updateRecipe(recipe: RecipeEntity)

    suspend fun deleteRecipe(id: Int)
}