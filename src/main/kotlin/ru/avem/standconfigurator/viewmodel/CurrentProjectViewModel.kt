package ru.avem.standconfigurator.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import ru.avem.standconfigurator.model.structs.LogicItem
import ru.avem.standconfigurator.model.structs.Project
import ru.avem.standconfigurator.model.structs.Test

class CurrentProjectViewModel(private val currentProject: Project) {
    var selectedTest = mutableStateOf(currentProject.tests.firstOrNull())

    var tests = mutableStateListOf(*currentProject.tests.toTypedArray())

    val selectedTestIdx
        get() = tests.indexOf<Test>(selectedTest.value!!)

    var selectedTestLogics = mutableStateListOf(*selectedTest.value?.logics?.toTypedArray() ?: arrayOf())

    fun selectTest(test: Test) {
        selectedTest.value = test
        selectedTestLogics.clear()
        selectedTestLogics.addAll(selectedTest.value?.logics ?: listOf())
    }

    fun selectFirst() {
        if (tests.size != 1) {
            selectedTest.value = tests.firstOrNull()
            selectedTestLogics.clear()
            selectedTestLogics.addAll(selectedTest.value?.logics ?: listOf())
        }
    }

    fun selectLast() {
        selectedTest.value = tests.lastOrNull()
        selectedTestLogics.clear()
        selectedTestLogics.addAll(selectedTest.value?.logics ?: listOf())
    }

    fun addLogic(logicItem: LogicItem) {
        selectedTest.value?.logics?.add(logicItem)
        selectedTestLogics.add(logicItem)
    }

    fun addTest(test: Test) {
        currentProject.tests.add(test)
        tests.add(test)
        selectedTest.value = test
    }

    fun removeAt(testIdx: Int) {
        currentProject.tests.removeAt(testIdx)
        tests.removeAt(testIdx)
        selectedTest.value = tests.firstOrNull()
    }

    fun clear() {
        selectedTest.value = tests.firstOrNull()
        selectedTestLogics.clear()
    }
}
