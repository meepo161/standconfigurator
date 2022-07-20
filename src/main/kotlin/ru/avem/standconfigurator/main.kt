package ru.avem.standconfigurator

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.FadeTransition
import cafe.adriel.voyager.transitions.ScreenTransition
import cafe.adriel.voyager.transitions.SlideTransition
import ru.avem.standconfigurator.model.MainModel
import ru.avem.standconfigurator.model.repos.ProjectRepository
import ru.avem.standconfigurator.model.repos.UsersRepository
import ru.avem.standconfigurator.view.craneTypography
import ru.avem.standconfigurator.view.screens.auth.LoginScreen

@OptIn(ExperimentalAnimationApi::class)
fun main() = application {
    UsersRepository.init()
    ProjectRepository.init()

    MainModel.isOpen = remember { mutableStateOf(true) }
    if (MainModel.isOpen.value) {
        Window(
//            resizable = false,
//            undecorated = true,
            state = rememberWindowState(size = DpSize(1400.dp, 700.dp),/*placement = WindowPlacement.Maximized*/),
            onCloseRequest = ::exitApplication,
            title = "Конфигуратор промышленных стендов"
        ) {
            MaterialTheme(
                typography = craneTypography,
                colors = MaterialTheme.colors.copy(secondary = Color.Cyan)
            ) {
                Navigator(LoginScreen()) {
                    SlideTransition(it)
                }
            }
        }
    }
}
