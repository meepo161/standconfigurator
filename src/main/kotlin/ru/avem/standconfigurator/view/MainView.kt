package ru.avem.standconfigurator.view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import ru.avem.standconfigurator.Devices

@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview
fun MainView() {
    MaterialTheme(typography = Typography(FontFamily.Monospace)) {
        Scaffold(
            topBar = { TopAppBar { TopBar() } },

            content = {
                Row(Modifier.fillMaxSize().padding(bottom = 60.dp)) {
                    Column(
                        modifier = Modifier.fillMaxHeight().weight(0.3f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Devices()
                        Column(
                            modifier = Modifier.background(color = Color(200, 200, 200))
                                .fillMaxHeight().fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text("MOCK2")
                        }
                    }

                    Column(
                        modifier = Modifier.background(color = Color(200, 200, 200))
                            .fillMaxHeight().fillMaxWidth().weight(0.3f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text("CENTER")
                    }

                    Column(
                        modifier = Modifier.background(color = Color(200, 200, 200))
                            .fillMaxHeight().fillMaxWidth().weight(0.3f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text("RIGHT")
                    }
                }
            },

            bottomBar = {
                BottomAppBar {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        Text(text = "Инфо")
                    }
                }
            }

        )
    }
}

@Composable
private fun TopBar() = Button({}) {
    Text("Файл")
}
