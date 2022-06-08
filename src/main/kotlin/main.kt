import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    var isVisible by remember { mutableStateOf(true) }
    var text by remember { mutableStateOf("Hello, World!") }

    Window(
        onCloseRequest = { isVisible = false },
        visible = isVisible,
        title = "Counter",
    ) {
        MaterialTheme(typography = Typography(FontFamily.Monospace)) {
            Scaffold(
                topBar = {
                    TopAppBar {
                        Button({}) {
                            Text("Файл")
                        }
                    }
                }, content = {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(bottom = 60.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.fillMaxHeight().weight(0.1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Column(
                                    modifier = Modifier.padding(4.dp).background(color = Color(250, 250, 250)).fillMaxHeight()
                                        .fillMaxWidth()
                                        .weight(0.5f),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Box(
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(text = "MOCK1")
                                    }
                                }
                                Column(
                                    modifier = Modifier.padding(4.dp).background(color = Color(250, 250, 250)).fillMaxHeight()
                                        .fillMaxWidth()
                                        .weight(0.5f),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text("MOCK2")
                                }
                            }

                            Column(
                                modifier = Modifier.padding(4.dp).background(color = Color(250, 250, 250)).fillMaxHeight()
                                    .weight(0.8f),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text("CENTER")
                            }
                            Column(
                                modifier = Modifier.padding(4.dp).background(color = Color(250, 250, 250)).fillMaxHeight()
                                    .weight(0.2f),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text("MOCK2")
                            }
                        }
                    }
                },
                bottomBar = {
                    BottomAppBar {
                        Box(
                            modifier = Modifier.padding(end = 4.dp).fillMaxWidth().weight(0.05f),
                            contentAlignment = Alignment.BottomEnd
                        ) {
                            Text(text = "Инфо")
                        }
                    }
                }
            )
        }
    }
}