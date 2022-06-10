package ru.avem.standconfigurator.view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.splitpane.ExperimentalSplitPaneApi
import org.jetbrains.compose.splitpane.HorizontalSplitPane
import org.jetbrains.compose.splitpane.rememberSplitPaneState

@OptIn(ExperimentalSplitPaneApi::class)
@Composable
@Preview
fun MainView() {
    val stateFlagList = mutableStateListOf<Boolean>()
    for (i in 0..50) {
        stateFlagList.add(false)
    }

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
                HorizontalSplitPane(splitPaneState = splitpaneState) {
                    first {
                        Text("LEFT SIDE")
                    }
                    second {
                        HorizontalSplitPane(splitPaneState = splitpaneState2) {
                            first {
                                Text("CENTER")
                            }
                            second {
                                Text("RIGHT SIDE")
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
                                            .background(SolidColor(Color.Gray), alpha = 0.50f)
                                            .width(9.dp)
                                            .fillMaxHeight()
                                    )
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
                                    .background(SolidColor(Color.Gray), alpha = 0.50f)
                                    .width(9.dp)
                                    .fillMaxHeight()
                            )
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
