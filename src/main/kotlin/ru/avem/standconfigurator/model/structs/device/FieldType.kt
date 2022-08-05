package ru.avem.standconfigurator.model.structs.device

@kotlinx.serialization.Serializable
enum class FieldType {
    STRING,
    FLOAT,
    DOUBLE,
    INT,
    BOOL,
    ENUM,
    NONE
}
