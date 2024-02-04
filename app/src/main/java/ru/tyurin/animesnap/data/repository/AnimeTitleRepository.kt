package ru.tyurin.animesnap.data.repository

import ru.tyurin.animesnap.data.models.AnimeTitle
import ru.tyurin.animesnap.data.network.TitleApiService
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
interface AnimeTitleRepository {
    suspend fun getAnimeTitlePhoto(url: String): AnimeTitle

}