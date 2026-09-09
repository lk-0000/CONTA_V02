package com.zces.conta.data.local.converter

import androidx.room.TypeConverter
import com.zces.conta.data.local.entity.EntryType
import com.zces.conta.data.local.entity.PhotoSource

class Converters {
    @TypeConverter
    fun fromEntryType(value: EntryType): String = value.name

    @TypeConverter
    fun toEntryType(value: String): EntryType = EntryType.valueOf(value)

    @TypeConverter
    fun fromPhotoSource(value: PhotoSource): String = value.name

    @TypeConverter
    fun toPhotoSource(value: String): PhotoSource = PhotoSource.valueOf(value)
}
