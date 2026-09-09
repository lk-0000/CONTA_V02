package com.zces.conta.data.repository

import com.zces.conta.data.prefs.AuthorPreferences
import kotlinx.coroutines.flow.Flow

class AuthorRepository(private val authorPreferences: AuthorPreferences) {
    val authorName: Flow<String?> = authorPreferences.authorName
    val isOnboarded: Flow<Boolean> = authorPreferences.isOnboarded

    suspend fun setAuthorName(name: String) = authorPreferences.setAuthorName(name)
}
