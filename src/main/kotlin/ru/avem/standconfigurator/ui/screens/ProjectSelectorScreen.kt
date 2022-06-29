package ru.avem.standconfigurator.ui.screens

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
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
import ru.avem.standconfigurator.model.MainModel
import ru.avem.standconfigurator.model.ProjectsViewModel
import ru.avem.standconfigurator.model.blob.Project
import ru.avem.standconfigurator.ui.keyEventNext
import ru.avem.standconfigurator.ui.keyboardActionNext
import ru.avem.standconfigurator.ui.composables.TableView
import java.text.SimpleDateFormat

@Suppress("OPT_IN_IS_NOT_ENABLED")
class ProjectSelectorScreen : Screen {
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    @Preview
    override fun Content() {
        val localNavigator = LocalNavigator.currentOrThrow
        val focusManager = LocalFocusManager.current
        val pvm = ProjectsViewModel()

        var isNewProjectDialogVisible by remember { mutableStateOf(false) }
        val scaffoldState = rememberScaffoldState()

        var name by remember { mutableStateOf(TextFieldValue("1")) }
        var projectErrorState by remember { mutableStateOf(false) }
        var templateErrorState by remember { mutableStateOf(false) }
        var template by remember { mutableStateOf(TextFieldValue("1")) }
        var filterValue by remember { mutableStateOf("") }
        var projectNameErrorState by remember { mutableStateOf(false) }
        var currentProject by remember { mutableStateOf<Project?>(null) }

        @OptIn(ExperimentalMaterialApi::class)
        @Composable
        fun AddProjectDialog() {
            AlertDialog(modifier = Modifier.width(600.dp).padding(16.dp),
                onDismissRequest = { isNewProjectDialogVisible = false },
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
                                    pvm.add(
                                        Project(
                                            name.text,
                                            SimpleDateFormat("dd.MM.yyyy").format(System.currentTimeMillis()),
                                            MainModel.currentUser.name
                                        )
                                    )
                                    isNewProjectDialogVisible = false
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
                                value = name,
                                onValueChange = {
                                    if (projectErrorState) {
                                        projectErrorState = false
                                    }
                                    name = it
                                },
                                isError = projectErrorState,
                                modifier = Modifier.fillMaxWidth().focusTarget().onPreviewKeyEvent {
                                    keyEventNext(it, focusManager)
                                },
                                label = {
                                    Text(text = "Введите название проекта*")
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
                                value = template,
                                onValueChange = {
                                    if (templateErrorState) {
                                        templateErrorState = false
                                    }
                                    template = it
                                },
                                isError = templateErrorState,
                                modifier = Modifier.fillMaxWidth().focusTarget().onPreviewKeyEvent {
                                    keyEventNext(it, focusManager)
                                },
                                label = {
                                    Text(text = "Введите шаблон*")
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
            content = {
                if (isNewProjectDialogVisible) {
                    AddProjectDialog()
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
                            value = filterValue,
                            onValueChange = { newText ->
                                if (projectNameErrorState) {
                                    projectNameErrorState = false
                                }
                                filterValue = newText
                                currentProject = null
                            },
                            isError = projectNameErrorState,
                            label = {
                                Text(text = "Фильтр")
                            },
                        )
                        Button(
                            modifier = Modifier.height(56.dp),
                            onClick = {
                                isNewProjectDialogVisible = true
                            }) {
                            Text("Новый")
                        }
                    }
                    Spacer(Modifier.size(16.dp))
                    TableView(
                        selectedItem = currentProject,
                        items = pvm.getFilteredProjects(filterValue),
                        columns = listOf(
                            Project::name,
                            Project::date,
                            Project::author,
                        ),
                        columnNames = listOf(
                            "Название",
                            "Дата",
                            "Автор",
                        ),
                        onItemPrimaryPressed = {
                            localNavigator.push(MainScreen(pvm.getFilteredProjects(filterValue)[it]))
                        },
                        onItemSecondaryPressed = {
                            currentProject = pvm.getFilteredProjects(filterValue)[it]
                        },
                        contextMenuContent = {
                            DropdownMenuItem(onClick = {
                                localNavigator.push(MainScreen(currentProject!!))
                            }) {
                                Text("Открыть")
                            }
                            DropdownMenuItem(onClick = {
                                pvm.remove(currentProject!!)
                                currentProject = null
                            }) {
                                Text("Удалить")
                            }
                        }
                    )
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
