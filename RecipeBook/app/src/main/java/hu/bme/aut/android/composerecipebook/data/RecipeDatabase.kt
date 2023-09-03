package hu.bme.aut.android.composerecipebook.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import hu.bme.aut.android.composerecipebook.data.converters.ListConverter
import hu.bme.aut.android.composerecipebook.data.dao.RecipeDao
import hu.bme.aut.android.composerecipebook.data.entities.RecipeEntity

@Database(entities = [RecipeEntity::class], version = 1)
@TypeConverters(ListConverter::class)
abstract class RecipeDatabase : RoomDatabase() {
    abstract val dao: RecipeDao
}