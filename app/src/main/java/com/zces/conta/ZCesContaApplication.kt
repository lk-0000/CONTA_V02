package com.zces.conta

import android.app.Application
import androidx.room.Room
import com.zces.conta.data.local.ZCesDatabase
import com.zces.conta.data.prefs.AuthorPreferences
import com.zces.conta.data.repository.AuthorRepository
import com.zces.conta.data.repository.ProjectRepository

/**
 * Simple manual DI via lazily-constructed singletons (CLAUDE.md: "Prefer AndroidX/standard-library
 * solutions over unnecessary dependencies" — no Hilt/Dagger for V1's scope).
 */
class ZCesContaApplication : Application() {

    val database: ZCesDatabase by lazy {
        Room.databaseBuilder(this, ZCesDatabase::class.java, ZCesDatabase.DATABASE_NAME)
            .build()
    }

    val authorPreferences: AuthorPreferences by lazy { AuthorPreferences(this) }

    val authorRepository: AuthorRepository by lazy { AuthorRepository(authorPreferences) }
    val projectRepository: ProjectRepository by lazy { ProjectRepository(database.projectDao()) }
}
