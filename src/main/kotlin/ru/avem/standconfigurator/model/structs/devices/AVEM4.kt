package ru.avem.standconfigurator.model.structs.devices

import ru.avem.standconfigurator.DeviceParam
import ru.avem.standconfigurator.model.structs.*

class AVEM4 : Device(
    mark = "PV", address = 11, name = "АВЭМ-4",
    params = listOf(
        DeviceParam(
            paramData = ParamData(name = properties[0], unit = "", fieldType = FieldType.BOOL),
            paramValue = ParamValue("false")
        ),
        DeviceParam(
            paramData = ParamData(name = properties[1], unit = "", fieldType = FieldType.BOOL),
            paramValue = ParamValue("false")
        ),
        DeviceParam(
            paramData = ParamData(name = properties[2], unit = "", fieldType = FieldType.BOOL),
            paramValue = ParamValue("true")
        ),
        DeviceParam(
            paramData = ParamData(name = "Коэффициент трансформации", unit = "x", fieldType = FieldType.FLOAT),
            paramValue = ParamValue("1")
        ),
        DeviceParam(
            paramData = ParamData(name = "Версия прошивки", unit = "xx.xx.xxx.x", fieldType = FieldType.STRING),
            paramValue = ParamValue("21.32.565.7")
        ),
        DeviceParam(
            paramData = ParamData(name = "Кол-во знаков после запятой", unit = "шт.", fieldType = FieldType.INT),
            paramValue = ParamValue("4")
        ),
        DeviceParam(
            paramData = ParamData(
                name = "Значение",
                unit = "",
                fieldType = FieldType.ENUM,
                stores = mutableListOf("TRMS", "AVG", "AVR")
            ),
            paramValue = ParamValue("1")
        ),
        DeviceParam(
            paramData = ParamData(name = "Использовать реле", unit = "", fieldType = FieldType.BOOL),
            paramValue = ParamValue("true")
        ),
        DeviceParam(
            paramData = ParamData(
                name = "Напряжение (действующее)",
                unit = "В",
                fieldType = FieldType.DOUBLE,
                isIndicate = true
            ),
            paramValue = ParamValue("0")
        ),
    ),
//    registers = listOf( TODO
//
//    )
)
