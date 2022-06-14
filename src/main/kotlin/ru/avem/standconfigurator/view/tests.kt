package ru.avem.standconfigurator.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun Tests(onClick: (String) -> Unit) {
    val stateFlagList = mutableStateListOf<Boolean>()
    for (i in 0..50) {
        stateFlagList.add(false)
    }
    val testsState by rememberSaveable {
        mutableStateOf(stateFlagList)
    }

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        items((1..50).toList()) {
            Box {
                TextButton(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight().fillMaxSize()
                        .background(if (testsState[it]) Color.Cyan else Color.White),
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
}
