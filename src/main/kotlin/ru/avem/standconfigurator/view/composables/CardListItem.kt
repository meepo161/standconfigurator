package ru.avem.standconfigurator.view.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.mouseClickable
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.isSecondaryPressed
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T> CardListItem(
    modifier: Modifier = Modifier,
    item: T,
    selectedItem: T?,
    onNextListItem: @Composable () -> Unit = {},
    onClick: (T, Boolean) -> Unit
) {
    Card(elevation = 4.dp, modifier = modifier.padding(8.dp).height(52.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                modifier = Modifier.background(if (selectedItem == item) MaterialTheme.colors.secondary else MaterialTheme.colors.surface)
                    .fillMaxWidth()
                    .mouseClickable {
                        if (buttons.isSecondaryPressed) {
                            onClick(item, false)
                        }
                    },
                onClick = {
                    onClick(item, true)
                }) {
                Text(
                    modifier = Modifier
                        .background(if (selectedItem == item) MaterialTheme.colors.secondary else MaterialTheme.colors.surface),
                    text = item.toString()
                )
            }
            if (selectedItem == item) {
                onNextListItem()
            }
        }
    }
}
