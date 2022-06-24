package ru.avem.standconfigurator.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun TestsList(modifier: Modifier = Modifier, onClick: (String) -> Unit) {
    val stateFlagList = mutableStateListOf<Boolean>()
    for (i in 0..50) {
        stateFlagList.add(false)
    }
    val testsState by rememberSaveable {
        mutableStateOf(stateFlagList)
    }

    LazyColumn(
        modifier = modifier
    ) {
        items((1..50).toList()) {
            TextButton(
                modifier = Modifier.background(if (testsState[it]) Color.Cyan else Color.White).fillMaxWidth(),
                onClick = {
                    for (i in 0..50) {
                        testsState[i] = false
                    }
                    testsState[it] = !testsState[it]
                    onClick("Опыт $it")
                }) {
                Text(
                    modifier = Modifier
                        .background(if (testsState[it]) Color.Cyan else Color.White),
                    text = "Опыт $it"
                )
            }
        }
    }
}
