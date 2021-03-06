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
import ru.avem.standconfigurator.model.repos.ProjectRepository
import ru.avem.standconfigurator.model.structs.LogicItem
import ru.avem.standconfigurator.model.structs.Project
import ru.avem.standconfigurator.model.structs.Test
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

        val scaffoldState = rememberScaffoldState()

        val currentProjectVM by remember { mutableStateOf(CurrentProjectViewModel(currentProject)) }

        var isProjectMenuExpanded by remember { mutableStateOf(false) }
        var isProtectionsMenuExpanded by remember { mutableStateOf(false) }

        var isAddLogicDialogVisible by remember { mutableStateOf(false) }
        var logicType by remember { mutableStateOf(TextFieldValue("??????????????????????")) }
        var logicsErrorState by remember { mutableStateOf(false) }

        var isAddTestDialogVisible by remember { mutableStateOf(false) }
        var testName by remember { mutableStateOf(TextFieldValue("????????")) }
        var testsErrorState by remember { mutableStateOf(false) }

        var comment by remember { mutableStateOf(TextFieldValue("")) }
        var commentErrorState by remember { mutableStateOf(false) }

        val logicsScrollState = rememberReorderableLazyListState(onMove = { from, to ->
            currentProjectVM.selectedTestLogics = currentProjectVM.selectedTestLogics.apply {
                add(to.index, removeAt(from.index))
            }
        })

        var testIdx by remember { mutableStateOf(0) }
        var isShowDropdownMenu by remember { mutableStateOf(false) }

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
                                append("??")
                            }
                            withStyle(style = SpanStyle(color = Color.Black)) {
                                append("?????????????? ?????? ????????????????????")
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
                                Text("??????????????")
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
                                    Text(text = "??????:")
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
                                    Text(text = "?????????? ??????????????????????")
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
                                append("??")
                            }
                            withStyle(style = SpanStyle(color = Color.Black)) {
                                append("???????????????? ????????")
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
                                    currentProjectVM.addTest(Test(testName.text))
                                    scope.launch {
//                                        testsScrollState.listState.scrollToItem(currentProjectVM.tests.size - 1)
                                    }
                                    currentProjectVM.selectLast()
                                    isAddTestDialogVisible = false
                                }) {
                                Text("??????????????")
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
                                    Text(text = "????????????????:")
                                },
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                                keyboardActions = keyboardActionNext(focusManager)
                            )
                        }
                    }
                })
        }

        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                TopAppBar(
                    title = {
                        Row {
                            Button(onClick = { isProjectMenuExpanded = true }) {
                                Text(text = "????????????")
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
                                    Text("??????????")
                                }

                                DropdownMenuItem(onClick = {
                                    ProjectRepository.save()
                                    scope.launch {
                                        scaffoldState.snackbarHostState.showSnackbar(
                                            message = "?????????????? ??????????????????"
                                        )
                                    }
                                }, modifier = Modifier.width(150.dp)) {
                                    Icon(imageVector = Icons.Default.Save, contentDescription = null)
                                    Spacer(modifier = Modifier.weight(1f))
                                    Text("??????????????????")
                                }

                                DropdownMenuItem(onClick = {
                                    localNavigator.push(LoginScreen())
                                }) {
                                    Icon(imageVector = Icons.Default.Logout, contentDescription = null)
                                    Spacer(modifier = Modifier.weight(1f))
                                    Text("??????????")
                                }

                                DropdownMenuItem(onClick = {
                                    MainModel.isOpen.value = false
                                }) {
                                    Icon(imageVector = Icons.Default.ExitToApp, contentDescription = null)
                                    Spacer(modifier = Modifier.weight(1f))
                                    Text("??????????")
                                }
                            }

                            Button(onClick = {
                                localNavigator.push(DevicesScreen(currentProject))

                            }) {
                                Text(text = "??????????????")
                            }

                            Button(onClick = { isProtectionsMenuExpanded = true }) {
                                Text(text = "????????????")
                            }
                            DropdownMenu(
                                expanded = isProtectionsMenuExpanded,
                                onDismissRequest = { isProtectionsMenuExpanded = false }) {

                                DropdownMenuItem(onClick = {
//                                    localNavigator.push(SoftAlarmsScreen()) todo ?????????????? ?????????? ??????????
                                }) {
                                    Icon(imageVector = Icons.Outlined.OnlinePrediction, contentDescription = null)
                                    Spacer(modifier = Modifier.weight(1f))
                                    Text("????????-????????????")
                                }

                                DropdownMenuItem(onClick = {
//                                    localNavigator.push(HardAlarmsScreen()) todo ?????????????? ?????????? ??????????
                                }) {
                                    Icon(imageVector = Icons.Default.OfflineBolt, contentDescription = null)
                                    Spacer(modifier = Modifier.weight(1f))
                                    Text("????????-????????????")
                                }
                            }
                            Button(onClick = {
                            }) {
                                Text(text = currentProjectVM.selectedTest.value?.text ?: "")
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
            if (isAddTestDialogVisible) {
                AddTestDialog()
            }

            Row(modifier = Modifier.fillMaxWidth().padding(bottom = 56.dp)) {
                Column(modifier = Modifier.weight(.1f).padding(16.dp)) {
                    LazyList(
                        modifier = Modifier.weight(.9f),
                        items = currentProjectVM.tests,
                        selectedItem = currentProjectVM.selectedTest.value,
                        onNextListItem = {
                            if (it == testIdx) {
                                DropdownMenu(
                                    expanded = isShowDropdownMenu,
                                    onDismissRequest = { isShowDropdownMenu = false }) {
                                    DropdownMenuItem(onClick = {
                                        currentProjectVM.removeAt(testIdx)
                                        testIdx = 0
                                        currentProjectVM.clear()
                                        currentProjectVM.selectFirst()
                                        isShowDropdownMenu = false
                                    }) {
                                        Text("??????????????")
                                    }
                                }
                            }
                        }
                    ) { it, isPrimary, _testIdx ->
                        testIdx = _testIdx
                        currentProjectVM.selectTest(it)
                        isShowDropdownMenu = !isPrimary
                    }

                    CardListItem(modifier = Modifier.clickable {
                        isAddTestDialogVisible = true
                    }) {
                        Text("???????????????? ????????")
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
                        Text("???????????????? ????????????????????")
                    }
                }
            }
        }
    }
}
