package ru.avem.standconfigurator.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlusOne
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.launch
import ru.avem.standconfigurator.model.blob.LogicItem
import ru.avem.standconfigurator.model.blob.Project
import ru.avem.standconfigurator.model.devices.Device
import ru.avem.standconfigurator.ui.composables.DevicesList
import ru.avem.standconfigurator.ui.composables.LogicListItem
import ru.avem.standconfigurator.ui.composables.TestsList
import ru.avem.standconfigurator.ui.devices.avem4.AVEM4Configurator
import ru.avem.standconfigurator.ui.devices.latr.LatrConfigurator

class MainScreen(private val currentProject: Project) : Screen {
    @Composable
    override fun Content() {
        val localNavigator = LocalNavigator.currentOrThrow

        var isTopBarMenuExpanded by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()
        var selectedDevice: Device? by remember { mutableStateOf(null) }
        val rtlDrawer = rememberDrawerState(DrawerValue.Closed)
        val logicItemsList = remember { mutableStateListOf<LogicItem>(*emptyArray()) }

        val logicItemsScrollState = rememberLazyListState()
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Row {
                            Button(onClick = { isTopBarMenuExpanded = true }) {
                                Text("Файл")
                            }
                            DropdownMenu(
                                expanded = isTopBarMenuExpanded,
                                onDismissRequest = { isTopBarMenuExpanded = false }) {
                                DropdownMenuItem(onClick = {
                                }) {
                                    Column {
                                        Button(
                                            onClick = {
                                                scope.launch {
                                                    isTopBarMenuExpanded = false
                                                    localNavigator.pop()
                                                }
                                            }) {
                                            Text("Новый")
                                        }
                                        Button(
                                            onClick = {
                                                localNavigator.push(LoginScreen())
                                            }) {
                                            Text("Выйти")
                                        }
                                    }
                                }
                            }
                        }
                    },
                    actions = {
                        if (rtlDrawer.isOpen) {
                            IconButton(
                                onClick = {
                                    scope.launch {
                                        rtlDrawer.close()
                                    }
                                }
                            ) {
                                Icon(Icons.Filled.ArrowBack, contentDescription = null)
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
            ModalDrawer(
                gesturesEnabled = false,
                drawerContent = {
                    Column(
                        modifier = Modifier.fillMaxHeight().fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        when (selectedDevice?.type) {
                            "ЛАТР" -> LatrConfigurator()
                            "АВЭМ4" -> AVEM4Configurator()
                        }
                    }
                },
                drawerState = rtlDrawer
            ) {
                Row(modifier = Modifier.fillMaxWidth().padding(bottom = 56.dp)) {
                    Column(modifier = Modifier.weight(.1f)) {
                        TestsList(modifier = Modifier.fillMaxHeight(.5f)) {
//                            textCenter = it
                        }
                        Divider()
                        DevicesList(modifier = Modifier.fillMaxHeight(.5f)) {
                            selectedDevice = it
                            scope.launch {
                                rtlDrawer.open()
                            }
                        }
                    }
                    LazyColumn(modifier = Modifier.weight(.8f).padding(16.dp), state = logicItemsScrollState) {
                        items(logicItemsList.size) {
                            LogicListItem {
                                Text(logicItemsList[it].mockedParameter)
                            }
                        }
                        item {
                            LogicListItem {
                                IconButton(onClick = {
                                    logicItemsList.add(LogicItem("NIGGER"))
                                    scope.launch {
                                        logicItemsScrollState.scrollToItem(logicItemsList.size - 1)
                                    }
                                }) {
                                    Icon(Icons.Filled.PlusOne, contentDescription = null)
                                }
                            }
                        }
                    }
                    Column(modifier = Modifier.weight(.1f)) {
                        TestsList(modifier = Modifier.fillMaxHeight(.5f)) {
//                            textCenter = it
                        }
                        Divider()
                        DevicesList(modifier = Modifier.fillMaxHeight(.5f)) {
                            selectedDevice = it
                        }
                    }
                }
            }
        }
    }
}
