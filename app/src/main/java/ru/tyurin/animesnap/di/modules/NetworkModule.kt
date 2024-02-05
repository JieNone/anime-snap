package ru.tyurin.animesnap.di.modules

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import ru.tyurin.animesnap.data.network.TitleApiService
import ru.tyurin.animesnap.data.utils.SharedPreferencesKeys
import javax.inject.Scope

@Scope
annotation class RetrofitScope
@InstallIn(SingletonComponent::class)
@Module
interface NetworkModule {

    companion object {

        private var apiService: TitleApiService? = null
        fun getInstance(): TitleApiService {
            if (apiService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(SharedPreferencesKeys.BASE_URL)
                    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
                    .build()
                apiService = retrofit.create(TitleApiService::class.java)
            }
            return apiService!!
        }
    }


}
