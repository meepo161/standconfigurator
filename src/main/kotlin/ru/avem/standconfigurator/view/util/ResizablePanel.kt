package ru.avem.standconfigurator.view.util

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

class PanelState {
    val collapsedSize = 24.dp
    var expandedSize by mutableStateOf(300.dp)
    val expandedSizeMin = 90.dp
    var isExpanded by mutableStateOf(true)
    val splitter = SplitterState()
}

@Composable
fun ResizablePanel(
    modifier: Modifier,
    state: PanelState,
    leftSide: Boolean = true,
    content: @Composable () -> Unit
) {
    val alpha by animateFloatAsState(if (state.isExpanded) 1f else 0f, SpringSpec(stiffness = Spring.StiffnessLow))

    Box(modifier) {
        Box(Modifier.fillMaxSize().graphicsLayer(alpha = alpha)) {
            content()
        }

        Icon(
            if (state.isExpanded && leftSide || !state.isExpanded && !leftSide) {
                Icons.Default.ArrowBack
            } else if (!state.isExpanded && leftSide || state.isExpanded && !leftSide) {
                Icons.Default.ArrowForward
            } else {
                Icons.Default.Warning
            },
            contentDescription =
            if (state.isExpanded) "Collapse" else "Expand",
            tint = LocalContentColor.current,
            modifier = if (leftSide) {
                Modifier
                    .padding(top = 4.dp)
                    .width(24.dp)
                    .clickable {
                        state.isExpanded = !state.isExpanded
                    }
                    .padding(4.dp)
                    .align(Alignment.TopEnd)
            } else {
                Modifier
                    .padding(top = 4.dp)
                    .width(24.dp)
                    .clickable {
                        state.isExpanded = !state.isExpanded
                    }
                    .padding(4.dp)
                    .align(Alignment.TopStart)
            }
        )
    }
}
