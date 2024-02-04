package ru.tyurin.animesnap.di

import android.net.Network
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import ru.tyurin.animesnap.data.models.AnimeTitle
import ru.tyurin.animesnap.data.network.TitleApiService
import ru.tyurin.animesnap.data.repository.AnimeTitleRepository
import ru.tyurin.animesnap.data.repository.NetworkAnimeTitleRepository
import ru.tyurin.animesnap.data.utils.SharedPreferencesKeys
import javax.inject.Singleton


@Singleton
interface AnimeTitleRepository {
    suspend fun getAnimeTitlePhoto(url: String): AnimeTitle
}

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val baseUrl = SharedPreferencesKeys.BASE_URL
        return Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(baseUrl)
            .build()
    }

    @Provides
    @Singleton
    fun provideTitleApiService(): TitleApiService {
        return provideRetrofit().create(TitleApiService::class.java)
    }

}

