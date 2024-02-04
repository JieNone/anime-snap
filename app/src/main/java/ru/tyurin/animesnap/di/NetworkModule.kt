package ru.tyurin.animesnap.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import ru.tyurin.animesnap.data.network.TitleApiService
import ru.tyurin.animesnap.data.utils.SharedPreferencesKeys
import javax.inject.Singleton

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

