package ru.avem.standconfigurator

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import cafe.adriel.voyager.navigator.Navigator
import ru.avem.standconfigurator.view.LoginScreen

fun main() =
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Конфигуратор стендов"
        ) {
            Navigator(LoginScreen())
        }
    }
