package hu.bme.aut.android.composerecipebook.ui.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import hu.bme.aut.android.composerecipebook.ui.common.NormalTextField

@Composable
fun CreateRecipeDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit,
    show: Boolean
) {
    var textFieldValue by remember { mutableStateOf("") }
    var nameError by rememberSaveable { mutableStateOf(false) }

    if(show) {
        AlertDialog(
            onDismissRequest = {
                onDismiss()
                textFieldValue = ""
            },
            title = { Text(text = "Create new recipe") },
            text = {
                NormalTextField(
                    label = "Name",
                    value = textFieldValue,
                    onValueChange = { newValue ->
                        textFieldValue = newValue
                        if(textFieldValue.trim() != "") {
                            nameError = false
                        }
                    },
                    trailingIcon = {},
                    onDone = {
                        if(textFieldValue.trim() == "") {
                            nameError = true
                            return@NormalTextField
                        }
                        onConfirm(textFieldValue.trim())
                        textFieldValue = ""
                    },
                    isError = nameError
                ) },
            confirmButton = {
                TextButton(onClick = {
                    if(textFieldValue.trim() == "") {
                        nameError = true
                        return@TextButton
                    }
                    onConfirm(textFieldValue.trim())
                    textFieldValue = ""
                }) {
                    Text(text = "Create")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onDismiss()
                        textFieldValue = ""
                    }
                ) {
                    Text(text = "Cancel")
                }
            }
        )
    }
}