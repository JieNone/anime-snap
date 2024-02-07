package ru.tyurin.animesnap.domain.repository

import ru.tyurin.animesnap.domain.models.AnimeTitle

interface AnimeTitleRepository {
    suspend fun getAnimeTitlePhoto(url: String): AnimeTitle
}
