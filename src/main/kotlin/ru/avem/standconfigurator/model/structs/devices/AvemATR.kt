package ru.avem.standconfigurator.model.structs.devices

import ru.avem.standconfigurator.DeviceParam
import ru.avem.standconfigurator.model.structs.*

class AvemATR : Device(
    mark = "GV", address = 240, name = "АВЭМ АТР",
    params = listOf(
        DeviceParam(
            paramData = ParamData(name = properties[0], unit = "", fieldType = FieldType.BOOL),
            paramValue = ParamValue("false")
        ),
        DeviceParam(
            paramData = ParamData(name = properties[1], unit = "", fieldType = FieldType.BOOL),
            paramValue = ParamValue("true")
        ),
        DeviceParam(
            paramData = ParamData(name = properties[2], unit = "", fieldType = FieldType.BOOL),
            paramValue = ParamValue("true")
        ),
        DeviceParam(
            paramData = ParamData(name = properties[3], unit = "", fieldType = FieldType.ENUM, stores = mutableListOf()),
            paramValue = ParamValue("")
        ),
        DeviceParam(
            paramData = ParamData(name = "Количество катушек", unit = "шт.", fieldType = FieldType.INT),
            paramValue = ParamValue("1")
        ),
        DeviceParam(
            paramData = ParamData(name = "Коэффициент трансформации", unit = "", fieldType = FieldType.FLOAT),
            paramValue = ParamValue("1.0")
        ),
        DeviceParam(
            paramData = ParamData(name = "Коэффициент трансформации отвода", unit = "", fieldType = FieldType.FLOAT),
            paramValue = ParamValue("1.0")
        ),
        DeviceParam(
            paramData = ParamData(name = "Ручная регулировка", unit = "", fieldType = FieldType.BOOL),
            paramValue = ParamValue("false")
        ),
        DeviceParam(
            paramData = ParamData(name = "Регулируется по обратной связи", unit = "", fieldType = FieldType.BOOL),
            paramValue = ParamValue("true")
        ),
        DeviceParam(
            paramData = ParamData(name = "Скорость вращения", unit = "об/мин", fieldType = FieldType.FLOAT),
            paramValue = ParamValue("1")
        ),
        DeviceParam(
            paramData = ParamData(name = "Набор напряжения", unit = "В/с", fieldType = FieldType.INT),
            paramValue = ParamValue("1")
        ),
        DeviceParam(
            paramData = ParamData(name = "Таймаут регулировки", unit = "с", fieldType = FieldType.INT),
            paramValue = ParamValue("1")
        ),
        DeviceParam(
            paramData = ParamData(
                name = "Напряжение (действующее) (АТР)",
                unit = "В",
                fieldType = FieldType.DOUBLE,
                isIndicate = true
            ),
            paramValue = ParamValue("0")
        ),
    )
)
