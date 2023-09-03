package hu.bme.aut.android.composerecipebook.ui.dialog

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.bme.aut.android.composerecipebook.ui.common.MeasurementDropdown
import hu.bme.aut.android.composerecipebook.ui.common.NormalTextField
import hu.bme.aut.android.composerecipebook.ui.common.NumberTextField

@Composable
fun CreateIngredientDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit,
    show: Boolean = false
) {
    var nameFieldValue by remember { mutableStateOf("") }
    var quantityFieldValue by remember { mutableStateOf("") }

    var nameError by rememberSaveable { mutableStateOf(false) }
    var quantityError by rememberSaveable { mutableStateOf(false) }

    var dropdownExpanded by rememberSaveable { mutableStateOf(false) }
    val dropDownButtonRotation by animateFloatAsState(targetValue = if(dropdownExpanded) 180f else 0f)

    var dropDownSelected by remember { mutableStateOf("ml") }

    if(show) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = "Add new ingredient") },
            text = {
                Column() {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Box(modifier = Modifier.weight(0.4F)) {
                            NumberTextField(
                                label = "Qty",
                                value = quantityFieldValue,
                                onValueChange = { newValue ->
                                    quantityFieldValue = newValue
                                },
                                trailingIcon = {},
                                onDone = {
                                    if(quantityFieldValue.trim() == "") {
                                        quantityError = true
                                        return@NumberTextField
                                    } else {
                                        if(nameFieldValue.trim() == "") {
                                            nameError = true
                                            return@NumberTextField
                                        } else {
                                            onConfirm("$quantityFieldValue $dropDownSelected ${nameFieldValue.trim()}")
                                            nameFieldValue = ""
                                            quantityFieldValue = ""
                                        }
                                    }
                                },
                                isError = quantityError
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Box(modifier = Modifier.weight(0.6F)) {
                            NormalTextField(
                                label = "",
                                value = dropDownSelected,
                                onValueChange = { },
                                trailingIcon = {
                                    IconButton(
                                        onClick = { dropdownExpanded = !dropdownExpanded },
                                        modifier = Modifier.rotate(degrees = dropDownButtonRotation)
                                    ) {
                                        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                                    }
                                },
                                onDone = { },
                                readOnly = true
                            )
                            MeasurementDropdown(
                                expanded = dropdownExpanded,
                                onDismissRequest = { dropdownExpanded = false },
                                onItemClick = { itemText ->
                                    dropDownSelected = itemText
                                    dropdownExpanded = false
                                }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    NormalTextField(
                        label = "Ingredient",
                        value = nameFieldValue,
                        onValueChange = { newValue ->
                            nameFieldValue = newValue
                        },
                        trailingIcon = {},
                        onDone = {
                            if(nameFieldValue.trim() == "") {
                                nameError = true
                                return@NormalTextField
                            }
                            if(quantityFieldValue.trim() == "") {
                                quantityError = true
                                return@NormalTextField
                            }
                            onConfirm("$quantityFieldValue $dropDownSelected ${nameFieldValue.trim()}")
                            quantityFieldValue = ""
                            nameFieldValue = ""
                        },
                        isError = nameError
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (quantityFieldValue.trim() == "") {
                            quantityError = true
                            return@TextButton
                        } else {
                            if (nameFieldValue.trim() == "") {
                                nameError = true
                                return@TextButton
                            } else {
                                onConfirm("$quantityFieldValue $dropDownSelected ${nameFieldValue.trim()}")
                                nameFieldValue = ""
                                quantityFieldValue = ""
                            }
                        }
                    }
                ) {
                    Text(text = "Create")
                }
            },
            dismissButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text(text = "Cancel")
                }
            }
        )
    }
}

@Preview
@Composable
fun CreateIngredientDialogPreview() {
    CreateIngredientDialog(onDismiss = { /*TODO*/ }, onConfirm = { /*TODO*/ }, show = true)
}