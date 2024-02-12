package ru.tyurin.animesnap.domain.repository

import android.net.Uri
import okhttp3.MultipartBody
import ru.tyurin.animesnap.domain.models.AnimeTitle
import java.io.File

interface AnimeTitleRepository {
    suspend fun getByUrl(url: String): AnimeTitle
    suspend fun getFromLocal(uri: Uri): AnimeTitle
}
