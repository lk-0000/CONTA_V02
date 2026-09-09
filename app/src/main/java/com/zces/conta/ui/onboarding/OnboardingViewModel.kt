package com.zces.conta.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zces.conta.data.repository.AuthorRepository
import kotlinx.coroutines.launch

class OnboardingViewModel(private val authorRepository: AuthorRepository) : ViewModel() {

    fun submitAuthorName(name: String, onDone: () -> Unit) {
        val trimmed = name.trim()
        if (trimmed.isEmpty()) return
        viewModelScope.launch {
            authorRepository.setAuthorName(trimmed)
            onDone()
        }
    }
}
