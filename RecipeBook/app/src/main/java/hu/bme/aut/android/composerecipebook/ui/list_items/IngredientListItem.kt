package hu.bme.aut.android.composerecipebook.ui.list_items

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@ExperimentalMaterial3Api
@Composable
fun RecipeDetailsListItem(
    ingredient: String
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = ingredient)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun RecipeDetailsListItem() {
    RecipeDetailsListItem(ingredient = "Dog")
}