package hu.bme.aut.android.composerecipebook.ui.list_items

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeListItem(
    recipeName: String,
    recipeId: Int,
    onOpenButtonClick: (Int) -> Unit
) {
    var expanded by rememberSaveable{ mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            ListItem(
                headlineText = {
                    Text(
                        text = recipeName,
                        fontWeight = FontWeight.Bold
                    )
                },
                modifier = Modifier
                    .clickable(
                        onClick = { expanded = !expanded }
                    )
            )
        }
        if(expanded) {
            Divider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.primary
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Button(
                    onClick = { onOpenButtonClick(recipeId) }
                ) {
                    Text(text = "Open")
                }
            }
        }
    }
    
}

@Preview
@Composable
fun RecipeListItemPreview() {
    RecipeListItem(
        recipeName = "French Fries",
        recipeId = 0,
        onOpenButtonClick = {  }
    )
}