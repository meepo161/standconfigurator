package ru.avem.standconfigurator.model.structs

val logicTypes = listOf("Коммутация", "Регулировка", "Выжидание", "Защиты", "Комментарий")

@kotlinx.serialization.Serializable
data class LogicItem(
    var type: String = logicTypes[0],
    var field1: String = "",
    var field2: String = "",
    var field3: String = "",
    var field4: String = "",
    var field5: String = ""
) {
    override fun toString(): String {
        return when (type) {
            "Коммутация" -> "$type: $field1->$field2 [$field3]"
            "Регулировка" -> "$type: Ус-вом $field1 (по $field4:$field2) выставить значение $field3 $field5"
            "Выжидание" -> "$type: $field1 с"
            "Защиты" -> "$type: $field1 [$field2]"
            "Комментарий" -> "$type: $field1"

            else -> error("handle")
        }
    }
}
