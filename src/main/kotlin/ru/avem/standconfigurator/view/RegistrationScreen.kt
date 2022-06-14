package ru.avem.standconfigurator.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Face
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

class RegistrationScreen : Screen {
    @Composable
    override fun Content() {
        val localNavigator = LocalNavigator.currentOrThrow

        val name = remember {
            mutableStateOf(TextFieldValue())
        }
        val login = remember { mutableStateOf(TextFieldValue()) }
        val password = remember { mutableStateOf(TextFieldValue()) }
        val confirmPassword = remember { mutableStateOf(TextFieldValue()) }

        val nameErrorState = remember { mutableStateOf(false) }
        val loginErrorState = remember { mutableStateOf(false) }
        val passwordErrorState = remember { mutableStateOf(false) }
        val confirmPasswordErrorState = remember { mutableStateOf(false) }
        val scrollState = rememberScrollState()

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
                value = name.value,
                onValueChange = {
                    if (nameErrorState.value) {
                        nameErrorState.value = false
                    }
                    name.value = it
                },

                modifier = Modifier.fillMaxWidth(),
                isError = nameErrorState.value,
                label = {
                    Text(text = "ФИО*")
                },
            )
            if (nameErrorState.value) {
                Text(text = "Обязательно", color = MaterialTheme.colors.primary)
            }
            Spacer(Modifier.size(16.dp))

            OutlinedTextField(
                value = login.value,
                onValueChange = {
                    if (loginErrorState.value) {
                        loginErrorState.value = false
                    }
                    login.value = it
                },

                modifier = Modifier.fillMaxWidth(),
                isError = loginErrorState.value,
                label = {
                    Text(text = "Логин*")
                },
            )
            if (loginErrorState.value) {
                Text(text = "Обязательно", color = MaterialTheme.colors.primary)
            }

            Spacer(Modifier.size(16.dp))
            val passwordVisibility = remember { mutableStateOf(true) }
            OutlinedTextField(
                value = password.value,
                onValueChange = {
                    if (passwordErrorState.value) {
                        passwordErrorState.value = false
                    }
                    password.value = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(text = "Пароль*")
                },
                isError = passwordErrorState.value,
                trailingIcon = {
                    IconButton(onClick = {
                        passwordVisibility.value = !passwordVisibility.value
                    }) {
                        Icon(
                            imageVector = if (passwordVisibility.value) Icons.Default.Face else Icons.Default.Clear,
                            contentDescription = "visibility",
                            tint = MaterialTheme.colors.primary
                        )
                    }
                },
                visualTransformation = if (passwordVisibility.value) PasswordVisualTransformation() else VisualTransformation.None
            )
            if (passwordErrorState.value) {
                Text(text = "Обязательно", color = MaterialTheme.colors.primary)
            }

            Spacer(Modifier.size(16.dp))
            val cPasswordVisibility = remember { mutableStateOf(true) }
            OutlinedTextField(
                value = confirmPassword.value,
                onValueChange = {
                    if (confirmPasswordErrorState.value) {
                        confirmPasswordErrorState.value = false
                    }
                    confirmPassword.value = it
                },
                modifier = Modifier.fillMaxWidth(),
                isError = confirmPasswordErrorState.value,
                label = {
                    Text(text = "Повторите пароль*")
                },
                trailingIcon = {
                    IconButton(onClick = {
                        cPasswordVisibility.value = !cPasswordVisibility.value
                    }) {
                        Icon(
                            imageVector = if (cPasswordVisibility.value) Icons.Default.Face else Icons.Default.Clear,
                            contentDescription = "visibility",
                            tint = MaterialTheme.colors.primary
                        )
                    }
                },
                visualTransformation = if (cPasswordVisibility.value) PasswordVisualTransformation() else VisualTransformation.None
            )
            if (confirmPasswordErrorState.value) {
                val msg = when {
                    confirmPassword.value.text.isEmpty() -> {
                        "Обязательно"
                    }
                    confirmPassword.value.text != password.value.text -> {
                        "Пароли не совпадают"
                    }
                    else -> {
                        ""
                    }
                }
                Text(text = msg, color = MaterialTheme.colors.primary)
            }
            Spacer(Modifier.size(16.dp))
            Button(
                onClick = {
                    when {
                        name.value.text.isEmpty() -> {
                            nameErrorState.value = true
                        }
                        login.value.text.isEmpty() -> {
                            loginErrorState.value = true
                        }
                        password.value.text.isEmpty() -> {
                            passwordErrorState.value = true
                        }
                        confirmPassword.value.text.isEmpty() -> {
                            confirmPasswordErrorState.value = true
                        }
                        confirmPassword.value.text != password.value.text -> {
                            confirmPasswordErrorState.value = true
                        }
                        else -> {
                            localNavigator.push(LoginScreen())
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
    }}
