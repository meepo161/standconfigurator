package ru.avem.standconfigurator.model.blob

import androidx.compose.runtime.mutableStateListOf
import java.util.*

@kotlinx.serialization.Serializable
data class Project  (
    var name: String = "",
    var date: String = "",
    var author: String = "",
    val tests: List<Test> = List(80) {
        Test(
            text = "Опыт ${it + 1}",
            logics = mutableStateListOf(*Array(it + 1) {
                LogicItem(UUID.randomUUID().toString())
            })
        )
    },
    val devices: MutableList<Device> = mutableListOf()
) {
    override fun toString() = "$name | $date | $author"
}
