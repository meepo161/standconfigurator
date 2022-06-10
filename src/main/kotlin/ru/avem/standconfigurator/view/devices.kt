package ru.avem.standconfigurator

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
fun Devices() {
    val stateFlagList = mutableStateListOf<Boolean>()
    for (i in 0..50) {
        stateFlagList.add(false)
    }
    val devicesState by rememberSaveable {
        mutableStateOf(stateFlagList)
    }

    LazyColumn(
        modifier = Modifier.fillMaxHeight(0.5f).fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        items((1..50).toList()) {
            Box {
                TextButton(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight().fillMaxSize()
                        .background(if (devicesState[it]) Color.Cyan else Color.White),
                    onClick = {
                        for (i in 0..50) {
                            devicesState[i] = false
                        }
                        devicesState[it] = !devicesState[it]
                    }) {
                    Text(
                        modifier = Modifier
                            .background(if (devicesState[it]) Color.Cyan else Color.White),
                        text = "Прибор $it"
                    )
                }
            }
        }
    }
}
