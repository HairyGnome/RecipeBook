package hu.bme.aut.android.composerecipebook.ui.common

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MeasurementDropdown(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    onItemClick: (String) -> Unit
) {
    val dropdownMenuItemTexts = arrayOf(
        "ml",
        "cl",
        "dl",
        "l",
        "g",
        "dkg",
        "kg",
        "pcs",
        "cup",
        "tsp",
        "tbsp",
        "lbs",
        "oz(weight)",
        "oz(volume)",
        "pints"
    )

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = modifier
    ) {
        for(itemText in dropdownMenuItemTexts) {
            DropdownMenuItem(
                text = { Text(text = itemText) },
                onClick = { onItemClick(itemText) })
        }
    }
}