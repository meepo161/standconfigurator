package ru.avem.standconfigurator.model

import androidx.compose.runtime.MutableState
import ru.avem.standconfigurator.model.structs.Device
import ru.avem.standconfigurator.model.structs.User

object MainModel {
    lateinit var isOpen: MutableState<Boolean>

    lateinit var currentUser: User

    val allDevices = listOf(
        "ОВЕН ПР100",
        "ОВЕН ПР102",
        "ОВЕН ПР200",
        "Delta C200",
        "Delta C2000",
        "Delta EF-DL",
        "Mitsubishi FRA7XX",
        "Mitsubishi FRA8XX",
        "АВЭМ АТР",
        "АВЭМ-3",
        "АВЭМ-4",
        "Satec PM-130",
        "Парма Т400",
        "ИКАС-8",
        "ИКАС-10",
        "APPA 70x",
        "GW Instek GPT",
        "Megger ЦС-02021",
        "ОВЕН СИ-30",
        "ОВЕН ТХ-01",
        "ОВЕН ТРМ-138",
        "ОВЕН ТРМ-202",
        "ОВЕН ТРМ-202",
        "Термодат Р10И5",
        "ProsoftSystems ИВД-3",
        "Тилком Т42",
        "BZVM РСТ-5",
    ).map(::Device).toMutableList() // TODO вытаскивать из базы
}
