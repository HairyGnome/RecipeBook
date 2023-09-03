package hu.bme.aut.android.composerecipebook.feature

import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import hu.bme.aut.android.composerecipebook.feature.camera.CameraScreen
import hu.bme.aut.android.composerecipebook.feature.list.ListRecipesViewModel
import hu.bme.aut.android.composerecipebook.ui.common.NormalTextField
import hu.bme.aut.android.composerecipebook.ui.dialog.CreateRecipeDialog
import hu.bme.aut.android.composerecipebook.ui.list.RecipeAppBar
import hu.bme.aut.android.composerecipebook.ui.list_items.RecipeListItem
import java.lang.Integer.parseInt

@androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun ListScreen(
    modifier: Modifier = Modifier,
    onItemOpenButtonClick: (Int) -> Unit,
    onScannerButtonClick: () -> Unit,
    viewModel: ListRecipesViewModel = viewModel(factory = ListRecipesViewModel.Factory)
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    var searchTerm by remember { mutableStateOf("") }
    var showDialog by rememberSaveable { mutableStateOf(false) }

    var cameraPermisson = rememberPermissionState(permission = android.Manifest.permission.CAMERA)


    Scaffold (
        modifier = modifier,
        topBar = {
            RecipeAppBar(
                title = "",
                modifier = Modifier,
                actions = {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()) {
                        Box(
                            modifier = Modifier
                                .weight(0.9F)
                                .align(Alignment.CenterVertically)
                        ) {
                            NormalTextField(
                                label = "Search",
                                value = searchTerm,
                                onValueChange = { newValue ->
                                    searchTerm = newValue
                                    viewModel.findRecipesByName(searchTerm)
                                },
                                trailingIcon = { },
                                onDone = {},
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .weight(0.1F)
                                .align(Alignment.CenterVertically),
                            contentAlignment = Alignment.Center
                        ) {
                            IconButton(
                                onClick = {
                                    cameraPermisson.launchPermissionRequest()
                                    onScannerButtonClick()
                                }
                            ) {
                                Icon(imageVector = Icons.Default.QrCodeScanner, contentDescription = "QR code scanning")
                            }
                        }
                    }
                },
                navigationIcon = {}
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                shape = CircleShape,
                modifier = Modifier.size(70.dp),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Button",
                    tint = Color.White
                )
            }
        }
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(paddingValues = it)
        ) {
            if(state.isLoading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.secondaryContainer
                )
            } else if(state.isError) {
                Text(
                    text = "An error has occurred",
                    color = Color.Red,
                    fontWeight = FontWeight.Bold
                )
            } else if(state.recipes.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center,
                    content = {
                        Text(
                            text = "We haven't found any recipes",
                            textAlign = TextAlign.Center
                        )
                    }
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(5.dp))
                ) {
                    items(state.recipes.size) {idx ->
                        RecipeListItem(
                            recipeName = state.recipes[idx].name,
                            recipeId = state.recipes[idx].id,
                            onOpenButtonClick = { id ->
                                onItemOpenButtonClick(id)
                            }
                        )
                        if (idx != state.recipes.lastIndex) {
                            Divider(
                                thickness = 2.dp,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
        CreateRecipeDialog(
            show = showDialog,
            onDismiss = { showDialog = false },
            onConfirm = { recipeName ->
                viewModel.onSave(recipeName)
                showDialog = false
            }
        )
    }
}



@ExperimentalMaterial3Api
@Preview
@Composable
fun ListScreenPreview() {
    ListScreen(
        modifier = Modifier.fillMaxSize(),
        onItemOpenButtonClick = { },
        onScannerButtonClick = {  }
    )
}