package com.zces.conta.data.prefs

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * PROJECT_CONTEXT.md #3.3: first launch asks only "Kako naj vas aplikacija označuje?" and
 * stores the author name locally. Not a registration/login — a single local string.
 */
private val Context.authorDataStore by preferencesDataStore(name = "author_profile")

class AuthorPreferences(private val context: Context) {

    private object Keys {
        val AUTHOR_NAME = stringPreferencesKey("author_name")
    }

    /** Null until the user completes onboarding. */
    val authorName: Flow<String?> = context.authorDataStore.data.map { it[Keys.AUTHOR_NAME] }

    val isOnboarded: Flow<Boolean> = authorName.map { !it.isNullOrBlank() }

    suspend fun setAuthorName(name: String) {
        context.authorDataStore.edit { it[Keys.AUTHOR_NAME] = name.trim() }
    }
}
