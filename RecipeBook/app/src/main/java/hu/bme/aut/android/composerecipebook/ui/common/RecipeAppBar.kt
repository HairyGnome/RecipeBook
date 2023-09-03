package hu.bme.aut.android.composerecipebook.ui.list

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.bme.aut.android.composerecipebook.ui.common.NormalTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeAppBar(
    modifier: Modifier = Modifier,
    title: String,
    actions: @Composable() RowScope.() -> Unit = {},
    navigationIcon: @Composable (() -> Unit)
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(text = title) },
        navigationIcon = navigationIcon,
        actions = actions,
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun ListScreenTopAppBarPreview() {
    RecipeAppBar(
        title = "",
        modifier = Modifier,
        navigationIcon = { },
        actions = {
            NormalTextField(
                label = "Search",
                value = "",
                onValueChange = {},
                trailingIcon = { },
                onDone = {},
                modifier = Modifier.fillMaxWidth()
                    .padding(5.dp)
            )
        }
    )
}