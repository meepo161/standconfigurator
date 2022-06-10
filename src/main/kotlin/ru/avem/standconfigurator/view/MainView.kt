package ru.avem.standconfigurator.view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.splitpane.ExperimentalSplitPaneApi
import org.jetbrains.compose.splitpane.HorizontalSplitPane
import org.jetbrains.compose.splitpane.VerticalSplitPane
import org.jetbrains.compose.splitpane.rememberSplitPaneState
import ru.avem.standconfigurator.Devices
import java.awt.Cursor

@OptIn(ExperimentalSplitPaneApi::class)
@Composable
@Preview
fun MainView() {
    MaterialTheme(typography = Typography(FontFamily.Monospace)) {
        Scaffold(
            topBar = {
                TopAppBar {
                    Button({}) {
                        Text("Файл")
                    }
                }
            },

            content = {
                val splitpaneState = rememberSplitPaneState(0.3f)
                val splitpaneState2 = rememberSplitPaneState(0.7f)
                val verticalSplitpaneState = rememberSplitPaneState(0.5f)

                var textCenter by remember { mutableStateOf("") }
                var textRight by remember { mutableStateOf("") }

                HorizontalSplitPane(splitPaneState = splitpaneState) {
                    first() {
                        VerticalSplitPane(
                            splitPaneState = verticalSplitpaneState,
                            modifier = Modifier.background(Color.Gray)
                        ) {
                            first {
                                Tests {
                                    textCenter = it
                                }
                            }
                            second {
                                Devices {
                                    textRight = it
                                }
                            }
                            splitter {
                                visiblePart {
                                    Box(
                                        Modifier
                                            .height(1.dp)
                                            .fillMaxWidth()
                                    )
                                }
                                handle {
                                    Box(
                                        Modifier
                                            .markAsHandle()
                                            .cursorForVerticalResize()
                                            .height(8.dp)
                                            .fillMaxWidth()
                                    ) {
                                        Box(Modifier.background(Color.Blue).height(1.dp).fillMaxWidth()) {
                                        }
                                    }
                                }
                            }
                        }
                    }
                    second {
                        HorizontalSplitPane(splitPaneState = splitpaneState2) {
                            first {
                                Column(
                                    modifier = Modifier.fillMaxHeight().fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Box(contentAlignment = Alignment.TopCenter) {
                                        Text(textCenter)
                                    }
                                }
                            }
                            second {
                                Column(
                                    modifier = Modifier.fillMaxHeight().fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Box(contentAlignment = Alignment.TopCenter) {
                                        Text(textRight)
                                    }
                                }
                            }
                            splitter {
                                visiblePart {
                                    Box(
                                        Modifier
                                            .width(1.dp)
                                            .fillMaxHeight()
                                            .background(MaterialTheme.colors.background)
                                    )
                                }
                                handle {
                                    Box(
                                        Modifier
                                            .markAsHandle()
                                            .cursorForHorizontalResize()
                                            .width(16.dp)
                                            .fillMaxHeight(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Box(Modifier.background(Color.Blue).width(1.dp).fillMaxHeight()) {
                                        }
                                    }
                                }
                            }
                        }
                    }
                    splitter {
                        visiblePart {
                            Box(
                                Modifier
                                    .width(1.dp)
                                    .fillMaxHeight()
                                    .background(MaterialTheme.colors.background)
                            )
                        }
                        handle {
                            Box(
                                Modifier
                                    .markAsHandle()
                                    .cursorForHorizontalResize()
                                    .width(16.dp)
                                    .fillMaxHeight(),
                                contentAlignment = Alignment.Center
                            ) {
                                Box(Modifier.background(Color.Blue).width(1.dp).fillMaxHeight()) {
                                }
                            }
                        }
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

@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.cursorForHorizontalResize(): Modifier =
    this.pointerHoverIcon(PointerIcon(Cursor(Cursor.E_RESIZE_CURSOR)))

@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.cursorForVerticalResize(): Modifier =
    this.pointerHoverIcon(PointerIcon(Cursor(Cursor.N_RESIZE_CURSOR)))
