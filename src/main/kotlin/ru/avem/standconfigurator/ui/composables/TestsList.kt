package ru.avem.standconfigurator.ui.composables

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import ru.avem.standconfigurator.model.MainModel
import ru.avem.standconfigurator.model.blob.Test

@Composable
fun TestsList(modifier: Modifier = Modifier, onClick: (Test) -> Unit) {
    var selectedTest by remember { mutableStateOf<Test?>(null) }
    LazyColumn(
        modifier = modifier,
    ) {
        repeat(MainModel.testsList.size) { testIdx ->
            item {
                ListItem(item = MainModel.testsList[testIdx], selectedTest) {
                    onClick(it)
                    selectedTest = it
                }
            }
        }
    }
}
