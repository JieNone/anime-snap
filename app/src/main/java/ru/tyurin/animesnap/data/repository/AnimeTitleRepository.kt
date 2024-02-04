package ru.tyurin.animesnap.data.repository

import dagger.Component
import dagger.Module
import ru.tyurin.animesnap.data.models.AnimeTitle
import ru.tyurin.animesnap.di.NetworkModule
import javax.inject.Singleton


@Singleton
interface AnimeTitleRepository {
    suspend fun getAnimeTitlePhoto(url: String): AnimeTitle
}
@Component(modules = [NetworkModule::class])
class NetworkAnimeTitleRepository : AnimeTitleRepository {
    private val titleApiService = NetworkModule.provideTitleApiService()
    override suspend fun getAnimeTitlePhoto(url: String): AnimeTitle =
        titleApiService.searchByURL(url)
}
