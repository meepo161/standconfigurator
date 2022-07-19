package ru.avem.standconfigurator.view.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import ru.avem.standconfigurator.model.IListItem

@Composable
fun <T : IListItem> LazyList(
    modifier: Modifier = Modifier, items: List<T>, selectedItem: T?,
    onNextListItem:
    @Composable (Int) -> Unit = {}, onClick: (T, Boolean, Int) -> Unit
) {
    LazyColumn(
        modifier = modifier,
    ) {
        repeat(items.size) { index ->
            item {
                Row {
                    ListItem(item = items[index], selectedItem) { it, isPrimary ->
                        onClick(it, isPrimary, index)
//                        selectedItem = it
                    }
                    onNextListItem(index)
                }
            }
        }
    }
}
