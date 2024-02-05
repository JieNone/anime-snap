package ru.tyurin.animesnap.di.modules

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import ru.tyurin.animesnap.data.network.TitleApiService
import ru.tyurin.animesnap.data.network.repository.AnimeTitleRepositoryImpl
import ru.tyurin.animesnap.data.utils.SharedPreferencesKeys
import ru.tyurin.animesnap.domain.repository.AnimeTitleRepository

@Module
@InstallIn(SingletonComponent::class)
class AppModules {

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(SharedPreferencesKeys.BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }
    @Provides
    fun provideApiService(
        retrofit: Retrofit
    ): TitleApiService = retrofit.create(TitleApiService::class.java)


    @Provides
    fun provideAnimeTitleRepository(
        titleApiService: TitleApiService
    ) : AnimeTitleRepository = AnimeTitleRepositoryImpl(
        apiService = titleApiService
    )
}