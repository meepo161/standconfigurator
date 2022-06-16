package ru.avem.standconfigurator.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Face
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import ru.avem.standconfigurator.model.UserModel
import ru.avem.standconfigurator.model.UsersRepository

class RegistrationScreen : Screen {
    @Composable
    override fun Content() {
        var localNavigator = LocalNavigator.currentOrThrow
        val focusManager = LocalFocusManager.current

        var name by remember {
            mutableStateOf(TextFieldValue())
        }
        var login by remember { mutableStateOf(TextFieldValue()) }
        var password by remember { mutableStateOf(TextFieldValue()) }
        var confirmPassword by remember { mutableStateOf(TextFieldValue()) }

        var nameErrorState by remember { mutableStateOf(false) }
        var loginErrorState by remember { mutableStateOf(false) }
        var passwordErrorState by remember { mutableStateOf(false) }
        var confirmPasswordErrorState by remember { mutableStateOf(false) }
        var scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Center,
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

                modifier = Modifier.fillMaxWidth().focusTarget().onPreviewKeyEvent {
                    keyEventNext(it, focusManager)},
                isError = nameErrorState,
                label = {
                    Text(text = "ФИО*")
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = keyboardActionNext(focusManager)
            )
            if (nameErrorState) {
                Text(text = "Обязательно", color = MaterialTheme.colors.primary)
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

                modifier = Modifier.fillMaxWidth().focusTarget().onPreviewKeyEvent {
                    keyEventNext(it, focusManager)},
                isError = loginErrorState,
                label = {
                    Text(text = "Логин*")
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
                value = password,
                onValueChange = {
                    if (passwordErrorState) {
                        passwordErrorState = false
                    }
                    password = it
                },
                modifier = Modifier.fillMaxWidth().focusTarget().onPreviewKeyEvent {
                    keyEventNext(it, focusManager)},
                label = {
                    Text(text = "Пароль*")
                },
                trailingIcon = {
                    IconButton(onClick = {
                        passwordVisibility = !passwordVisibility
                    }) {
                        Icon(
                            imageVector = if (passwordVisibility) Icons.Default.Face else Icons.Default.Clear,
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
                Text(text = "Обязательно", color = MaterialTheme.colors.primary)
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
                modifier = Modifier.fillMaxWidth().focusTarget().onPreviewKeyEvent {
                    keyEventNext(it, focusManager)},
                isError = confirmPasswordErrorState,
                label = {
                    Text(text = "Повторите пароль*")
                },
                trailingIcon = {
                    IconButton(onClick = {
                        cPasswordVisibility = !cPasswordVisibility
                    }) {
                        Icon(
                            imageVector = if (cPasswordVisibility) Icons.Default.Face else Icons.Default.Clear,
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
                Text(text = msg, color = MaterialTheme.colors.primary)
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
                            UsersRepository.addUser(
                                UserModel(
                                    name = name.text,
                                    login = login.text,
                                    password = password.text
                                )
                            )
                        }
                    }
                },
                content = {
                    Text(text = "Зарегистрироваться", color = Color.White)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
            )
            Spacer(Modifier.size(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                TextButton(onClick = {
                    localNavigator.push(LoginScreen())
                }) {
                    Text(text = "Назад", color = MaterialTheme.colors.primary)
                }
            }
        }
    }
}
