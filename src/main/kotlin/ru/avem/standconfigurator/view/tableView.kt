package ru.avem.standconfigurator.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

@Composable
fun <T> TableView(items: List<T>, columns: List<KProperty1<T, Any>>, columnNames: List<String> = emptyList()) {
    Column(modifier = Modifier.fillMaxWidth()) {
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
        items.forEach { item ->
            Row {
                columns.forEach { column ->
                    val field = item!!.getField<Any>(column.name)!!.toString()
                    Box(
                        modifier = Modifier.border(
                            width = 1.dp,
                            color = MaterialTheme.colors.primary,
                            shape = RoundedCornerShape(4.dp)
                        )
                            .weight(0.3f).height(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(field)
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