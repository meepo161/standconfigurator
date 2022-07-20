package ru.avem.standconfigurator.view.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@Composable
fun <T> LazyList(
    modifier: Modifier = Modifier, state: LazyListState = rememberLazyListState(), items: List<T>, selectedItem: T?,
    onNextListItem:
    @Composable (Int) -> Unit = {}, onClick: (T, Boolean, Int) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        state = state
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
