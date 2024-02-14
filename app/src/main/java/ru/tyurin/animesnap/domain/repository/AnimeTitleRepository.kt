package ru.tyurin.animesnap.domain.repository

import android.net.Uri
import ru.tyurin.animesnap.domain.models.AnimeTitle

interface AnimeTitleRepository {
    suspend fun getByUrl(url: String): AnimeTitle
    suspend fun getDataFromServer(uri: Uri): AnimeTitle
}
