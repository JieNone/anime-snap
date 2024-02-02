package ru.tyurin.animesnap.data.repository

import ru.tyurin.animesnap.data.network.AnimeTitle
import ru.tyurin.animesnap.data.network.TitleApiService



interface AnimeTitleRepository {
    suspend fun getAnimeTitlePhoto(url: String): AnimeTitle
}

class NetworkAnimeTitleRepository(
    private val titleApiService: TitleApiService,
) : AnimeTitleRepository {
    override suspend fun getAnimeTitlePhoto(url: String): AnimeTitle =
        titleApiService.searchByURL(url)
}