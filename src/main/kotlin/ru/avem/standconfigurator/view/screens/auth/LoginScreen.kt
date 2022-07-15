package ru.avem.standconfigurator.view.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.RemoveRedEye
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ru.avem.standconfigurator.model.MainModel
import ru.avem.standconfigurator.model.repos.UsersRepository
import ru.avem.standconfigurator.model.structs.User
import ru.avem.standconfigurator.view.keyEventNext
import ru.avem.standconfigurator.view.keyboardActionNext
import ru.avem.standconfigurator.view.screens.intermediate.ProjectSelectorScreen

class LoginScreen : Screen {
    @Composable
    override fun Content() {
        val localNavigator = LocalNavigator.currentOrThrow
        val focusManager = LocalFocusManager.current

        var login by remember { mutableStateOf(TextFieldValue("admin")) }
        var loginErrorState by remember { mutableStateOf(false) }
        var passwordErrorState by remember { mutableStateOf(false) }
        var password by remember { mutableStateOf(TextFieldValue("avem")) }

        val authorize: (User) -> Unit = { user ->
            MainModel.currentUser = user
            localNavigator.push(ProjectSelectorScreen())
        }

        Scaffold(
            content = {
                Column(
                    modifier = Modifier.padding(16.dp).fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = MaterialTheme.colors.primary)) {
                            append("В")
                        }
                        withStyle(style = SpanStyle(color = Color.Black)) {
                            append("ход")
                        }
                    }, fontSize = 30.sp)
                    Spacer(Modifier.size(16.dp))
                    OutlinedTextField(
                        singleLine = true,
                        value = login,
                        onValueChange = {
                            if (loginErrorState) {
                                loginErrorState = false
                            }
                            login = it
                        },
                        isError = loginErrorState,
                        modifier = Modifier.focusTarget().onPreviewKeyEvent {
                            keyEventNext(it, focusManager)
                        },
                        label = {
                            Text(text = "Введите логин*")
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = keyboardActionNext(focusManager)
                    )
                    if (loginErrorState) {
                        Text(text = "Обязательно", color = MaterialTheme.colors.primary)
                    }
                    Spacer(Modifier.size(16.dp))
                    var passwordVisibility by remember { mutableStateOf(true) }
                    OutlinedTextField(
                        singleLine = true,
                        value = password,
                        onValueChange = {
                            if (passwordErrorState) {
                                passwordErrorState = false
                            }
                            password = it
                        },
                        isError = passwordErrorState,
                        modifier = Modifier.focusTarget().onPreviewKeyEvent {
                            keyEventNext(it, focusManager)
                        },
                        label = {
                            Text(text = "Введите пароль*")
                        },
                        trailingIcon = {
                            IconButton(onClick = {
                                passwordVisibility = !passwordVisibility
                            }) {
                                Icon(
                                    imageVector = if (passwordVisibility) Icons.Default.RemoveRedEye else Icons.Default.Clear,
                                    contentDescription = "visibility",
                                    tint = MaterialTheme.colors.primary
                                )
                            }
                        },
                        visualTransformation = if (passwordVisibility) PasswordVisualTransformation() else VisualTransformation.None,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = keyboardActionNext(focusManager)
                    )
                    if (passwordErrorState) {
                        Text(text = "Обязательно", color = MaterialTheme.colors.primary)
                    }
                    Spacer(Modifier.size(16.dp))
                    Button(
                        onClick = {
                            when {
                                login.text.isEmpty() -> {
                                    loginErrorState = true
                                }
                                password.text.isEmpty() -> {
                                    passwordErrorState = true
                                }
                                else -> {
                                    if (login.text == "admin" && password.text == "avem") {
                                        authorize(User("ADMIN"))
                                    } else {
                                        val currentUser = UsersRepository.users.firstOrNull { user ->
                                            user.login == login.text && user.password == password.text
                                        }
                                        if (currentUser != null) {
                                            authorize(currentUser)
                                        } else {
                                            loginErrorState = true
                                            passwordErrorState = true
                                        }
                                    }
                                }
                            }

                        },
                        content = {
                            Text(text = "Вход", color = Color.White)
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                    )
                    Row(horizontalArrangement = Arrangement.Center) {
                        TextButton(onClick = {
                            localNavigator.push(RegistrationScreen())
                        }) {
                            Text(text = "Регистрация", color = MaterialTheme.colors.primary)
                        }
                    }
                }
            })
    }
}
