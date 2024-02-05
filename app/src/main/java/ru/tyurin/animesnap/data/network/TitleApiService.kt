package ru.tyurin.animesnap.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import ru.tyurin.animesnap.domain.models.AnimeTitle


interface TitleApiService {
    @GET("/search")
    suspend fun searchByURL(
        @Query(value = "url") imageUrl: String
    ): AnimeTitle
}
