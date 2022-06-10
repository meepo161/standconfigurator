package ru.avem.standconfigurator

import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ru.avem.standconfigurator.view.MainView

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Конфигуратор стендов"
    ) {
        MainView()
    }
}