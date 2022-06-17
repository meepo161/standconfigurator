package ru.avem.standconfigurator

import androidx.compose.material.MaterialTheme
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import cafe.adriel.voyager.navigator.Navigator
import ru.avem.standconfigurator.model.ProjectRepository
import ru.avem.standconfigurator.model.UsersRepository
import ru.avem.standconfigurator.view.LoginScreen
import ru.avem.standconfigurator.view.craneTypography

fun main() = application {
    UsersRepository.init()
    ProjectRepository.init()
    Window(
        resizable = false,
        undecorated = true,
        state = rememberWindowState(placement = WindowPlacement.Maximized),
        onCloseRequest = ::exitApplication,
        title = "Конфигуратор стендов"
    ) {
        MaterialTheme(typography = craneTypography) {
            Navigator(LoginScreen())
        }
    }
}
