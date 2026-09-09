package com.zces.conta.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zces.conta.data.local.converter.Converters
import com.zces.conta.data.local.dao.BoqPositionDao
import com.zces.conta.data.local.dao.EntryDao
import com.zces.conta.data.local.dao.EntryPhotoDao
import com.zces.conta.data.local.dao.ProjectDao
import com.zces.conta.data.local.dao.ScheduleTaskDao
import com.zces.conta.data.local.entity.BoqPositionEntity
import com.zces.conta.data.local.entity.EntryEntity
import com.zces.conta.data.local.entity.EntryPhotoEntity
import com.zces.conta.data.local.entity.ProjectEntity
import com.zces.conta.data.local.entity.ScheduleTaskEntity
import com.zces.conta.data.local.entity.ScheduleTaskPredecessorEntity

/**
 * Schema version 1 — V1 initial release. Any future schema change must ship an explicit
 * Room Migration; exported XML/ZIP is a versioned contract independent of this DB schema
 * (CLAUDE.md §Architecture).
 */
@Database(
    entities = [
        ProjectEntity::class,
        BoqPositionEntity::class,
        ScheduleTaskEntity::class,
        ScheduleTaskPredecessorEntity::class,
        EntryEntity::class,
        EntryPhotoEntity::class,
    ],
    version = 1,
    exportSchema = true,
)
@TypeConverters(Converters::class)
abstract class ZCesDatabase : RoomDatabase() {
    abstract fun projectDao(): ProjectDao
    abstract fun boqPositionDao(): BoqPositionDao
    abstract fun scheduleTaskDao(): ScheduleTaskDao
    abstract fun entryDao(): EntryDao
    abstract fun entryPhotoDao(): EntryPhotoDao

    companion object {
        const val DATABASE_NAME = "zces_conta.db"
    }
}
