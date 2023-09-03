package hu.bme.aut.android.composerecipebook.domain.model

import hu.bme.aut.android.composerecipebook.data.entities.RecipeEntity

data class Recipe(
    val id: Int = 0,
    val name: String,
    val ingredients: List<String>,
    val instructions: List<String>
)

fun Recipe.asRecipeEntity(): RecipeEntity = RecipeEntity(
    id = id,
    name = name,
    ingredients = ingredients,
    instructions = instructions
)

fun RecipeEntity.asRecipe(): Recipe = Recipe(
    id = id,
    name = name,
    ingredients = ingredients,
    instructions = instructions
)