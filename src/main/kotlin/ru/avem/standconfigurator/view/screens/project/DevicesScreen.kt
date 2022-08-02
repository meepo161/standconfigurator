package ru.avem.standconfigurator.view.screens.project

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.launch
import ru.avem.standconfigurator.model.MainModel
import ru.avem.standconfigurator.model.ProjectModel
import ru.avem.standconfigurator.model.ProjectModel.dvm
import ru.avem.standconfigurator.model.structs.Project
import ru.avem.standconfigurator.view.composables.CardListItem
import ru.avem.standconfigurator.view.composables.LazyList
import ru.avem.standconfigurator.view.composables.devices.DeviceConfigurator
import ru.avem.standconfigurator.viewmodel.DevicesViewModel

class DevicesScreen(private val currentProject: Project) : Screen {
    @Composable
    override fun Content() {
        val localNavigator = LocalNavigator.currentOrThrow
        val scope = rememberCoroutineScope()

        val scrollState = rememberLazyListState()

        var isShowDropdownMenu by remember { mutableStateOf(false) }

        var selectedDeviceIdx by remember { mutableStateOf(0) }
        val rtlDrawer = rememberDrawerState(DrawerValue.Closed)

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        if (!rtlDrawer.isOpen) {
                            Text("К проекту")
                        } else {
                            Text("Назад")
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            if (!rtlDrawer.isOpen) {
                                localNavigator.pop()
                            } else {
                                scope.launch {
                                    rtlDrawer.close()
                                }
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Назад"
                            )
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
                        if (!(selectedDeviceIdx < 0 || selectedDeviceIdx >= dvm.stateDevices.size)) {
                            DeviceConfigurator(dvm.stateDevices[selectedDeviceIdx])
                        }
                    }
                },
                drawerState = rtlDrawer
            ) {
                Row(modifier = Modifier.fillMaxWidth().padding(bottom = 56.dp)) {
                    Column(modifier = Modifier.weight(.1f)) {
                        LazyList(
                            modifier = Modifier.fillMaxHeight(),
                            items = MainModel.allDevices.values.toList(),
                            selectedItem = MainModel.allDevices.values.first()
                        ) { it, isPrimary, _ ->
                            if (isPrimary) {
                                dvm.add(it.name)
                                scope.launch {
                                    scrollState.scrollToItem(dvm.stateDevices.size - 1)
                                }
                            }
                        }
                    }
                    LazyColumn(modifier = Modifier.weight(.8f).padding(16.dp), state = scrollState) {
                        items(dvm.stateDevices.size) {
                            CardListItem(
                                item = dvm.stateDevices[it],
                                selectedItem = dvm.stateDevices[selectedDeviceIdx],
                                onNextListItem = {
                                    DropdownMenu(
                                        expanded = isShowDropdownMenu,
                                        onDismissRequest = { isShowDropdownMenu = false }) {
                                        DropdownMenuItem(onClick = {
                                            scope.launch {
                                                isShowDropdownMenu = false
                                                rtlDrawer.open()
                                            }
                                        }) {
                                            Text("Открыть")
                                        }
                                        DropdownMenuItem(onClick = {
                                            dvm.removeAt(selectedDeviceIdx)
                                            selectedDeviceIdx = 0
                                            isShowDropdownMenu = false
                                        }) {
                                            Text("Удалить")
                                        }
                                    }
                                },
                                onClick = { item, isPrimary ->
                                    selectedDeviceIdx = dvm.stateDevices.indexOf(item)

                                    if (isPrimary) {
                                        scope.launch {
                                            rtlDrawer.open()
                                        }
                                    } else {
                                        isShowDropdownMenu = true
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
