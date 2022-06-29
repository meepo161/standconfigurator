package ru.avem.standconfigurator.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ru.avem.standconfigurator.model.IListItem

@Composable
fun <T : IListItem> ListItem(item: T, selectedItem: T?, onClick: (T) -> Unit) {
    TextButton(
        modifier = Modifier.background(if (selectedItem == item) Color.Cyan else Color.White).fillMaxWidth(),
        onClick = {
            onClick(item)
        }) {
        Text(
            modifier = Modifier
                .background(if (selectedItem == item) Color.Cyan else Color.White),
            text = item.text
        )
    }
}
