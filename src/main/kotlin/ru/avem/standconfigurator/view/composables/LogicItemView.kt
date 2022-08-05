package ru.avem.standconfigurator.view.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import ru.avem.standconfigurator.model.ProjectModel
import ru.avem.standconfigurator.model.ProjectModel.findDeviceByName
import ru.avem.standconfigurator.model.structs.LogicItem
import ru.avem.standconfigurator.model.structs.logicTypes
import ru.avem.standconfigurator.model.structs.device.deviceProperties
import ru.avem.standconfigurator.view.keyEventNext
import ru.avem.standconfigurator.view.keyboardActionNext

@Composable
fun LogicItemView(logicItem: LogicItem) {
    val focusManager = LocalFocusManager.current

    val logicItemTypeState = remember { mutableStateOf(logicItem.type) }
    val logicItemField1State = remember { mutableStateOf(logicItem.field1) }
    val logicItemField2State = remember { mutableStateOf(logicItem.field2) }
    val logicItemField3State = remember { mutableStateOf(logicItem.field3) }
    val logicItemField4State = remember { mutableStateOf(logicItem.field4) }
    val logicItemField5State = remember { mutableStateOf(logicItem.field5) }

    fun clearFields() {
        logicItemField1State.value = ""
        logicItemField2State.value = ""
        logicItemField3State.value = ""
        logicItemField4State.value = ""
        logicItemField5State.value = ""
        logicItem.field1 = ""
        logicItem.field2 = ""
        logicItem.field3 = ""
        logicItem.field4 = ""
        logicItem.field5 = ""
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            modifier = Modifier.height(4.dp),
            text = "",
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ComboBox(
                modifier = Modifier.fillMaxWidth(),
                selectedItem = logicItemTypeState,
                items = logicTypes,
                onDismissState = {},
                selectedValue = {
                    clearFields()
                    logicItem.type = logicItemTypeState.value
                }
            )
        }
        Column {
            val commutationDevices =
                ProjectModel.dvm.stateDevices.filter { it.params.any() { it.paramData.name == deviceProperties[0] && it.paramValue.storeState.value == "true" } }
            val regulationDevices =
                ProjectModel.dvm.stateDevices.filter { it.params.any() { it.paramData.name == deviceProperties[1] && it.paramValue.storeState.value == "true" } }

            when (logicItemTypeState.value) {
                "Коммутация" -> {
                    ComboBox(
                        modifier = Modifier.fillMaxWidth(),
                        selectedItem = logicItemField1State,
                        items = commutationDevices.map(Any::toString),
                        onDismissState = {},
                        selectedValue = {
                            logicItem.field1 = logicItemField1State.value
                        })
                }
                "Регулировка" -> {
                    ComboBox(
                        modifier = Modifier.fillMaxWidth(),
                        selectedItem = logicItemField1State,
                        items = regulationDevices.map(Any::toString),
                        onDismissState = {},
                        selectedValue = {
                            logicItem.field1 = logicItemField1State.value
                        })
                }
                "Выжидание" -> OutlinedTextField(
                    singleLine = true,
                    value = logicItemField1State.value,
                    onValueChange = {
                        if (it.toIntOrNull() != null) {
                            logicItemField1State.value = it
                            logicItem.field1 = logicItemField1State.value
                        }
                    },
                    modifier = Modifier.fillMaxWidth().focusTarget().onPreviewKeyEvent {
                        keyEventNext(it, focusManager)
                    },
                    label = {
                        Text(text = "Введите время задержки, с")
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = keyboardActionNext(focusManager)
                )
                "Защиты" ->
                    ComboBox(
                        modifier = Modifier.fillMaxWidth(),
                        selectedItem = logicItemField3State,
                        items = listOf("Включить", "Отключить"),
                        onDismissState = {},
                        selectedValue = {
                            logicItem.field3 = logicItemField3State.value
                        }
                    )
                "Комментарий" -> OutlinedTextField(
                    singleLine = true,
                    value = logicItemField1State.value,
                    onValueChange = {
                        logicItemField1State.value = it
                        logicItem.field1 = logicItemField1State.value
                    },
                    modifier = Modifier.fillMaxWidth().focusTarget().onPreviewKeyEvent {
                        keyEventNext(it, focusManager)
                    },
                    label = {
                        Text(text = "Введите комментарий")
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = keyboardActionNext(focusManager)
                )
            }
            when (logicItemTypeState.value) {
                "Коммутация" -> ComboBox(
                    modifier = Modifier.fillMaxWidth(),
                    selectedItem = logicItemField2State,
                    items = commutationDevices.find { it.toString() == logicItemField1State.value }?.params?.map { it.paramValue.storeState.value }
                        ?: listOf(),
                    onDismissState = {},
                    selectedValue = {
                        logicItem.field2 = logicItemField2State.value
                    }
                )
                "Регулировка" -> ComboBox(
                    modifier = Modifier.fillMaxWidth(),
                    selectedItem = logicItemField2State,
                    items = regulationDevices.find { it.toString() == logicItemField1State.value }?.params
                        ?.find { it.paramData.name == "Ус-во по которому регулируемся" }?.paramValue?.storeState?.value
                        ?.run {
                            findDeviceByName(this)?.params?.filter { it.paramData.isIndicate }
                                ?.map { it.paramData.name }
                        } ?: listOf(""),
                    onDismissState = {},
                    selectedValue = {
                        logicItem.field2 = logicItemField2State.value

                        logicItemField4State.value = findDeviceByName(logicItem.field1)?.run {
                            this.params.find { it.paramData.name == "Ус-во по которому регулируемся" }?.paramValue?.storeState?.value
                        } ?: ""
                        logicItem.field4 = logicItemField4State.value

                        logicItemField5State.value =
                            findDeviceByName(logicItemField4State.value)?.params?.find { it.paramData.name == logicItemField2State.value }?.paramData?.unit
                                ?: ""
                        logicItem.field5 = logicItemField5State.value
                    }
                )
            }
            when (logicItemTypeState.value) {
                "Коммутация" -> ComboBox(
                    modifier = Modifier.fillMaxWidth(),
                    selectedItem = logicItemField3State,
                    items = listOf("Замкнуть", "Разомкнуть"),
                    onDismissState = {},
                    selectedValue = {
                        logicItem.field3 = logicItemField3State.value
                    }
                )
                "Регулировка" -> OutlinedTextField(
                    singleLine = true,
                    value = logicItemField3State.value,
                    onValueChange = {
                        logicItemField3State.value = it
                        logicItem.field3 = logicItemField3State.value
                    },
                    modifier = Modifier.fillMaxWidth().focusTarget().onPreviewKeyEvent {
                        keyEventNext(it, focusManager)
                    },
                    label = {
                        Text(text = "Введите значение")
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = keyboardActionNext(focusManager)
                )
            }
        }
    }
}
