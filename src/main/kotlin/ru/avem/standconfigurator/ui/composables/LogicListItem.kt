package ru.avem.standconfigurator.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CardListItem(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Card(elevation = 4.dp, modifier = Modifier.padding(8.dp)) {
        Row(
            modifier = modifier.fillMaxWidth().padding(8.dp), horizontalArrangement = Arrangement.Center
        ) {
            content()
        }
    }
}
