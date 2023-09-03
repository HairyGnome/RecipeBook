package hu.bme.aut.android.composerecipebook.ui.dialog

import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import hu.bme.aut.android.composerecipebook.ui.common.NormalTextField

@Composable
fun CreateInstructionDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit,
    show: Boolean
) {
    var textFieldValue by remember { mutableStateOf("") }
    var instructionError by rememberSaveable { mutableStateOf(false) }

    if(show) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = "Add new instruction") },
            text = {
                NormalTextField(
                    label = "Instruction",
                    value = textFieldValue,
                    onValueChange = { newValue ->
                        textFieldValue = newValue
                        if(textFieldValue.trim() != "") {
                            instructionError = false
                        }
                    },
                    trailingIcon = {},
                    onDone = {
                        if(textFieldValue.trim() == "") {
                            instructionError = true
                        }
                        onConfirm(textFieldValue)
                        textFieldValue = ""
                    },
                    isError = instructionError,
                    singleLine = false,
                    modifier = Modifier.height(240.dp)
                ) },
            confirmButton = {
                TextButton(onClick = {
                    if(textFieldValue.trim() == "") {
                        instructionError = true
                    }
                    onConfirm(textFieldValue)
                    textFieldValue = ""
                }) {
                    Text(text = "Create")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    onDismiss()
                    textFieldValue = ""
                }) {
                    Text(text = "Cancel")
                }
            }
        )
    }
}