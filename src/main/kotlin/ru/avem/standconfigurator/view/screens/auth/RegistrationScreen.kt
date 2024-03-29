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
import ru.avem.standconfigurator.model.repos.UsersRepository
import ru.avem.standconfigurator.model.structs.User
import ru.avem.standconfigurator.view.keyEventNext
import ru.avem.standconfigurator.view.keyboardActionNext

class RegistrationScreen : Screen {
    @Composable
    override fun Content() {
        val localNavigator = LocalNavigator.currentOrThrow
        val focusManager = LocalFocusManager.current

        var name by remember { mutableStateOf(TextFieldValue()) }
        var login by remember { mutableStateOf(TextFieldValue()) }
        var password by remember { mutableStateOf(TextFieldValue()) }
        var confirmPassword by remember { mutableStateOf(TextFieldValue()) }

        var nameErrorState by remember { mutableStateOf(false) }
        var loginErrorState by remember { mutableStateOf(false) }
        var passwordErrorState by remember { mutableStateOf(false) }
        var confirmPasswordErrorState by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier.padding(16.dp).fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = MaterialTheme.colors.primary)) {
                    append("Р")
                }
                withStyle(style = SpanStyle(color = Color.Black)) {
                    append("егистрация")
                }
            }, fontSize = 30.sp)
            Spacer(Modifier.size(16.dp))
            OutlinedTextField(
                value = name,
                onValueChange = {
                    if (nameErrorState) {
                        nameErrorState = false
                    }
                    name = it
                },

                modifier = Modifier.focusTarget().onPreviewKeyEvent {
                    keyEventNext(it, focusManager)
                },
                isError = nameErrorState,
                label = {
                    Text(text = "ФИО*")
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = keyboardActionNext(focusManager)
            )
            if (nameErrorState) {
                Text(text = "Обязательно", color = MaterialTheme.colors.error)
            }
            Spacer(Modifier.size(16.dp))

            OutlinedTextField(
                value = login,
                onValueChange = {
                    if (loginErrorState) {
                        loginErrorState = false
                    }
                    login = it
                },

                modifier = Modifier.focusTarget().onPreviewKeyEvent {
                    keyEventNext(it, focusManager)
                },
                isError = loginErrorState,
                label = {
                    Text(text = "Логин*")
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = keyboardActionNext(focusManager)
            )
            if (loginErrorState) {
                Text(text = "Обязательно", color = MaterialTheme.colors.error)
            }

            Spacer(Modifier.size(16.dp))
            var passwordVisibility by remember { mutableStateOf(true) }
            OutlinedTextField(
                value = password,
                onValueChange = {
                    if (passwordErrorState) {
                        passwordErrorState = false
                    }
                    password = it
                },
                modifier = Modifier.focusTarget().onPreviewKeyEvent {
                    keyEventNext(it, focusManager)
                },
                label = {
                    Text(text = "Пароль*")
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
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = keyboardActionNext(focusManager),
                isError = passwordErrorState,
                visualTransformation = if (passwordVisibility) PasswordVisualTransformation() else VisualTransformation.None
            )
            if (passwordErrorState) {
                Text(text = "Обязательно", color = MaterialTheme.colors.error)
            }

            Spacer(Modifier.size(16.dp))
            var cPasswordVisibility by remember { mutableStateOf(true) }
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = {
                    if (confirmPasswordErrorState) {
                        confirmPasswordErrorState = false
                    }
                    confirmPassword = it
                },
                modifier = Modifier.focusTarget().onPreviewKeyEvent {
                    keyEventNext(it, focusManager)
                },
                isError = confirmPasswordErrorState,
                label = {
                    Text(text = "Повторите пароль*")
                },
                trailingIcon = {
                    IconButton(onClick = {
                        cPasswordVisibility = !cPasswordVisibility
                    }) {
                        Icon(
                            imageVector = if (cPasswordVisibility) Icons.Default.RemoveRedEye else Icons.Default.Clear,
                            contentDescription = "visibility",
                            tint = MaterialTheme.colors.primary
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = keyboardActionNext(focusManager),
                visualTransformation = if (cPasswordVisibility) PasswordVisualTransformation() else VisualTransformation.None
            )
            if (confirmPasswordErrorState) {
                val msg = when {
                    confirmPassword.text.isEmpty() -> "Обязательно"
                    confirmPassword.text != password.text -> "Пароли не совпадают"
                    else -> ""
                }
                Text(text = msg, color = MaterialTheme.colors.error)
            }
            Spacer(Modifier.size(16.dp))
            Button(
                onClick = {
                    when {
                        name.text.isEmpty() -> {
                            nameErrorState = true
                        }
                        login.text.isEmpty() -> {
                            loginErrorState = true
                        }
                        password.text.isEmpty() -> {
                            passwordErrorState = true
                        }
                        confirmPassword.text.isEmpty() -> {
                            confirmPasswordErrorState = true
                        }
                        confirmPassword.text != password.text -> {
                            confirmPasswordErrorState = true
                        }
                        else -> {
                            UsersRepository.add(
                                User(
                                    name = name.text,
                                    login = login.text,
                                    password = password.text
                                )
                            )
                            localNavigator.pop()
                        }
                    }
                },
                content = {
                    Text(text = "Зарегистрироваться", color = MaterialTheme.colors.surface)
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
            )
            Spacer(Modifier.size(16.dp))
            Row(horizontalArrangement = Arrangement.Center) {
                TextButton(onClick = {
                    localNavigator.push(LoginScreen())
                }) {
                    Text(text = "Назад", color = MaterialTheme.colors.primary)
                }
            }
        }
    }
}
