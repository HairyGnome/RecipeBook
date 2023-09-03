package hu.bme.aut.android.composerecipebook.feature.details

import android.graphics.Bitmap
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.google.gson.Gson
import hu.bme.aut.android.composerecipebook.RecipeApplication
import hu.bme.aut.android.composerecipebook.domain.model.Recipe
import hu.bme.aut.android.composerecipebook.domain.usecases.RecipeUseCases
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException

class DetailsViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val recipeOperations: RecipeUseCases
) : ViewModel() {
    private val _state = MutableStateFlow(RecipeDetailsState())
    val state = _state.asStateFlow()

    init {
        savedStateHandle.get<Int>("id")?.let { loadRecipe(it) }
    }

    fun generateQrCode(recipe: Recipe): Bitmap {
        var recipeJson = Gson().toJson(recipe)
        var qrEncoder = QRGEncoder(recipeJson, null, QRGContents.Type.TEXT, 400)
        return qrEncoder.bitmap
    }

    private fun loadRecipe(recipeId: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                CoroutineScope(coroutineContext).launch(Dispatchers.IO) {
                    val recipe = recipeOperations.loadRecipe(recipeId).getOrThrow()
                    _state.update {
                        it.copy(
                            isLoading = false,
                            recipe = recipe
                        )
                    }
                }
            } catch(e: IOException) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e
                    )
                }
            }
        }
    }

    fun onDeleteRecipe() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            try {
                CoroutineScope(coroutineContext).launch(Dispatchers.IO) {
                    _state.first().recipe?.let { recipeOperations.deleteRecipe(it.id) }
                }
            } catch(e: IOException) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e
                    )
                }
            }
        }
    }

    fun onSaveInstruction(instruction: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                CoroutineScope(coroutineContext).launch(Dispatchers.IO) {
                    var newList = _state.first().recipe?.instructions?.toMutableList()
                    newList?.add(instruction)
                    val oldRecipe = _state.first().recipe
                    val updatedRecipe = Recipe(
                        id = oldRecipe!!.id,
                        name = oldRecipe!!.name,
                        ingredients = oldRecipe!!.ingredients,
                        instructions = newList!!
                    )
                    recipeOperations.updateRecipe(updatedRecipe)
                    loadRecipe(updatedRecipe.id)
                    _state.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                }
            } catch (e: IOException) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e
                    )
                }
            }
        }
    }

    fun onSaveIngredient(ingredient: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                CoroutineScope(coroutineContext).launch(Dispatchers.IO) {
                    var newList = _state.first().recipe?.ingredients?.toMutableList()
                    newList?.add(ingredient)
                    val oldRecipe = _state.first().recipe
                    val updatedRecipe = Recipe(
                        id = oldRecipe!!.id,
                        name = oldRecipe!!.name,
                        ingredients = newList!!,
                        instructions = oldRecipe!!.instructions
                    )
                    recipeOperations.updateRecipe(updatedRecipe)
                    loadRecipe(updatedRecipe.id)
                    _state.update { it.copy(isLoading = false) }
                }
            } catch(e: IOException) {
                _state.update {
                    it.copy(
                        isLoading = false,
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
                DetailsViewModel(recipeOperations = recipeOperations, savedStateHandle = savedStateHandle)
            }
        }
    }
}