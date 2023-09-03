package hu.bme.aut.android.composerecipebook.ui.common

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NormalTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    trailingIcon: @Composable (() -> Unit),
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    isError: Boolean = false,
    singleLine: Boolean = true,
    onDone: (KeyboardActionScope.() -> Unit)
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        trailingIcon = if (isError) {
            {
                Icon(imageVector = Icons.Default.ErrorOutline, contentDescription = null)
            }
        } else {
            {
                if (trailingIcon != null) {
                    trailingIcon()
                }
            }
        },
        modifier = modifier
            .width(TextFieldDefaults.MinWidth),
        singleLine = singleLine,
        readOnly = readOnly,
        isError = isError,
        enabled = enabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            capitalization = KeyboardCapitalization.Words,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = onDone
        )
    )
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun NormalTextFieldPreview() {
    NormalTextField(
        label = "Preview label",
        value = "Preview value",
        onValueChange = { },
        trailingIcon = {  }
    ) { }
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun NormalTextFieldErrorPreview() {
    NormalTextField(
        label = "Preview label",
        value = "Preview value",
        onValueChange = { },
        trailingIcon = {  },
        onDone = { },
        isError = true
    )
}