package ru.avem.standconfigurator.view.screens.project

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.OfflineBolt
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.outlined.OnlinePrediction
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.launch
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable
import ru.avem.standconfigurator.model.MainModel
import ru.avem.standconfigurator.model.ProjectModel
import ru.avem.standconfigurator.model.ProjectModel.currentProject
import ru.avem.standconfigurator.model.ProjectModel.currentProjectVM
import ru.avem.standconfigurator.model.ProjectModel.dvm
import ru.avem.standconfigurator.model.repos.ProjectRepository
import ru.avem.standconfigurator.model.structs.LogicItem
import ru.avem.standconfigurator.model.structs.Project
import ru.avem.standconfigurator.model.structs.Test
import ru.avem.standconfigurator.view.composables.CardListItem
import ru.avem.standconfigurator.view.composables.LazyList
import ru.avem.standconfigurator.view.composables.LogicItemView
import ru.avem.standconfigurator.view.keyEventNext
import ru.avem.standconfigurator.view.keyboardActionNext
import ru.avem.standconfigurator.viewmodel.DevicesViewModel
import ru.avem.standconfigurator.viewmodel.ProjectViewModel

class ProjectScreen(currentProject: Project) : Screen {
    init {
        ProjectModel.currentProject = currentProject
    }

    @Composable
    override fun Content() {
        val localNavigator = LocalNavigator.currentOrThrow
        val focusManager = LocalFocusManager.current
        val scope = rememberCoroutineScope()

        val scaffoldState = rememberScaffoldState()

        currentProjectVM = remember { mutableStateOf(ProjectViewModel(currentProject)) }
        dvm = DevicesViewModel(currentProject.devices)

        var isProjectMenuExpanded by remember { mutableStateOf(false) }
        var isProtectionsMenuExpanded by remember { mutableStateOf(false) }

        var isAddTestDialogVisible by remember { mutableStateOf(false) }
        var testName by remember { mutableStateOf(TextFieldValue("")) }
        var testsErrorState by remember { mutableStateOf(false) }
        var selectTestIdx by remember { mutableStateOf(0) }
        var isShowTestDropdownMenu by remember { mutableStateOf(false) }

        var isEditLogicDialogVisible by remember { mutableStateOf(false) }
        val logicsScrollState = rememberReorderableLazyListState(onMove = { from, to ->
            currentProjectVM.value.selectedTestLogics = currentProjectVM.value.selectedTestLogics.apply {
                add(to.index, removeAt(from.index))
            }
        })

        var selectedLogicIdx by remember { mutableStateOf(0) }
        var isShowLogicsDropdownMenu by remember { mutableStateOf(false) }
        var isNewLogic by remember { mutableStateOf(true) }

        val logicItemValue by remember { mutableStateOf(LogicItem()) }

        val testsScrollState = rememberLazyListState()

        @OptIn(ExperimentalMaterialApi::class)
        @Composable
        fun AddTestDialog() {
            AlertDialog(modifier = Modifier.width(600.dp).padding(16.dp),
                onDismissRequest = { isAddTestDialogVisible = false },
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = buildAnnotatedString {
                            withStyle(style = SpanStyle(color = MaterialTheme.colors.primary)) {
                                append("З")
                            }
                            withStyle(style = SpanStyle(color = Color.Black)) {
                                append("аполните поля")
                            }
                        }, fontSize = 30.sp)
                    }
                }, buttons = {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = {
                                    currentProjectVM.value.addTest(Test(testName.text))
                                    currentProjectVM.value.selectLast()
                                    scope.launch {
                                        testsScrollState.scrollToItem(currentProjectVM.value.selectedTestIdx)
                                    }
                                    isAddTestDialogVisible = false
                                }) {
                                Text("Создать")
                            }
                        }
                    }
                }, text = {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            modifier = Modifier.height(4.dp),
                            text = "",
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            OutlinedTextField(
                                singleLine = true,
                                value = testName,
                                onValueChange = {
                                    if (testsErrorState) {
                                        testsErrorState = false
                                    }
                                    testName = it
                                },
                                isError = testsErrorState,
                                modifier = Modifier.fillMaxWidth().focusTarget().onPreviewKeyEvent {
                                    keyEventNext(it, focusManager)
                                },
                                label = {
                                    Text(text = "Название:")
                                },
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                                keyboardActions = keyboardActionNext(focusManager)
                            )
                        }
                    }
                })
        }

        @OptIn(ExperimentalMaterialApi::class)
        @Composable
        fun AddLogicDialog() {
            AlertDialog(modifier = Modifier.width(600.dp).padding(16.dp),
                onDismissRequest = { isEditLogicDialogVisible = false },
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = buildAnnotatedString {
                            withStyle(style = SpanStyle(color = MaterialTheme.colors.primary)) {
                                append("З")
                            }
                            withStyle(style = SpanStyle(color = Color.Black)) {
                                append("аполните поля")
                            }
                        }, fontSize = 30.sp)
                    }
                }, buttons = {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = {
                                    if (isNewLogic) {
                                        currentProjectVM.value.addLogic(logicItemValue.copy())
                                    } else {
                                        currentProjectVM.value.editLogic(selectedLogicIdx, logicItemValue)
                                        isNewLogic = true
                                    }
                                    scope.launch {
                                        logicsScrollState.listState.scrollToItem(currentProjectVM.value.selectedTestLogics.size - 1)
                                    }
                                    isEditLogicDialogVisible = false
                                }) {
                                Text(if (isNewLogic) "Создать" else "Изменить")
                            }
                        }
                    }
                }, text = {
                    LogicItemView(logicItemValue)
                })
        }

        Scaffold(
            scaffoldState = scaffoldState,
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
                                    ProjectRepository.save()
                                    scope.launch {
                                        scaffoldState.snackbarHostState.showSnackbar(
                                            message = "Успешно сохранено"
                                        )
                                    }
                                }, modifier = Modifier.width(250.dp)) {
                                    Icon(imageVector = Icons.Default.Save, contentDescription = null)
                                    Spacer(modifier = Modifier.weight(1f))
                                    Text("Сохранить")
                                }

                                DropdownMenuItem(onClick = {
                                    // TODO добавить окно выбора места сохранения
                                    ProjectRepository.save()
                                    scope.launch {
                                        scaffoldState.snackbarHostState.showSnackbar(
                                            message = "Успешно сохранено"
                                        )
                                    }
                                }, modifier = Modifier.width(250.dp)) {
                                    Icon(imageVector = Icons.Default.Save, contentDescription = null)
                                    Spacer(modifier = Modifier.weight(1f))
                                    Text("Сохранить как...")
                                }

                                DropdownMenuItem(onClick = {
                                    localNavigator.popUntilRoot()
                                }, modifier = Modifier.width(250.dp)) {
                                    Icon(imageVector = Icons.Default.Logout, contentDescription = null)
                                    Spacer(modifier = Modifier.weight(1f))
                                    Text("Сменить пользователя")
                                }

                                DropdownMenuItem(onClick = {
                                    MainModel.isOpen.value = false
                                }, modifier = Modifier.width(250.dp)) {
                                    Icon(imageVector = Icons.Default.ExitToApp, contentDescription = null)
                                    Spacer(modifier = Modifier.weight(1f))
                                    Text("Выход")
                                }
                            }

                            Button(onClick = {
                                localNavigator.push(DevicesScreen())
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
//                                    localNavigator.push(SoftAlarmsScreen()) TODO сделать экран защит
                                }) {
                                    Icon(imageVector = Icons.Outlined.OnlinePrediction, contentDescription = null)
                                    Spacer(modifier = Modifier.weight(1f))
                                    Text("Софт-аварии")
                                }

                                DropdownMenuItem(onClick = {
//                                    localNavigator.push(HardAlarmsScreen()) TODO сделать экран защит
                                }) {
                                    Icon(imageVector = Icons.Default.OfflineBolt, contentDescription = null)
                                    Spacer(modifier = Modifier.weight(1f))
                                    Text("Хард-аварии")
                                }
                            }

                            Button(onClick = {
//                                localNavigator.push(ViewConstructorScreen()) TODO сделать экран конструктора представлений
                            }) {
                                Text(text = "Конструктор представления")
                            }

                            Button(onClick = {
//                                localNavigator.push(ProtocolConstructorScreen()) TODO сделать экран конструктора протокола
                            }) {
                                Text(text = "Конструктор протокола")
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
            if (isAddTestDialogVisible) {
                AddTestDialog()
            }
            if (isEditLogicDialogVisible) {
                AddLogicDialog()
            }

            Row(modifier = Modifier.fillMaxWidth().padding(bottom = 56.dp)) {
                Column(modifier = Modifier.weight(.1f).padding(16.dp)) {
                    LazyList(
                        modifier = Modifier.weight(.9f),
                        state = testsScrollState,
                        items = currentProjectVM.value.tests,
                        selectedItem = currentProjectVM.value.selectedTest.value,
                        onNextListItem = {
                            if (it == selectTestIdx) {
                                DropdownMenu(
                                    expanded = isShowTestDropdownMenu,
                                    onDismissRequest = { isShowTestDropdownMenu = false }) {
                                    DropdownMenuItem(onClick = {
                                        currentProjectVM.value.removeAt(selectTestIdx)
                                        selectTestIdx = 0
                                        currentProjectVM.value.clear()
                                        currentProjectVM.value.selectFirst()
                                        isShowTestDropdownMenu = false
                                    }) {
                                        Text("Удалить")
                                    }
                                }
                            }
                        }
                    ) { it, isPrimary, _testIdx ->
                        selectTestIdx = _testIdx
                        currentProjectVM.value.selectTest(it)
                        isShowTestDropdownMenu = !isPrimary
                    }

                    Card(elevation = 4.dp, modifier = Modifier.clickable {
                        isAddTestDialogVisible = true
                    }.padding(8.dp).height(52.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(8.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Добавить опыт")
                        }
                    }
                }
                Column(modifier = Modifier.weight(.8f).padding(16.dp)) {
                    LazyColumn(
                        modifier = Modifier
                            .weight(.9f)
                            .reorderable(logicsScrollState)
                            .detectReorderAfterLongPress(logicsScrollState),
                        state = logicsScrollState.listState
                    ) {
                        repeat(currentProjectVM.value.selectedTestLogics.size) {
                            item() {
                                ReorderableItem(logicsScrollState, index = it, key = null) { isDragging ->
                                    val padding = animateDpAsState(if (isDragging) 16.dp else 0.dp)
                                    CardListItem(
                                        modifier = Modifier
                                            .padding(padding.value)
                                            .hoverable(interactionSource = remember { MutableInteractionSource() }),
                                        item = currentProjectVM.value.selectedTestLogics[it],
                                        selectedItem = currentProjectVM.value.selectedTestLogics[selectedLogicIdx],
                                        onNextListItem = {
                                            DropdownMenu(
                                                expanded = isShowLogicsDropdownMenu,
                                                onDismissRequest = { isShowLogicsDropdownMenu = false }) {
                                                DropdownMenuItem(onClick = {
                                                    isEditLogicDialogVisible = true
                                                    isNewLogic = false
                                                }) {
                                                    Text("Редактировать")
                                                }
                                                DropdownMenuItem(onClick = {
                                                    currentProjectVM.value.removeAtLogic(selectedLogicIdx)
                                                    selectedLogicIdx = 0
                                                    isShowLogicsDropdownMenu = false
                                                }) {
                                                    Text("Удалить")
                                                }
                                            }
                                        },
                                        onClick = { item, isPrimary ->
                                            selectedLogicIdx = currentProjectVM.value.selectedTestLogics.indexOf(item)
                                            isShowLogicsDropdownMenu = !isPrimary
                                        }
                                    )
                                }
                            }
                        }
                    }
                    Card(elevation = 4.dp, modifier = Modifier.clickable {
                        isEditLogicDialogVisible = true
                    }.padding(8.dp).height(52.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(8.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Добавить инструкцию")
                        }
                    }
                }
            }
        }
    }
}
