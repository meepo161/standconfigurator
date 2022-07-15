package ru.avem.standconfigurator.view.composables

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import ru.avem.standconfigurator.model.IListItem

@Composable
fun <T : IListItem> LazyList(modifier: Modifier = Modifier, items: List<T>, onClick: (T) -> Unit) {
    var selectedItem by remember { mutableStateOf(items.firstOrNull()) }
    LazyColumn(
        modifier = modifier,
    ) {
        repeat(items.size) { testIdx ->
            item {
                ListItem(item = items[testIdx], selectedItem) {
                    onClick(it)
                    selectedItem = it
                }
            }
        }
    }
}
