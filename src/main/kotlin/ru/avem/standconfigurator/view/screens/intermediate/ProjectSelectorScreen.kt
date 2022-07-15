package ru.avem.standconfigurator.view.screens.intermediate

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import ru.avem.standconfigurator.model.structs.Project
import ru.avem.standconfigurator.model.structs.projectClasses
import ru.avem.standconfigurator.view.composables.ComboBox
import ru.avem.standconfigurator.view.composables.TableView
import ru.avem.standconfigurator.view.keyEventNext
import ru.avem.standconfigurator.view.keyboardActionNext
import ru.avem.standconfigurator.view.screens.project.ProjectScreen
import ru.avem.standconfigurator.viewmodel.ProjectsViewModel
import java.text.SimpleDateFormat

@Suppress("OPT_IN_IS_NOT_ENABLED")
class ProjectSelectorScreen : Screen {
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    @Preview
    override fun Content() {
        val localNavigator = LocalNavigator.currentOrThrow
        val focusManager = LocalFocusManager.current

        val scaffoldState = rememberScaffoldState()

        val pvm = ProjectsViewModel()

        var isNewProjectDialogVisible by remember { mutableStateOf(false) }

        var filterValue by remember { mutableStateOf("") }
        var filterValueErrorState by remember { mutableStateOf(false) }

        var name by remember { mutableStateOf(TextFieldValue("")) }
        var nameErrorState by remember { mutableStateOf(false) }

        var selectedProjectClass by remember { mutableStateOf(projectClasses.first()) }

        var currentProject by remember { mutableStateOf<Project?>(null) }

        @OptIn(ExperimentalMaterialApi::class)
        @Composable
        fun AddProjectDialog() {
            AlertDialog(
                modifier = Modifier.width(600.dp).padding(top = 16.dp, bottom = 16.dp),
                onDismissRequest = { },
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
                        IconButton(onClick = {
                            isNewProjectDialogVisible = false
                        }) {
                            Icon(Icons.Filled.Close, contentDescription = null)
                        }
                    }
                },
                buttons = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = {
                                pvm.add(
                                    Project(
                                        name = name.text,
                                        type = selectedProjectClass,
                                        createDate = SimpleDateFormat("dd.MM.yyyy").format(System.currentTimeMillis()),
                                        modificationDate = SimpleDateFormat("dd.MM.yyyy").format(System.currentTimeMillis()),
                                        author = MainModel.currentUser.name
                                    ),
                                )
                                isNewProjectDialogVisible = false
                            }) {
                            Text("Создать")
                        }
                    }
                },
                text = {
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
                                    if (nameErrorState) {
                                        nameErrorState = false
                                    }
                                    name = it
                                },
                                isError = nameErrorState,
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
                            ComboBox(
                                modifier = Modifier.fillMaxWidth().focusTarget().onPreviewKeyEvent {
                                    keyEventNext(it, focusManager)
                                },
                                initialValue = selectedProjectClass,
                                items = projectClasses,
                                onDismissState = {},
                                selectedValue = {
                                    selectedProjectClass = it
                                }
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
                                if (filterValueErrorState) {
                                    filterValueErrorState = false
                                }
                                filterValue = newText
                                currentProject = null
                            },
                            isError = filterValueErrorState,
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
                            Project::type,
                            Project::modificationDate,
                            Project::createDate,
                            Project::author,
                        ),
                        columnNames = listOf(
                            "Название",
                            "Тип",
                            "Дата изменения",
                            "Дата создания",
                            "Автор",
                        ),
                        onItemPrimaryPressed = {
                            localNavigator.push(ProjectScreen(pvm.getFilteredProjects(filterValue)[it]))
                        },
                        onItemSecondaryPressed = {
                            currentProject = pvm.getFilteredProjects(filterValue)[it]
                        },
                        contextMenuContent = {
                            DropdownMenuItem(onClick = {
                                localNavigator.push(ProjectScreen(currentProject!!))
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
