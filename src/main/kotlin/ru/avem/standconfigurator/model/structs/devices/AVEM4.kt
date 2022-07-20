package ru.avem.standconfigurator.model.structs.devices

import ru.avem.standconfigurator.model.structs.Device
import ru.avem.standconfigurator.model.structs.ParamData
import ru.avem.standconfigurator.model.structs.Type
import ru.avem.standconfigurator.model.structs.Value

class AVEM4 : Device(
    mark = "PV", address = 11, name = "АВЭМ-4",
    params = listOf(
        ParamData("Коэффициент трансформации", "x", Type.FIELD_FLOAT) to Value("1"),
        ParamData("Версия прошивки", "xx.xx.xxx.x", Type.FIELD_STR) to Value("21.32.565.7"),
        ParamData("Кол-во знаков после запятой", "шт.", Type.FIELD_INT) to Value("4"),
        ParamData("Значение", "", Type.ENUM) to Value("1", values = listOf("TRMS", "AVG", "AVR")),
        ParamData("Использовать реле", "", Type.BOOL) to Value("true"),
    )
)
