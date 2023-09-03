package hu.bme.aut.android.composerecipebook

import android.app.Application
import androidx.room.Room
import hu.bme.aut.android.composerecipebook.data.RecipeDatabase
import hu.bme.aut.android.composerecipebook.data.repository.RecipeRepository
import hu.bme.aut.android.composerecipebook.data.repository.RecipeRepositoryImpl

class RecipeApplication : Application() {
    companion object {
        private lateinit var db: RecipeDatabase

        lateinit var repository: RecipeRepository
    }

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            applicationContext,
            RecipeDatabase::class.java,
            "recipe_database"
        ).fallbackToDestructiveMigration().build()

        repository = RecipeRepositoryImpl(db.dao)
    }
}