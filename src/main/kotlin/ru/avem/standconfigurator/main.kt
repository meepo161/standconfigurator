package ru.avem.standconfigurator

import androidx.compose.material.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import cafe.adriel.voyager.navigator.Navigator
import ru.avem.standconfigurator.model.ProjectRepository
import ru.avem.standconfigurator.model.UsersRepository
import ru.avem.standconfigurator.view.LoginScreen

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
        MaterialTheme(
            typography = craneTypography,
            colors = MaterialTheme.colors.copy(secondary = Color(0.38f, 0f, 0.93f))
        ) {
            Navigator(LoginScreen())
        }
    }
}
