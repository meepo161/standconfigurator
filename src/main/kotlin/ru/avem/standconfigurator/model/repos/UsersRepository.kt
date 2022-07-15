package ru.avem.standconfigurator.model.repos

import ru.avem.standconfigurator.model.structs.User
import ru.avem.standconfigurator.model.utils.loadFromJson
import ru.avem.standconfigurator.model.utils.saveToJsonFile
import java.nio.file.Paths

object UsersRepository {
    val users = mutableListOf<User>()

    fun add(user: User) {
        users.add(user)

        saveToJsonFile(
            Paths.get("users.json"),
            users
        )
    }

    fun init() {
        try {
            users.addAll(loadFromJson(Paths.get("users.json")))
        } catch (_: Exception) {
        }
    }
}
