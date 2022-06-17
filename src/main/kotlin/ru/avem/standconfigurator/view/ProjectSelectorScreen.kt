package ru.avem.standconfigurator.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.DelicateCoroutinesApi
import org.jetbrains.compose.splitpane.ExperimentalSplitPaneApi
import ru.avem.standconfigurator.model.MainModel
import ru.avem.standconfigurator.model.ProjectModel
import ru.avem.standconfigurator.model.ProjectRepository
import java.text.SimpleDateFormat

@OptIn(ExperimentalSplitPaneApi::class)
class ProjectSelectorScreen : Screen {
    @OptIn(DelicateCoroutinesApi::class, ExperimentalMaterialApi::class)
    @Composable
    @Preview
    override fun Content() {
        val localNavigator = LocalNavigator.currentOrThrow
        val focusManager = LocalFocusManager.current

        var isNewProjectDialogVisible by remember { mutableStateOf(false) }
        val scaffoldState = rememberScaffoldState()
        val scope = rememberCoroutineScope()

        var name by remember { mutableStateOf(TextFieldValue("1")) }
        var search by remember { mutableStateOf(TextFieldValue("")) }
        var projectErrorState by remember { mutableStateOf(false) }
        var templateErrorState by remember { mutableStateOf(false) }
        var template by remember { mutableStateOf(TextFieldValue("1")) }
        val projectName = remember { mutableStateOf(TextFieldValue()) }
        val projectNameErrorState = remember { mutableStateOf(false) }

        Scaffold(
            scaffoldState = scaffoldState,

            content = {
                if (isNewProjectDialogVisible) {
                    AnimatedVisibility(isNewProjectDialogVisible) {
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
                                                ProjectRepository.addProject(
                                                    ProjectModel(
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
                            value = projectName.value,
                            onValueChange = {
                                if (projectNameErrorState.value) {
                                    projectNameErrorState.value = false
                                }
                                projectName.value = it
                            },
                            isError = projectNameErrorState.value,
                            label = {
                                Text(text = "Поиск")
                            },
                        )
                        Button(
                            modifier = Modifier.height(56.dp),
                            onClick = {
                                isNewProjectDialogVisible = true
                            }) {
                            Text("Новый")
                        }
                        Button(
                            modifier = Modifier.height(56.dp),
                            onClick = {
                                localNavigator.push(MainScreen())
                            }) {
                            Text("Открыть")
                        }
                        Button(
                            modifier = Modifier.height(56.dp),
                            onClick = {
                                ProjectRepository.removeProject(MainModel.currentProjectIndex)
                                projectNameErrorState.value = false
                                projectNameErrorState.value = true //TODO перерисовывать нормально
                            }) {
                            Text("Удалить")
                        }
                    }
                    Spacer(Modifier.size(16.dp))
                    TableView(
                        ProjectRepository.projects,
                        listOf(
                            ProjectModel::name,
                            ProjectModel::date,
                            ProjectModel::author,
                        ),
                        listOf(
                            "Название",
                            "Дата",
                            "Автор",
                        )
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
