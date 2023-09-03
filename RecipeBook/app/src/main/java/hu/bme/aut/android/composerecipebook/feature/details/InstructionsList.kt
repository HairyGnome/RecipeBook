package hu.bme.aut.android.composerecipebook.feature.details

import android.graphics.Bitmap
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import hu.bme.aut.android.composerecipebook.ui.dialog.CreateIngredientDialog
import hu.bme.aut.android.composerecipebook.ui.dialog.CreateInstructionDialog
import hu.bme.aut.android.composerecipebook.ui.dialog.QrDialog
import hu.bme.aut.android.composerecipebook.ui.list.RecipeAppBar
import hu.bme.aut.android.composerecipebook.ui.list_items.InstructionListItem
import hu.bme.aut.android.composerecipebook.ui.list_items.RecipeDetailsListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstructionsList(
    onNavigateBack: () -> Unit,
    onDialogConfirm: (String) -> Unit,
    onDelete: () -> Unit,
    qrBitmap: Bitmap,
    isLoading: Boolean = false,
    isError: Boolean = false,
    recipeName: String,
    instructions: List<String> = emptyList()
) {
    var showAddDialog by rememberSaveable { mutableStateOf(false) }
    var showQrDialog by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            RecipeAppBar(
                title = "Instructions",
                modifier = Modifier,
                actions = {
                    IconButton(onClick = { showQrDialog = true }) {
                        Icon(imageVector = Icons.Default.QrCode2, contentDescription = "QR Code Button")
                    }
                    IconButton(
                        onClick = {
                            onDelete()
                            onNavigateBack()
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Remove Button")
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateBack
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back Button"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
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
        if(isLoading) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.secondaryContainer
            )
        } else if(isError) {
            Text(
                text = "An error has occurred",
                color = Color.Red,
                fontWeight = FontWeight.Bold
            )
        } else if(instructions.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center,
                content = {
                    Text(
                        text = "We haven't found any instructions",
                        textAlign = TextAlign.Center
                    )
                }
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyColumn() {
                    items(instructions.size) { idx ->
                        InstructionListItem(
                            instruction = instructions[idx],
                            instructionIdx = idx+1
                        )
                    }
                }
            }
        }
        CreateInstructionDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { instruction ->
                onDialogConfirm(instruction)
                showAddDialog = false
            },
            show = showAddDialog
        )
        QrDialog(
            qrCode = qrBitmap.asImageBitmap(),
            show = showQrDialog,
            recipeName = recipeName,
            onDismiss = { showQrDialog = false }
        )
    }
}