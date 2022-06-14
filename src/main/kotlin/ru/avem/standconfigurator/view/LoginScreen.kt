package ru.avem.standconfigurator.view

import androidx.compose.foundation.layout.*
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
import kotlinx.coroutines.DelicateCoroutinesApi

class LoginScreen : Screen {
    @OptIn(DelicateCoroutinesApi::class)
    @Composable
    override fun Content() {
        val localNavigator = LocalNavigator.currentOrThrow

        val login = remember { mutableStateOf(TextFieldValue()) }
        val loginErrorState = remember { mutableStateOf(false) }
        val passwordErrorState = remember { mutableStateOf(false) }
        val password = remember { mutableStateOf(TextFieldValue()) }

        Scaffold(
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
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
                        value = login.value,
                        onValueChange = {
                            if (loginErrorState.value) {
                                loginErrorState.value = false
                            }
                            login.value = it
                        },
                        isError = loginErrorState.value,
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text(text = "Введите логин*")
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
                        isError = passwordErrorState.value,
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text(text = "Введите пароль*")
                        },
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
                    Button(
                        onClick = {
                            when {
                                login.value.text.isEmpty() -> {
                                    loginErrorState.value = true
                                }
                                password.value.text.isEmpty() -> {
                                    passwordErrorState.value = true
                                }
                                else -> {
                                    localNavigator.push(MainScreen())
                                }
                            }

                        },
                        content = {
                            Text(text = "Вход", color = Color.White)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                    )
//                    Spacer(Modifier.size(16.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
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

