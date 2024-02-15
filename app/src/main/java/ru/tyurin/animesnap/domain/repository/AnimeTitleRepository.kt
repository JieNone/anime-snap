package ru.tyurin.animesnap.domain.repository

import android.net.Uri
import retrofit2.Response
import ru.tyurin.animesnap.domain.models.AnimeTitle
import java.io.File

interface AnimeTitleRepository {
    suspend fun getByUrl(url: String): AnimeTitle

    suspend fun searchByImage(imageFile: File): Response<AnimeTitle>
}
