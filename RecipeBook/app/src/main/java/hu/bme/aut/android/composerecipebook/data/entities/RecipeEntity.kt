package hu.bme.aut.android.composerecipebook.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val ingredients: List<String>,
    val instructions: List<String>
)