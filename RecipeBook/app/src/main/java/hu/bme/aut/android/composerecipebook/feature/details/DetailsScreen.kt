package hu.bme.aut.android.composerecipebook.feature.details

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.bme.aut.android.composerecipebook.feature.camera.CameraScreen

@androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
@OptIn(ExperimentalFoundationApi::class)
@ExperimentalMaterial3Api
@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    onNavigateBack: () -> Unit,
    viewModel: DetailsViewModel = viewModel(factory = DetailsViewModel.Factory)
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    HorizontalPager(pageCount = 2, modifier = modifier) { page ->
        when(page) {
            0 -> {
                state.recipe?.let {
                    IngredientsList (
                        onNavigateBack = onNavigateBack,
                        onDialogConfirm = { ingredient ->
                            viewModel.onSaveIngredient(ingredient)
                        },
                        onDelete = { viewModel.onDeleteRecipe() },
                        qrBitmap = viewModel.generateQrCode(it),
                        recipeName = it.name,
                        isLoading = state.isLoading,
                        isError = state.isError,
                        ingredients = it.ingredients
                    )
                }
            }
            1 -> {
                state.recipe?.let {
                    state.recipe
                    InstructionsList (
                        onNavigateBack = onNavigateBack,
                        onDialogConfirm = { instruction ->
                            viewModel.onSaveInstruction(instruction)
                        },
                        onDelete = { viewModel.onDeleteRecipe() },
                        qrBitmap = viewModel.generateQrCode(it),
                        recipeName = it.name,
                        isLoading = state.isLoading,
                        isError = state.isError,
                        instructions = it.instructions
                    )
                }
            }
        }
    }
}


@ExperimentalMaterial3Api
@Preview
@Composable
fun DetailsScreenPreview() {
    DetailsScreen(modifier = Modifier.fillMaxSize(), onNavigateBack = {})
}