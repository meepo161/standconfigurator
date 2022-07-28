package ru.avem.standconfigurator.model.structs.devices

import ru.avem.standconfigurator.PairParam
import ru.avem.standconfigurator.model.structs.Device
import ru.avem.standconfigurator.model.structs.ParamData
import ru.avem.standconfigurator.model.structs.Type
import ru.avem.standconfigurator.model.structs.ParamValue

class AVEM4 : Device(
    mark = "PV", address = 11, name = "АВЭМ-4",
    params = listOf(
        PairParam(ParamData("Коэффициент трансформации", "x", Type.FIELD_FLOAT), ParamValue("1")),
        PairParam(ParamData("Версия прошивки", "xx.xx.xxx.x", Type.FIELD_STR), ParamValue("21.32.565.7")),
        PairParam(ParamData("Кол-во знаков после запятой", "шт.", Type.FIELD_INT), ParamValue("4")),
        PairParam(ParamData("Значение", "", Type.ENUM), ParamValue("1", stores = listOf("TRMS", "AVG", "AVR"))),
        PairParam(ParamData("Использовать реле", "", Type.BOOL), ParamValue("true")),
    )
)
