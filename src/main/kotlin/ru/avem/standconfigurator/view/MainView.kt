package ru.avem.standconfigurator.view

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import ru.avem.standconfigurator.view.util.PanelState
import ru.avem.standconfigurator.view.util.ResizablePanel
import ru.avem.standconfigurator.view.util.VerticalSplittable
import ru.avem.standconfigurator.view.util.VerticalSplittableRight

@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview
fun MainView() {
    val stateFlagList = mutableStateListOf<Boolean>()
    for (i in 0..50) {
        stateFlagList.add(false)
    }
    val stateFlag by rememberSaveable {
        mutableStateOf(stateFlagList)
    }

    val panelState = rememberSaveable { PanelState() }
    val panelState2 = rememberSaveable { PanelState() }

    val animatedSize = if (panelState.splitter.isResizing) {
        if (panelState.isExpanded) panelState.expandedSize else panelState.collapsedSize
    } else {
        animateDpAsState(
            if (panelState.isExpanded) panelState.expandedSize else panelState.collapsedSize,
            SpringSpec(stiffness = Spring.StiffnessLow)
        ).value
    }

    val animatedSize2 = if (panelState2.splitter.isResizing) {
        if (panelState2.isExpanded) panelState2.expandedSize else panelState2.collapsedSize
    } else {
        animateDpAsState(
            if (panelState2.isExpanded) panelState2.expandedSize else panelState2.collapsedSize,
            SpringSpec(stiffness = Spring.StiffnessLow)
        ).value
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
                VerticalSplittable(
                    Modifier.fillMaxSize().padding(bottom = 60.dp),
                    panelState.splitter,
                    onResize = {
                        panelState.expandedSize =
                            (panelState.expandedSize + it).coerceAtLeast(panelState.expandedSizeMin)
                    }
                ) {
                    ResizablePanel(Modifier.width(animatedSize).fillMaxHeight(), panelState) {
                        Column(
                            modifier = Modifier.fillMaxHeight()/*.weight(0.2f)*/,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            LazyColumn(
                                modifier = Modifier/*.background(color = Color(200, 200, 200))*/
                                    .fillMaxHeight(0.5f).fillMaxWidth(),
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.Center
                            ) {
                                items((1..50).toList()) {
                                    Box {
                                        TextButton(
                                            modifier = Modifier.fillMaxWidth().fillMaxHeight().fillMaxSize()
                                                .background(if (stateFlag[it]) Color.Cyan else Color.White),
                                            onClick = {
                                                for (i in 0..50) {
                                                    stateFlag[i] = false
                                                }
                                                stateFlag[it] = !stateFlag[it]
                                            }) {
                                            Text(
                                                modifier = Modifier
                                                    .background(if (stateFlag[it]) Color.Cyan else Color.White),
                                                text = "Прибор $it"
                                            )
                                        }
                                    }
                                }
                            }
                            Column(
                                modifier = Modifier.background(color = Color(200, 200, 200))
                                    .fillMaxHeight().fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text("MOCK2")
                            }
                        }
                    }

                    Box() {
                        VerticalSplittable(
                            Modifier.fillMaxSize(),
                            panelState2.splitter,
                            onResize = {
                                panelState2.expandedSize =
                                    (panelState2.expandedSize + it).coerceAtLeast(panelState2.expandedSizeMin)
                            }
                        ) {
                            ResizablePanel(
                                Modifier.width(animatedSize2).fillMaxHeight(),
                                panelState2, false
                            ) {
                                Column(
                                    Modifier.fillMaxHeight(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Box(
                                        modifier = Modifier.background(color = Color(200, 200, 200))
                                            .fillMaxHeight().fillMaxWidth(),
                                        contentAlignment = Alignment.Center
                                    ) {

                                        Text("ResizablePanel2")
                                    }
                                }
                            }
                            Box {
                                Column(
                                    modifier = Modifier,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Box(
                                        modifier = Modifier.background(color = Color(200, 200, 200))
                                            .fillMaxHeight(),
                                        contentAlignment = Alignment.Center
                                    ) {

                                        Text("CENTER")
                                    }
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
