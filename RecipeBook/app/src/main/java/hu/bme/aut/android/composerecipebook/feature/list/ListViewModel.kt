package hu.bme.aut.android.composerecipebook.feature.list

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.google.gson.Gson
import hu.bme.aut.android.composerecipebook.RecipeApplication
import hu.bme.aut.android.composerecipebook.domain.model.Recipe
import hu.bme.aut.android.composerecipebook.domain.usecases.RecipeUseCases
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException


class ListRecipesViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val recipeOperations: RecipeUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(ListRecipesState())
    val state = _state.asStateFlow()

    init {
        val scannedRecipe = savedStateHandle.get<String>("scannedRecipe")
        if(scannedRecipe != null && scannedRecipe != "") {
            onSaveRecipeFromQr(scannedRecipe)
        }
        loadRecipes()
    }

    private fun loadRecipes() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                CoroutineScope(coroutineContext).launch(Dispatchers.IO) {
                    val recipes = recipeOperations.loadRecipes().getOrThrow()
                    _state.update {
                        it.copy(
                            isLoading = false,
                            recipes = recipes
                        )
                    }
                }
            }
            catch (e: IOException) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e
                    )
                }
            }
        }
    }

    fun findRecipesByName(recipeName: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                CoroutineScope(coroutineContext).launch(Dispatchers.IO) {
                    val recipes = recipeOperations.findRecipes(recipeName).getOrThrow()
                    _state.update {
                        it.copy(
                            isLoading = false,
                            recipes = recipes
                        )
                    }
                }
            }
            catch (e: java.lang.Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e
                    )
                }
            }
        }
    }

    fun onSave(recipeName: String) {
        viewModelScope.launch {
            try {
                recipeOperations.saveRecipe(
                    Recipe(
                        name = recipeName,
                        ingredients = emptyList(),
                        instructions = emptyList()
                    )
                )
                loadRecipes()
            } catch (e: IOException) {
                _state.update {
                    it.copy(
                        error = e
                    )
                }
            }
        }
    }

    fun onSaveRecipeFromQr(recipe: String) {
        viewModelScope.launch {
            try {
                val newRecipe = Gson().fromJson(recipe, Recipe::class.java)
                recipeOperations.saveRecipe(newRecipe)
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        error = e
                    )
                }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                val recipeOperations = RecipeUseCases(RecipeApplication.repository)
                ListRecipesViewModel(recipeOperations = recipeOperations, savedStateHandle = savedStateHandle)
            }
        }
    }
}