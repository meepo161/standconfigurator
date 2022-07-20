package ru.avem.standconfigurator.view.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.mouseClickable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.isSecondaryPressed

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T> ListItem(item: T, selectedItem: T?, onClick: (T, Boolean) -> Unit) {
    TextButton(
        modifier = Modifier.background(if (selectedItem == item) MaterialTheme.colors.secondary else MaterialTheme.colors.surface).fillMaxWidth()
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
}
