package ru.avem.standconfigurator.viewmodel

import androidx.compose.runtime.mutableStateListOf
import ru.avem.standconfigurator.model.structs.LogicItem
import ru.avem.standconfigurator.model.structs.Project
import ru.avem.standconfigurator.model.structs.Test

class CurrentProjectViewModel(currentProject: Project) {
    private var selectedTest = currentProject.tests.first()

    var selectedTestLogics = mutableStateListOf(*selectedTest.logics.toTypedArray())

    fun selectTest(test: Test) {
        selectedTest = test
        selectedTestLogics.clear()
        selectedTestLogics.addAll(selectedTest.logics)
    }

    fun addLogic(logicItem: LogicItem) {
        selectedTest.logics.add(logicItem)
        selectedTestLogics.add(logicItem)
    }
}
