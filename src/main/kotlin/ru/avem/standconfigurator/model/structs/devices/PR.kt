package ru.avem.standconfigurator.model.structs.devices

import ru.avem.standconfigurator.PairParam
import ru.avem.standconfigurator.model.structs.Device
import ru.avem.standconfigurator.model.structs.ParamData
import ru.avem.standconfigurator.model.structs.Type
import ru.avem.standconfigurator.model.structs.ParamValue

class PR : Device(
    mark = "DD", address = 2, name = "ПР",
    params = listOf(
        PairParam(ParamData("Дискретный выход 01 (DO_01)", "", Type.FIELD_STR), ParamValue("Дискретный выход 01 (DO_01)")),
        PairParam(ParamData("Дискретный выход 02 (DO_02)", "", Type.FIELD_STR), ParamValue("Дискретный выход 02 (DO_02)")),
        PairParam(ParamData("Дискретный выход 03 (DO_03)", "", Type.FIELD_STR), ParamValue("Дискретный выход 03 (DO_03)")),
        PairParam(ParamData("Дискретный выход 04 (DO_04)", "", Type.FIELD_STR), ParamValue("Дискретный выход 04 (DO_04)")),
        PairParam(ParamData("Дискретный выход 05 (DO_05)", "", Type.FIELD_STR), ParamValue("Дискретный выход 05 (DO_05)")),
        PairParam(ParamData("Дискретный выход 06 (DO_06)", "", Type.FIELD_STR), ParamValue("Дискретный выход 06 (DO_06)")),
        PairParam(ParamData("Дискретный выход 07 (DO_07)", "", Type.FIELD_STR), ParamValue("Дискретный выход 07 (DO_07)")),
        PairParam(ParamData("Дискретный выход 08 (DO_08)", "", Type.FIELD_STR), ParamValue("Дискретный выход 08 (DO_08)")),
        PairParam(ParamData("Дискретный выход 09 (DO_09)", "", Type.FIELD_STR), ParamValue("Дискретный выход 09 (DO_09)")),
        PairParam(ParamData("Дискретный выход 10 (DO_10)", "", Type.FIELD_STR), ParamValue("Дискретный выход 10 (DO_10)")),
        PairParam(ParamData("Дискретный выход 11 (DO_11)", "", Type.FIELD_STR), ParamValue("Дискретный выход 11 (DO_11)")),
        PairParam(ParamData("Дискретный выход 12 (DO_12)", "", Type.FIELD_STR), ParamValue("Дискретный выход 12 (DO_12)")),
        PairParam(ParamData("Дискретный выход 13 (DO_13)", "", Type.FIELD_STR), ParamValue("Дискретный выход 13 (DO_13)")),
        PairParam(ParamData("Дискретный выход 14 (DO_14)", "", Type.FIELD_STR), ParamValue("Дискретный выход 14 (DO_14)")),
        PairParam(ParamData("Дискретный выход 15 (DO_15)", "", Type.FIELD_STR), ParamValue("Дискретный выход 15 (DO_15)")),
        PairParam(ParamData("Дискретный выход 16 (DO_16)", "", Type.FIELD_STR), ParamValue("Дискретный выход 16 (DO_16)")),
    )
)
