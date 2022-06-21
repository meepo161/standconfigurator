package ru.avem.standconfigurator.model

import ru.avem.standconfigurator.model.blob.User
import ru.avem.standconfigurator.model.utils.loadFromJson
import ru.avem.standconfigurator.model.utils.saveToJsonFile
import java.nio.file.Paths

object UsersRepository {
    lateinit var users: MutableList<User>

    fun add(user: User) {
        users.add(user)

        saveToJsonFile(
            Paths.get("users.json"),
            users
        )
    }

    fun init() {
        try {
            users = loadFromJson(Paths.get("users.json"))
        } catch (e: Exception) {
            users = mutableListOf(User("adminAvem", "admin", "avem"))
            saveToJsonFile(
                Paths.get("users.json"),
                users
            )
        }
    }
}
