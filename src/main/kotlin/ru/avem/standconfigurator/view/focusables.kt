package ru.avem.standconfigurator.view

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Typography
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.unit.sp
import java.awt.Cursor

fun Modifier.cursorForHorizontalResize(): Modifier =
    this.pointerHoverIcon(PointerIcon(Cursor(Cursor.E_RESIZE_CURSOR)))

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
