package ru.tyurin.animesnap.domain.repository

import okhttp3.MultipartBody
import ru.tyurin.animesnap.domain.models.AnimeTitle

interface AnimeTitleRepository {
    suspend fun getByUrl(url: String): AnimeTitle
    suspend fun getFromLocal(imageBody: MultipartBody.Part): AnimeTitle
}
