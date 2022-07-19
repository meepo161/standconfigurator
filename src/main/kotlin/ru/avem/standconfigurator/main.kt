package ru.avem.standconfigurator

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import cafe.adriel.voyager.navigator.Navigator
import ru.avem.standconfigurator.model.MainModel
import ru.avem.standconfigurator.model.repos.ProjectRepository
import ru.avem.standconfigurator.model.repos.UsersRepository
import ru.avem.standconfigurator.view.craneTypography
import ru.avem.standconfigurator.view.screens.auth.LoginScreen

fun main() = application {
    UsersRepository.init()
    ProjectRepository.init()

    MainModel.isOpen = remember { mutableStateOf(true) }
    if (MainModel.isOpen.value) {
        Window(
            resizable = false,
            undecorated = true,
            state = rememberWindowState(placement = WindowPlacement.Maximized),
            onCloseRequest = ::exitApplication,
            title = "Конфигуратор промышленных стендов"
        ) {
            MaterialTheme(
                typography = craneTypography,
                colors = MaterialTheme.colors.copy(secondary = Color.Cyan)
            ) {
                Navigator(LoginScreen())
            }
        }
    }
}
