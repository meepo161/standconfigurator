package ru.avem.standconfigurator.view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch
import org.jetbrains.compose.splitpane.ExperimentalSplitPaneApi
import org.jetbrains.compose.splitpane.HorizontalSplitPane
import org.jetbrains.compose.splitpane.VerticalSplitPane
import org.jetbrains.compose.splitpane.rememberSplitPaneState
import ru.avem.standconfigurator.Devices
import ru.avem.standconfigurator.model.MainModel
import ru.avem.standconfigurator.model.ProjectRepository

@OptIn(ExperimentalSplitPaneApi::class)
class MainScreen : Screen {
    @OptIn(DelicateCoroutinesApi::class)
    @Composable
    @Preview
    override fun Content() {
        val localNavigator = LocalNavigator.currentOrThrow
        var isExpanded by remember { mutableStateOf(false) }
        val scaffoldState = rememberScaffoldState()
        val scope = rememberCoroutineScope()

        var projects = ProjectRepository.projects

        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                TopAppBar {
                    Button(onClick = { isExpanded = true }) {
                        Text("Файл")
                    }
                    DropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
                        DropdownMenuItem(onClick = {
                        }) {
                            Column {
                                Button(
                                    onClick = {
                                        scope.launch {
                                            scaffoldState.snackbarHostState.showSnackbar("NIGGER")
                                        }
                                    }) {
                                    Text("Новый")
                                }
                                Button(
                                    onClick = {
                                        localNavigator.push(LoginScreen())
                                    }) {
                                    Text("Выход")
                                }
                            }
                        }
                    }
                }
            },

            content = {
                val splitPaneState = rememberSplitPaneState(0.3f)
                val splitPaneState2 = rememberSplitPaneState(0.7f)
                val verticalSplitpaneState = rememberSplitPaneState(0.5f)

                var textCenter by remember { mutableStateOf("") }
                var textRight by remember { mutableStateOf("") }

                HorizontalSplitPane(splitPaneState = splitPaneState) {
                    first() {
                        VerticalSplitPane(
                            splitPaneState = verticalSplitpaneState,
                            modifier = Modifier.background(Color.Gray)
                        ) {
                            first {
                                Tests {
                                    textCenter = it
                                }
                            }
                            second {
                                Devices {
                                    textRight = it
                                }
                            }
                            splitter {
                                visiblePart {
                                    Box(
                                        Modifier
                                            .height(1.dp)
                                            .fillMaxWidth()
                                    )
                                }
                                handle {
                                    Box(
                                        Modifier
                                            .markAsHandle()
                                            .cursorForVerticalResize()
                                            .height(8.dp)
                                            .fillMaxWidth()
                                    ) {
                                        Box(Modifier.background(Color.Blue).height(1.dp).fillMaxWidth()) {
                                        }
                                    }
                                }
                            }
                        }
                    }
                    second {
                        HorizontalSplitPane(splitPaneState = splitPaneState2) {
                            first {
                                Column(
                                    modifier = Modifier.fillMaxHeight().fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Box(contentAlignment = Alignment.TopCenter) {
                                        Text(textCenter)
                                    }
                                }
                            }
                            second {
                                Column(
                                    modifier = Modifier.fillMaxHeight().fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Box(contentAlignment = Alignment.TopCenter) {
                                        Text(textRight)
                                    }
                                }
                            }
                            splitter {
                                visiblePart {
                                    Box(
                                        Modifier
                                            .width(1.dp)
                                            .fillMaxHeight()
                                            .background(MaterialTheme.colors.background)
                                    )
                                }
                                handle {
                                    Box(
                                        Modifier
                                            .markAsHandle()
                                            .cursorForHorizontalResize()
                                            .width(16.dp)
                                            .fillMaxHeight(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Box(Modifier.background(Color.Blue).width(1.dp).fillMaxHeight()) {
                                        }
                                    }
                                }
                            }
                        }
                    }
                    splitter {
                        visiblePart {
                            Box(
                                Modifier
                                    .width(1.dp)
                                    .fillMaxHeight()
                                    .background(MaterialTheme.colors.background)
                            )
                        }
                        handle {
                            Box(
                                Modifier
                                    .markAsHandle()
                                    .cursorForHorizontalResize()
                                    .width(16.dp)
                                    .fillMaxHeight(),
                                contentAlignment = Alignment.Center
                            ) {
                                Box(Modifier.background(Color.Blue).width(1.dp).fillMaxHeight()) {
                                }
                            }
                        }
                    }
                }
            },

            bottomBar = {
                BottomAppBar {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        Text(text = projects[MainModel.currentProjectIndex].toString())
                        MainModel.currentProjectIndex = -1
                    }
                }
            }

        )
    }
}
