package ru.avem.standconfigurator.model

import androidx.compose.runtime.MutableState
import ru.avem.standconfigurator.model.structs.Project
import ru.avem.standconfigurator.viewmodel.ProjectViewModel
import ru.avem.standconfigurator.viewmodel.DevicesViewModel

object ProjectModel {
    lateinit var currentProject: Project

    lateinit var currentProjectVM: MutableState<ProjectViewModel>

    lateinit var dvm : DevicesViewModel

    fun refreshDevices() {
        val indicateDevices = dvm.stateDevices.filter { it.params.any { it.paramData.name == "Ус-во по которому можно регулироваться" && it.paramValue.storeState.value == "true" } }.map(Any::toString)

        MainModel.allDevices.values.filter { it.params.any { it.paramData.name == "Регулирующее?" && it.paramValue.storeState.value == "true" } }.forEach {
            val indicatorField = it.params.find { it.paramData.name == "Ус-во по которому регулируемся" }
            indicatorField?.let {
                it.paramData.stores.clear()
                it.paramData.stores.addAll(indicateDevices)
            }
        }
    }

    fun findDeviceByName(deviceID: String) = dvm.stateDevices.find { deviceID == it.toString() }
}
