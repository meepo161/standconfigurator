package ru.avem.standconfigurator.model.utils

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.nio.file.Path
import kotlin.io.path.writeText

inline fun <reified T> loadFromJson(path: Path): T {
    val resultFile = path.toFile()
    if (!resultFile.exists()) {
        resultFile.createNewFile()
    }
    return Json.decodeFromString(resultFile.readText())
}

inline fun <reified T> saveToJsonFile(path: Path, model: T) {
    path.writeText(Json.encodeToString(model))
}
