package hu.bme.aut.android.composerecipebook.data.converters

import androidx.room.TypeConverter
import java.io.File.separator

object ListConverter {
    private const val separator: String = ";"

    @TypeConverter
    fun List<String>.asString(): String {
        return this.joinToString(separator = separator)
    }

    @TypeConverter
    fun String.asList(): List<String> {
        if(this.length > 0) {
            return this.trim().split(separator)
        } else {
            return emptyList()
        }
    }
}