package ru.avem.standconfigurator.view

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.awt.Cursor

@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview
fun MainView() {
    val checkedState by remember { mutableStateOf(true) }
    val stateFlagList = mutableStateListOf<Boolean>()
    for (i in 0..50) {
        stateFlagList.add(false)
    }
    val stateFlag by rememberSaveable {
        mutableStateOf(stateFlagList)
    }

    val panelState = rememberSaveable { PanelState() }

    val animatedSize = if (panelState.splitter.isResizing) {
        if (panelState.isExpanded) panelState.expandedSize else panelState.collapsedSize
    } else {
        animateDpAsState(
            if (panelState.isExpanded) panelState.expandedSize else panelState.collapsedSize,
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


//                Row(
//                    modifier = Modifier.fillMaxSize().padding(bottom = 60.dp),
//                    horizontalArrangement = Arrangement.spacedBy(4.dp),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {

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
                            modifier = Modifier.fillMaxHeight().fillMaxWidth()/*.weight(0.2f)*/,
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


                    Box {

                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize().weight(0.7f),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Box(
                                    modifier = Modifier.background(color = Color(200, 200, 200))
                                        .fillMaxHeight().fillMaxWidth().weight(0.6f),
                                    contentAlignment = Alignment.Center
                                ) {

                                    Text("CENTER")
                                }
                            }
                            Column(
                                modifier = Modifier.fillMaxSize().weight(0.3f),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Box(
                                    modifier = Modifier.background(color = Color(200, 200, 200))
                                        .fillMaxHeight().fillMaxWidth().weight(0.6f),
                                    contentAlignment = Alignment.Center
                                ) {

                                    Text("MOCK2")
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

class SplitterState {
    var isResizing by mutableStateOf(false)
    var isResizeEnabled by mutableStateOf(true)
}

private class PanelState {
    val collapsedSize = 24.dp
    var expandedSize by mutableStateOf(300.dp)
    val expandedSizeMin = 90.dp
    var isExpanded by mutableStateOf(true)
    val splitter = SplitterState()
}

@Composable
private fun ResizablePanel(
    modifier: Modifier,
    state: PanelState,
    content: @Composable () -> Unit,
) {
    val alpha by animateFloatAsState(if (state.isExpanded) 1f else 0f, SpringSpec(stiffness = Spring.StiffnessLow))

    Box(modifier) {
        Box(Modifier.fillMaxSize().graphicsLayer(alpha = alpha)) {
            content()
        }

        Icon(
            if (state.isExpanded) Icons.Default.ArrowBack else Icons.Default.ArrowForward,
            contentDescription = if (state.isExpanded) "Collapse" else "Expand",
            tint = LocalContentColor.current,
            modifier = Modifier
                .padding(top = 4.dp)
                .width(24.dp)
                .clickable {
                    state.isExpanded = !state.isExpanded
                }
                .padding(4.dp)
                .align(Alignment.TopEnd)
        )
    }
}

@Composable
fun VerticalSplittable(
    modifier: Modifier,
    splitterState: SplitterState,
    onResize: (delta: Dp) -> Unit,
    children: @Composable () -> Unit
) = Layout({
    children()
    VerticalSplitter(splitterState, onResize)
}, modifier, measurePolicy = { measurables, constraints ->
//    require(measurables.size == 3)

    val firstPlaceable = measurables[0].measure(constraints.copy(minWidth = 0))
    val secondWidth = constraints.maxWidth - firstPlaceable.width
    val secondPlaceable = measurables[1].measure(
        Constraints(
            minWidth = secondWidth,
            maxWidth = secondWidth,
            minHeight = constraints.maxHeight,
            maxHeight = constraints.maxHeight
        )
    )
    val splitterPlaceable = measurables[2].measure(constraints)
    layout(constraints.maxWidth, constraints.maxHeight) {
        firstPlaceable.place(0, 0)
        secondPlaceable.place(firstPlaceable.width, 0)
        splitterPlaceable.place(firstPlaceable.width, 0)
    }
})

@Composable
fun VerticalSplitter(
    splitterState: SplitterState,
    onResize: (delta: Dp) -> Unit,
    color: Color = Color.Blue
) = Box {
    val density = LocalDensity.current
    Box(
        Modifier
            .width(8.dp)
            .fillMaxHeight()
            .run {
                if (splitterState.isResizeEnabled) {
                    this.draggable(
                        state = rememberDraggableState {
                            with(density) {
                                onResize(it.toDp())
                            }
                        },
                        orientation = Orientation.Horizontal,
                        startDragImmediately = true,
                        onDragStarted = { splitterState.isResizing = true },
                        onDragStopped = { splitterState.isResizing = false }
                    ).cursorForHorizontalResize()
                } else {
                    this
                }
            }
    )

    Box(
        Modifier
            .width(1.dp)
            .fillMaxHeight()
            .background(color)
    )
}

@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.cursorForHorizontalResize(): Modifier =
    this.pointerHoverIcon(PointerIcon(Cursor(Cursor.E_RESIZE_CURSOR)))