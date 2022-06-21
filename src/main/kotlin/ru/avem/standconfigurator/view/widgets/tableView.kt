package ru.avem.standconfigurator.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

@Composable
fun <T> TableView(
    items: List<T>,
    columns: List<KProperty1<T, Any>>,
    columnNames: List<String> = emptyList(),
    onItemSelect: (Int) -> Unit
) {
    var selectedIndex by remember {
        mutableStateOf(0)
    }

    Column(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState()).padding(bottom = 60.dp)) {
        Row {
            if (columnNames.size == columns.size) {
                columnNames.forEach {
                    Box(
                        modifier = Modifier.border(
                            width = 1.dp,
                            color = Color.White,
                            shape = RoundedCornerShape(8.dp)
                        )
                            .weight(0.3f).height(64.dp).clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colors.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(it, style = TextStyle(fontWeight = FontWeight.Bold, color = Color.White))
                    }
                }
            } else {
                columns.forEach {
                    Box(
                        modifier = Modifier.clip(RoundedCornerShape(10.dp)).weight(0.3f).height(64.dp)
                            .background(MaterialTheme.colors.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(it.name, style = TextStyle(fontWeight = FontWeight.Bold, color = Color.White))
                    }
                }
            }
        }
        items.forEachIndexed { i, item ->
            Row(
                modifier = Modifier.clickable(onClick = {
                    selectedIndex = i
                    onItemSelect(i)
                }).background(
                    if (selectedIndex == i) {
                        MaterialTheme.colors.secondary
                    } else {
                        MaterialTheme.colors.background
                    }
                )
            ) {
                columns.forEach { column ->
                    val field = item!!.getField<Any>(column.name)!!.toString()
                    Box(
                        modifier = Modifier.border(
                            width = 1.dp,
                            color = MaterialTheme.colors.primary,
                            shape = RoundedCornerShape(4.dp)
                        )
                            .weight(0.3f).height(48.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = field, fontSize = 30.sp, color = if (selectedIndex == i) Color.White else MaterialTheme.colors.onSurface)
                    }
                }
            }
        }
    }
}

@Throws(IllegalAccessException::class, ClassCastException::class)
inline fun <reified T> Any.getField(fieldName: String): T? {
    this::class.memberProperties.forEach { kCallable ->
        if (fieldName == kCallable.name) {
            return kCallable.getter.call(this) as T?
        }
    }
    return null
}
