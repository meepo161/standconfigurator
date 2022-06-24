package ru.avem.standconfigurator.view.widgets

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun <T> ComboBox(
    initialValue: T,
    onDismissState: () -> Unit = {},
    items: List<T>,
    selectedValue: (T) -> Unit,
    isEditable: Boolean = true
) {
    var expandedState by remember {
        mutableStateOf(false)
    }
    var selectedItem by remember {
        mutableStateOf(initialValue)
    }

    Column(modifier = Modifier.border(1.dp, Color.Black).width(280.dp).height(56.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().clickable {
                expandedState = true
            }.fillMaxWidth().height(64.dp),
        ) {
            Text(selectedItem.toString(), modifier = Modifier.padding(4.dp))
            Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
        }
        if (isEditable) {
            DropdownMenu(
                expanded = expandedState,
                onDismissRequest = {
                    expandedState = false
                    onDismissState()
                }) {
                items.forEach { item ->
                    DropdownMenuItem(modifier = Modifier.fillMaxWidth().height(64.dp), onClick = {
                        selectedItem = item
                        selectedValue(item)
                        expandedState = false
                    }) {
                        Text(item.toString())
                    }
                }
            }
        }
    }
}