package ru.avem.standconfigurator.view

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import java.awt.Cursor

@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.cursorForHorizontalResize(): Modifier =
    this.pointerHoverIcon(PointerIcon(Cursor(Cursor.E_RESIZE_CURSOR)))

@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.cursorForVerticalResize(): Modifier =
    this.pointerHoverIcon(PointerIcon(Cursor(Cursor.N_RESIZE_CURSOR)))
