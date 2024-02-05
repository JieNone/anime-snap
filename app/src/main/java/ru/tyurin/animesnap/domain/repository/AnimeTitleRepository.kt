package ru.tyurin.animesnap.domain.repository

import ru.tyurin.animesnap.domain.models.AnimeTitle
import ru.tyurin.animesnap.data.network.TitleApiService
import javax.inject.Inject
import javax.inject.Singleton

interface AnimeTitleRepository {
    suspend fun getAnimeTitlePhoto(url: String): AnimeTitle
}
