package ru.avem.standconfigurator.view.screens.intermediate

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.avem.standconfigurator.view.composables.ComboBox
import ru.avem.standconfigurator.view.keyEventNext
import ru.avem.standconfigurator.view.keyboardActionNext

class DialogRowData(
    val name: String,
    val type: DialogRowType,
    val isCanEmpty: Boolean = true,
    var variableField: List<String> = listOf(),
    var field: MutableState<String>,
    var isVisible: MutableState<Boolean>,
    var errorState: MutableState<Boolean>
)

enum class DialogRowType {
    TEXT,
    COMBO
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RowsAlertDialog(
    title: String,
    rows: List<DialogRowData>,
    isDialogVisible: MutableState<Boolean>,
    buttonText: String,
    checkCreateProjectErrors: () -> Unit,
    onClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    AlertDialog(
        modifier = Modifier.width(600.dp).padding(top = 16.dp, bottom = 16.dp),
        onDismissRequest = { },
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = MaterialTheme.colors.primary)) {
                        append(title.first())
                    }
                    withStyle(style = SpanStyle(color = Color.Black)) {
                        append(title.substring(1))
                    }
                }, fontSize = 30.sp)
                IconButton(onClick = {
                    isDialogVisible.value = false
                }) {
                    Icon(Icons.Filled.Close, contentDescription = null)
                }
            }
        },
        buttons = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        onClick()
                    }) {
                    Text(buttonText)
                }
            }
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    modifier = Modifier.height(4.dp),
                    text = "",
                )

                rows.forEach { dialogRowData ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            when (dialogRowData.type) {
                                DialogRowType.TEXT -> {
                                    OutlinedTextField(
                                        singleLine = true,
                                        value = dialogRowData.field.value,
                                        onValueChange = {
                                            dialogRowData.field.value = it
                                            checkCreateProjectErrors()
                                        },
                                        isError = dialogRowData.errorState.value,
                                        modifier = Modifier.fillMaxWidth().focusTarget().onPreviewKeyEvent {
                                            keyEventNext(it, focusManager)
                                        },
                                        label = {
                                            Text(text = dialogRowData.name)
                                        },
                                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                                        keyboardActions = keyboardActionNext(focusManager)
                                    )
                                    if (dialogRowData.errorState.value) {
                                        Text(text = "Проверьте правильность поля", color = MaterialTheme.colors.error)
                                    }
                                }
                                DialogRowType.COMBO -> {
                                    ComboBox(
                                        modifier = Modifier.fillMaxWidth().focusTarget().onPreviewKeyEvent {
                                            keyEventNext(it, focusManager)
                                        },
                                        initialValue = dialogRowData.variableField.first(),
                                        items = dialogRowData.variableField,
                                        onDismissState = {},
                                        selectedValue = {
                                            dialogRowData.field.value = it
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        })
}
