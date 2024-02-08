package ru.tyurin.animesnap.data.network.repository

import okhttp3.MultipartBody
import ru.tyurin.animesnap.data.network.TitleApiService
import ru.tyurin.animesnap.domain.models.AnimeTitle
import ru.tyurin.animesnap.domain.models.Result
import ru.tyurin.animesnap.domain.repository.AnimeTitleRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnimeTitleRepositoryImpl @Inject constructor(
    private val apiService: TitleApiService,
): AnimeTitleRepository {

    override suspend fun getByUrl(url: String): AnimeTitle {
        val response = apiService.searchByURL(url)

        if (response.error.isNotEmpty()) {
            throw IllegalStateException("Произошла ошибка: ${response.error}")
        }
        val results = response.result.map { result ->
            Result(
                similarity = result.similarity,
                imgSrc = result.imgSrc,
                anilist = result.anilist,
                filename = result.filename,
                episode = result.episode,
                from = result.from,
                to = result.to,
                video = result.video
            )
        }
        return AnimeTitle(
            frameCount = response.frameCount,
            error = response.error,
            result = results
        )
    }

    override suspend fun getFromLocal(imageBody: MultipartBody.Part): AnimeTitle {
        val response = apiService.searchFromLocal(imageBody)
        if (response.error.isNotEmpty()) {
            throw IllegalStateException("Произошла ошибка: ${response.error}")
        }
        val results = response.result.map { result ->
            Result(
                similarity = result.similarity,
                imgSrc = result.imgSrc,
                anilist = result.anilist,
                filename = result.filename,
                episode = result.episode,
                from = result.from,
                to = result.to,
                video = result.video
            )
        }
        return AnimeTitle(
            frameCount = response.frameCount,
            error = response.error,
            result = results
        )
    }
}