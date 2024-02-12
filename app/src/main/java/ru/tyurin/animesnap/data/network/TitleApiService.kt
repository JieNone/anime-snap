package ru.tyurin.animesnap.data.network

import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import ru.tyurin.animesnap.domain.models.AnimeTitle


interface TitleApiService {
    @GET("/search")
    suspend fun searchByURL(
        @Query(value = "url") imageUrl: String
    ): AnimeTitle

    @Multipart
    @POST("/search")
    suspend fun searchFromLocalStorage(
        @Part image: MultipartBody.Part
    ): AnimeTitle
}
