package ru.avem.standconfigurator.model.structs.devices

import ru.avem.standconfigurator.DeviceParam
import ru.avem.standconfigurator.model.structs.*

class PR : Device(
    mark = "DD", address = 2, name = "ПР",
    params = listOf(
        DeviceParam(ParamData(properties[0], "", FieldType.BOOL), ParamValue("true")),
        DeviceParam(ParamData(properties[1], "", FieldType.BOOL), ParamValue("false")),
        DeviceParam(ParamData(properties[2], "", FieldType.BOOL), ParamValue("false")),
        DeviceParam(ParamData("Дискретный выход 01 (DO_01)", "", FieldType.STRING), ParamValue("Дискретный выход 01 (DO_01)")),
        DeviceParam(ParamData("Дискретный выход 02 (DO_02)", "", FieldType.STRING), ParamValue("Дискретный выход 02 (DO_02)")),
        DeviceParam(ParamData("Дискретный выход 03 (DO_03)", "", FieldType.STRING), ParamValue("Дискретный выход 03 (DO_03)")),
        DeviceParam(ParamData("Дискретный выход 04 (DO_04)", "", FieldType.STRING), ParamValue("Дискретный выход 04 (DO_04)")),
        DeviceParam(ParamData("Дискретный выход 05 (DO_05)", "", FieldType.STRING), ParamValue("Дискретный выход 05 (DO_05)")),
        DeviceParam(ParamData("Дискретный выход 06 (DO_06)", "", FieldType.STRING), ParamValue("Дискретный выход 06 (DO_06)")),
        DeviceParam(ParamData("Дискретный выход 07 (DO_07)", "", FieldType.STRING), ParamValue("Дискретный выход 07 (DO_07)")),
        DeviceParam(ParamData("Дискретный выход 08 (DO_08)", "", FieldType.STRING), ParamValue("Дискретный выход 08 (DO_08)")),
        DeviceParam(ParamData("Дискретный выход 09 (DO_09)", "", FieldType.STRING), ParamValue("Дискретный выход 09 (DO_09)")),
        DeviceParam(ParamData("Дискретный выход 10 (DO_10)", "", FieldType.STRING), ParamValue("Дискретный выход 10 (DO_10)")),
        DeviceParam(ParamData("Дискретный выход 11 (DO_11)", "", FieldType.STRING), ParamValue("Дискретный выход 11 (DO_11)")),
        DeviceParam(ParamData("Дискретный выход 12 (DO_12)", "", FieldType.STRING), ParamValue("Дискретный выход 12 (DO_12)")),
        DeviceParam(ParamData("Дискретный выход 13 (DO_13)", "", FieldType.STRING), ParamValue("Дискретный выход 13 (DO_13)")),
        DeviceParam(ParamData("Дискретный выход 14 (DO_14)", "", FieldType.STRING), ParamValue("Дискретный выход 14 (DO_14)")),
        DeviceParam(ParamData("Дискретный выход 15 (DO_15)", "", FieldType.STRING), ParamValue("Дискретный выход 15 (DO_15)")),
        DeviceParam(ParamData("Дискретный выход 16 (DO_16)", "", FieldType.STRING), ParamValue("Дискретный выход 16 (DO_16)")),
    )
)
