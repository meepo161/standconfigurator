package ru.avem.standconfigurator.model

import java.nio.file.Paths

object UsersRepository {
    lateinit var users: MutableList<UserModel>

    fun addUser(userModel: UserModel) {
        users.add(userModel)

        saveToJsonFile(
            Paths.get("users.json"),
            users
        )
    }

    fun init() {
        try {
            users = loadFromJson(Paths.get("users.json"))
        } catch (e: Exception) {
            users = mutableListOf(UserModel("adminAvem", "admin", "avem"))
            saveToJsonFile(
                Paths.get("users.json"),
                users
            )
        }
    }
}
