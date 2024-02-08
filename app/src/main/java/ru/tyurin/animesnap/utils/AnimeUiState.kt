package ru.tyurin.animesnap.utils

import ru.tyurin.animesnap.domain.models.AnimeTitle

sealed interface AnimeUiState {
    data class Success(val animeTitle: AnimeTitle) : AnimeUiState
    data object Error : AnimeUiState
    data object Loading : AnimeUiState
}