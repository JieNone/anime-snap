package ru.tyurin.animesnap.data.network

import retrofit2.http.GET
import retrofit2.http.Query


interface TitleApiService {
    @GET("/search")
    suspend fun searchByURL(
        @Query("url") imageUrl: String
    ): AnimeTitle
}
