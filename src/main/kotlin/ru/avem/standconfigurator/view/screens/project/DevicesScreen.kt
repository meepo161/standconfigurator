package ru.avem.standconfigurator.view.screens.project

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.mouseClickable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.isSecondaryPressed
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.launch
import ru.avem.standconfigurator.model.MainModel
import ru.avem.standconfigurator.model.structs.Project
import ru.avem.standconfigurator.view.composables.CardListItem
import ru.avem.standconfigurator.view.composables.LazyList
import ru.avem.standconfigurator.view.devices.DeviceConfiguratorWidget
import ru.avem.standconfigurator.viewmodel.DevicesViewModel

class DevicesScreen(private val currentProject: Project) : Screen {
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val localNavigator = LocalNavigator.currentOrThrow
        val scope = rememberCoroutineScope()

        val dvm = DevicesViewModel(currentProject.devices)

        val scrollState = rememberLazyListState()

        var isShowDropdownMenu by remember { mutableStateOf(false) }

        var deviceIdx by remember { mutableStateOf(0) }
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
                        if (!(deviceIdx < 0 || deviceIdx >= dvm.stateDevices.size)) {
                            DeviceConfiguratorWidget(dvm.stateDevices[deviceIdx].text)
                        }
                    }
                },
                drawerState = rtlDrawer
            ) {
                Row(modifier = Modifier.fillMaxWidth().padding(bottom = 56.dp)) {
                    Column(modifier = Modifier.weight(.1f)) {
                        LazyList(
                            modifier = Modifier.fillMaxHeight(),
                            items = MainModel.allDevices
                        ) {
                            dvm.add(it)
                            scope.launch {
                                scrollState.scrollToItem(dvm.stateDevices.size - 1)
                            }
                        }
                    }
                    LazyColumn(modifier = Modifier.weight(.8f).padding(16.dp), state = scrollState) {
                        items(dvm.stateDevices.size) {
                            CardListItem {
                                TextButton(modifier = Modifier.fillMaxWidth().mouseClickable(
                                    onClick = {
                                        if (buttons.isSecondaryPressed) {
                                            deviceIdx = it
                                            isShowDropdownMenu = true
                                        }
                                    }
                                ), onClick = {
                                    deviceIdx = it
                                    scope.launch {
                                        rtlDrawer.open()
                                    }
                                }) {
                                    Text(dvm.stateDevices[it].text)
                                }

                                if (deviceIdx == it) {
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
                                            dvm.removeAt(deviceIdx)
                                            deviceIdx = 0
                                            isShowDropdownMenu = false
                                        }) {
                                            Text("Удалить")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
