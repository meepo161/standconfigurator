package ru.avem.standconfigurator.view

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import java.awt.Cursor

@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.cursorForHorizontalResize(): Modifier =
    this.pointerHoverIcon(PointerIcon(Cursor(Cursor.E_RESIZE_CURSOR)))

@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.cursorForVerticalResize(): Modifier =
    this.pointerHoverIcon(PointerIcon(Cursor(Cursor.N_RESIZE_CURSOR)))

fun keyboardActionNext(focusManager: FocusManager) = KeyboardActions(
    onNext = { focusManager.moveFocus(FocusDirection.Next) }
)

@OptIn(ExperimentalComposeUiApi::class)
fun keyEventNext(
    it: KeyEvent,
    focusManager: FocusManager
) = if (it.key == Key.Tab) {
    focusManager.moveFocus(FocusDirection.Next)
    true
} else {
    false
}