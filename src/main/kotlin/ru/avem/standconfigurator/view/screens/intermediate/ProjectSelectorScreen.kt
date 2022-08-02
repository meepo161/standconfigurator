package ru.avem.standconfigurator.view.screens.intermediate

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ru.avem.standconfigurator.model.MainModel
import ru.avem.standconfigurator.model.data.getProjectTypeByName
import ru.avem.standconfigurator.model.data.projectTypes
import ru.avem.standconfigurator.model.repos.ProjectRepository
import ru.avem.standconfigurator.model.structs.Project
import ru.avem.standconfigurator.view.composables.TableView
import ru.avem.standconfigurator.view.screens.project.ProjectScreen
import ru.avem.standconfigurator.viewmodel.ProjectsViewModel
import java.text.SimpleDateFormat

@Suppress("OPT_IN_IS_NOT_ENABLED")
class ProjectSelectorScreen : Screen {
    @Composable
    @Preview
    override fun Content() {
        val localNavigator = LocalNavigator.currentOrThrow

        val scaffoldState = rememberScaffoldState()

        val pvm = ProjectsViewModel()

        val isNewProjectDialogVisible = remember { mutableStateOf(false) }

        val isEditProjectDialogVisible = remember { mutableStateOf(false) }

        var filterValue by remember { mutableStateOf("") }
        var filterValueErrorState by remember { mutableStateOf(false) }

        var currentProject by remember { mutableStateOf<Project?>(null) }

        val isExpandedDropDownMenu = mutableStateOf(false)

        val nameNew = DialogRowData(
            name = "Введите название проекта",
            type = DialogRowType.TEXT,
            field = mutableStateOf(""),
            isVisible = mutableStateOf(true),
            errorState = mutableStateOf(false)
        )
        val selectedProjectTypeNew = DialogRowData(
            name = "Тип",
            type = DialogRowType.COMBO,
            variableField = projectTypes.map(Any::toString),
            field = mutableStateOf(projectTypes.first().toString()),
            isVisible = mutableStateOf(true),
            errorState = mutableStateOf(false),
        )

        val nameEdit = DialogRowData(
            name = "Введите название проекта",
            type = DialogRowType.TEXT,
            field = mutableStateOf(""),
            isVisible = mutableStateOf(true),
            errorState = mutableStateOf(false)
        )
        val typeEdit = DialogRowData(
            name = "Введите название проекта",
            type = DialogRowType.TEXT,
            field = mutableStateOf(""),
            isVisible = mutableStateOf(true),
            errorState = mutableStateOf(false)
        )

        fun checkCreateProjectErrors() { // TODO вынести проверку isCanEmpty в RowsAlertDialog
            nameNew.errorState.value =
                nameNew.field.value.isEmpty()
                        || ProjectRepository.projects.any { it.name == nameNew.field.value }
        }

        fun checkEditProjectErrors() {
            nameEdit.errorState.value =
                nameEdit.field.value.isEmpty()
                        || ProjectRepository.projects.any { it.name == nameEdit.field.value && it.name != currentProject?.name }
        }

        fun createProject() {
            pvm.add(
                Project(
                    name = nameNew.field.value,
                    projectTypeName = selectedProjectTypeNew.field.value,
                    createDate = SimpleDateFormat("dd.MM.yyyy").format(System.currentTimeMillis()),
                    modificationDate = SimpleDateFormat("dd.MM.yyyy").format(System.currentTimeMillis()),
                    author = MainModel.currentUser.name,
                    tests = mutableListOf(*getProjectTypeByName(selectedProjectTypeNew.field.value)!!.initialTests.map { it.copy() }
                        .toTypedArray()),
                    devices = mutableListOf(*getProjectTypeByName(selectedProjectTypeNew.field.value)!!.initialDevices.map { it.clone() }
                        .toTypedArray()),
                ),
            )
            isNewProjectDialogVisible.value = false
        }

        fun editProject() {
            currentProject?.name = nameEdit.field.value
            currentProject?.projectTypeName = typeEdit.field.value
            ProjectRepository.save()
            isEditProjectDialogVisible.value = false
        }

        Scaffold(
            scaffoldState = scaffoldState,
            content = {
                if (isNewProjectDialogVisible.value) {
                    RowsAlertDialog(
                        "Заполните поля",
                        listOf(nameNew, selectedProjectTypeNew),
                        isNewProjectDialogVisible,
                        "Создать",
                        ::checkCreateProjectErrors
                    ) {
                        if (!nameNew.errorState.value) {
                            createProject()
                        }
                    }
                    checkCreateProjectErrors()
                }
                if (isEditProjectDialogVisible.value) {
                    RowsAlertDialog(
                        "Заполните поля",
                        listOf(nameEdit, typeEdit),
                        isEditProjectDialogVisible,
                        "Сохранить",
                        ::checkEditProjectErrors
                    ) {
                        if (!nameEdit.errorState.value) {
                            editProject()
                        }
                    }
                    checkEditProjectErrors()
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
                                isNewProjectDialogVisible.value = true
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
                            Project::projectTypeName,
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
                                localNavigator.push(ProjectScreen(currentProject!!)) // TODO check
                            }) {
                                Text("Открыть")
                            }
                            DropdownMenuItem(onClick = {
                                isExpandedDropDownMenu.value = false
                                nameEdit.field.value = currentProject?.name ?: ""
                                typeEdit.field.value = currentProject?.projectTypeName ?: ""
                                isEditProjectDialogVisible.value = true
                            }) {
                                Text("Редактировать")
                            }
                            DropdownMenuItem(onClick = {
                                pvm.remove(currentProject!!)
                                currentProject = null
                            }) {
                                Text("Удалить")
                            }
                        },
                        isExpandedDropdownMenu = isExpandedDropDownMenu
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
