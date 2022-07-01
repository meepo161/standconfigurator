package ru.avem.standconfigurator.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.OfflineBolt
import androidx.compose.material.icons.outlined.OnlinePrediction
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.launch
import ru.avem.standconfigurator.model.MainModel
import ru.avem.standconfigurator.model.blob.LogicItem
import ru.avem.standconfigurator.model.blob.Project
import ru.avem.standconfigurator.model.blob.Test
import ru.avem.standconfigurator.ui.composables.LazyList
import ru.avem.standconfigurator.ui.composables.CardListItem

class MainScreen(private val currentProject: Project) : Screen {
    @Composable
    override fun Content() {
        val localNavigator = LocalNavigator.currentOrThrow
        val scope = rememberCoroutineScope()

        var selectedTest: Test by remember { mutableStateOf(currentProject.tests.first()) }

        var isProjectMenuExpanded by remember { mutableStateOf(false) }
        var isProtectionsMenuExpanded by remember { mutableStateOf(false) }

        val logicsScrollState = rememberLazyListState()

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Row {
                            Button(onClick = { isProjectMenuExpanded = true }) {
                                Text(text = "Проект")
                            }
                            DropdownMenu(
                                expanded = isProjectMenuExpanded,
                                onDismissRequest = { isProjectMenuExpanded = false }) {

                                DropdownMenuItem(onClick = {
                                    localNavigator.pop()
                                }) {
                                    Icon(
                                        painter = painterResource("icons/ic_draft_fill0_wght400_grad0_opsz48.xml"),
                                        contentDescription = null
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                    Text("Новый")
                                }

                                DropdownMenuItem(onClick = {
                                    localNavigator.push(LoginScreen())
                                }) {
                                    Icon(imageVector = Icons.Default.Logout, contentDescription = null)
                                    Spacer(modifier = Modifier.weight(1f))
                                    Text("Выйти")
                                }

                                DropdownMenuItem(onClick = {
                                    MainModel.isOpen.value = false
                                }) {
                                    Icon(imageVector = Icons.Default.ExitToApp, contentDescription = null)
                                    Spacer(modifier = Modifier.weight(1f))
                                    Text("Выход")
                                }
                            }

                            Button(onClick = {
                                localNavigator.push(DevicesScreen(currentProject))

                            }) {
                                Text(text = "Приборы")
                            }

                            Button(onClick = { isProtectionsMenuExpanded = true }) {
                                Text(text = "Защиты")
                            }
                            DropdownMenu(
                                expanded = isProtectionsMenuExpanded,
                                onDismissRequest = { isProtectionsMenuExpanded = false }) {

                                DropdownMenuItem(onClick = {
//                                    localNavigator.push(SoftAlarmsScreen()) todo
                                }) {
                                    Icon(imageVector = Icons.Outlined.OnlinePrediction, contentDescription = null)
                                    Spacer(modifier = Modifier.weight(1f))
                                    Text("Софт-аварии")
                                }

                                DropdownMenuItem(onClick = {
//                                    localNavigator.push(HardAlarmsScreen()) todo
                                }) {
                                    Icon(imageVector = Icons.Default.OfflineBolt, contentDescription = null)
                                    Spacer(modifier = Modifier.weight(1f))
                                    Text("Хард-аварии")
                                }
                            }
                        }
                    }
                )
            },
            bottomBar = {
                BottomAppBar {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        Text(text = currentProject.toString())
                    }
                }
            }
        ) {
            Row(modifier = Modifier.fillMaxWidth().padding(bottom = 56.dp)) {
                Column(modifier = Modifier.weight(.1f)) {
                    LazyList(
                        modifier = Modifier.fillMaxHeight(),
                        items = currentProject.tests
                    ) {
                        selectedTest = it
                    }
                }
                LazyColumn(modifier = Modifier.weight(.8f).padding(16.dp), state = logicsScrollState) {
                    items(selectedTest.logics.size) {
                        CardListItem {
                            Text(selectedTest.logics[it].text)
                        }
                    }
                    item {
                        CardListItem {
                            TextButton(onClick = {
                                selectedTest.logics.add(LogicItem("Комментарий"))
                                scope.launch {
                                    logicsScrollState.scrollToItem(selectedTest.logics.size - 1)
                                }
                            }) {
                                Text("Добавить инструкцию")
                            }
                        }
                    }
                }
            }
        }
    }
}
