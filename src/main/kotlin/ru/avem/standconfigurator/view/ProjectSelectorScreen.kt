package ru.avem.standconfigurator.view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch
import org.jetbrains.compose.splitpane.ExperimentalSplitPaneApi
import ru.avem.standconfigurator.model.MainModel

@OptIn(ExperimentalSplitPaneApi::class)
class ProjectSelectorScreen : Screen {
    @OptIn(DelicateCoroutinesApi::class)
    @Composable
    @Preview
    override fun Content() {
        val localNavigator = LocalNavigator.currentOrThrow
        var isExpanded by remember { mutableStateOf(false) }
        val scaffoldState = rememberScaffoldState()
        val scope = rememberCoroutineScope()

        val projectName = remember { mutableStateOf(TextFieldValue()) }
        val projectNameErrorState = remember { mutableStateOf(false) }

        Scaffold(
            scaffoldState = scaffoldState,

            content = {
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar("Здравсвуйте, ${MainModel.currentUser.name}")
                }
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        TextField(
                            modifier = Modifier.weight(0.8f),
                            value = projectName.value,
                            onValueChange = {
                                if (projectNameErrorState.value) {
                                    projectNameErrorState.value = false
                                }
                                projectName.value = it
                            },
                            isError = projectNameErrorState.value,
                            label = {
                                Text(text = "Поиск")
                            },
                        )
                        Button(
                            modifier = Modifier.height(56.dp),
                            onClick = {
                            }) {
                            Text("Новый")
                        }
                        Button(
                            modifier = Modifier.height(56.dp),
                            onClick = {
                                localNavigator.push(MainScreen())
                            }) {
                            Text("Открыть")
                        }
                    }
                    Projects {

                    }
                }


            },

            bottomBar = {
                BottomAppBar {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        Text(text = "Инфо")
                    }
                }
            }

        )
    }
}
