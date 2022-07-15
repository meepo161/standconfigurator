package ru.avem.standconfigurator.view.screens.project

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.OfflineBolt
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
import ru.avem.standconfigurator.model.structs.LogicItem
import ru.avem.standconfigurator.model.structs.Project
import ru.avem.standconfigurator.view.composables.CardListItem
import ru.avem.standconfigurator.view.composables.LazyList
import ru.avem.standconfigurator.view.keyEventNext
import ru.avem.standconfigurator.view.keyboardActionNext
import ru.avem.standconfigurator.view.screens.auth.LoginScreen
import ru.avem.standconfigurator.viewmodel.CurrentProjectViewModel

class ProjectScreen(private val currentProject: Project) : Screen {
    @Composable
    override fun Content() {
        val localNavigator = LocalNavigator.currentOrThrow
        val focusManager = LocalFocusManager.current
        val scope = rememberCoroutineScope()

        val currentProjectVM by remember { mutableStateOf(CurrentProjectViewModel(currentProject)) }

        var isProjectMenuExpanded by remember { mutableStateOf(false) }
        var isProtectionsMenuExpanded by remember { mutableStateOf(false) }

        var isAddLogicDialogVisible by remember { mutableStateOf(false) }
        var logicType by remember { mutableStateOf(TextFieldValue("Комментарий")) }
        var logicsErrorState by remember { mutableStateOf(false) }

        var comment by remember { mutableStateOf(TextFieldValue("")) }
        var commentErrorState by remember { mutableStateOf(false) }

        val logicsScrollState = rememberReorderableLazyListState(onMove = { from, to ->
            currentProjectVM.selectedTestLogics = currentProjectVM.selectedTestLogics.apply {
                add(to.index, removeAt(from.index))
            }
        })

        @OptIn(ExperimentalMaterialApi::class)
        @Composable
        fun AddLogicDialog() {
            AlertDialog(modifier = Modifier.width(600.dp).padding(16.dp),
                onDismissRequest = { isAddLogicDialogVisible = false },
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = buildAnnotatedString {
                            withStyle(style = SpanStyle(color = MaterialTheme.colors.primary)) {
                                append("В")
                            }
                            withStyle(style = SpanStyle(color = Color.Black)) {
                                append("ыберите тип инструкции")
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
                                    currentProjectVM.addLogic(LogicItem(comment.text))
                                    scope.launch {
                                        logicsScrollState.listState.scrollToItem(currentProjectVM.selectedTestLogics.size - 1)
                                    }
                                    isAddLogicDialogVisible = false
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
                                value = logicType,
                                onValueChange = {
                                    if (logicsErrorState) {
                                        logicsErrorState = false
                                    }
                                    logicType = it
                                },
                                isError = logicsErrorState,
                                modifier = Modifier.fillMaxWidth().focusTarget().onPreviewKeyEvent {
                                    keyEventNext(it, focusManager)
                                },
                                label = {
                                    Text(text = "Тип:")
                                },
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                                keyboardActions = keyboardActionNext(focusManager)
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            OutlinedTextField(
                                singleLine = true,
                                value = comment,
                                onValueChange = {
                                    if (commentErrorState) {
                                        commentErrorState = false
                                    }
                                    comment = it
                                },
                                isError = commentErrorState,
                                modifier = Modifier.fillMaxWidth().focusTarget().onPreviewKeyEvent {
                                    keyEventNext(it, focusManager)
                                },
                                label = {
                                    Text(text = "Текст комментария")
                                },
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                                keyboardActions = keyboardActionNext(focusManager)
                            )
                        }
                    }
                })
        }

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
//                                    localNavigator.push(SoftAlarmsScreen()) todo сделать экран защит
                                }) {
                                    Icon(imageVector = Icons.Outlined.OnlinePrediction, contentDescription = null)
                                    Spacer(modifier = Modifier.weight(1f))
                                    Text("Софт-аварии")
                                }

                                DropdownMenuItem(onClick = {
//                                    localNavigator.push(HardAlarmsScreen()) todo сделать экран защит
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
            if (isAddLogicDialogVisible) {
                AddLogicDialog()
            }

            Row(modifier = Modifier.fillMaxWidth().padding(bottom = 56.dp)) {
                Column(modifier = Modifier.weight(.1f).padding(16.dp)) {
                    LazyList(
                        modifier = Modifier.weight(.9f),
                        items = currentProject.tests
                    ) {
                        currentProjectVM.selectTest(it)
                    }
                    CardListItem(modifier = Modifier.clickable {

                    }) {
                        Text("Добавить опыт")
                    }
                }
                Column(modifier = Modifier.weight(.8f).padding(16.dp)) {
                    LazyColumn(
                        modifier = Modifier
                            .weight(.9f)
                            .reorderable(logicsScrollState)
                            .detectReorderAfterLongPress(logicsScrollState),
                        state = logicsScrollState.listState//logicsScrollState
                    ) {
                        items(currentProjectVM.selectedTestLogics.size) {
                            ReorderableItem(logicsScrollState, index = it, key = null) { isDragging ->
                                val padding = animateDpAsState(if (isDragging) 16.dp else 0.dp)
                                CardListItem(
                                    modifier = Modifier
                                        .padding(padding.value)
                                        .hoverable(interactionSource = remember { MutableInteractionSource() })
                                ) {
                                    Text(currentProjectVM.selectedTestLogics[it].text)
                                }
                            }
                        }
                    }
                    CardListItem(modifier = Modifier.clickable {
                        isAddLogicDialogVisible = true
                    }) {
                        Text("Добавить инструкцию")
                    }
                }
            }
        }
    }
}
