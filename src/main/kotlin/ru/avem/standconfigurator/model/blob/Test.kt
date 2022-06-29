package ru.avem.standconfigurator.model.blob

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import ru.avem.standconfigurator.model.IListItem

data class Test(override val text: String, val logics: SnapshotStateList<LogicItem> = mutableStateListOf()) : IListItem
