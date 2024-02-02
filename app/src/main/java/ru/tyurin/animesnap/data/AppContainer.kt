package ru.tyurin.animesnap.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import ru.tyurin.animesnap.data.network.TitleApiService
import ru.tyurin.animesnap.data.repository.AnimeTitleRepository
import ru.tyurin.animesnap.data.repository.NetworkAnimeTitleRepository

interface AppContainer {
    val animeTitleRepository: AnimeTitleRepository
}


class DefaultAppContainer : AppContainer {
    private val baseUrl = "https://api.trace.moe"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()


    private val retrofitService: TitleApiService by lazy {
        retrofit.create(TitleApiService::class.java)
    }

    override val animeTitleRepository: AnimeTitleRepository by lazy {
        NetworkAnimeTitleRepository(retrofitService)
    }
}