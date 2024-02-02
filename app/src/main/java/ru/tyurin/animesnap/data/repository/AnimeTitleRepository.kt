package ru.tyurin.animesnap.data.repository

import androidx.lifecycle.liveData
import ru.tyurin.animesnap.data.models.AnimeTitle
import ru.tyurin.animesnap.data.network.TitleApiService



interface AnimeTitleRepository {
    suspend fun getAnimeTitlePhoto(url: String): AnimeTitle

    fun getData() = liveData {
        emit(String)
    }
}

class NetworkAnimeTitleRepository(
    private val titleApiService: TitleApiService,
) : AnimeTitleRepository {
    override suspend fun getAnimeTitlePhoto(url: String): AnimeTitle =
        titleApiService.searchByURL(url)
}