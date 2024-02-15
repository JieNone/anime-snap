package ru.tyurin.animesnap.viewmodels

import android.util.Log
import androidx.compose.runtime.produceState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.tyurin.animesnap.domain.repository.AnimeTitleRepository
import ru.tyurin.animesnap.utils.AnimeUiState
import java.io.File
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class UploadViewModel @Inject constructor(
    private val repository: AnimeTitleRepository
) : ViewModel() {

    var uiState: MutableStateFlow<AnimeUiState> = MutableStateFlow(AnimeUiState.Loading)


    fun uploadImage(imageFile: File) {
        viewModelScope.launch {
            try {
                Log.d("UPLOAD_VIEW_MODEL", "Start uploading image: $imageFile")
                val response = repository.searchByImage(imageFile)
                if (response.isSuccessful) {
                    val animeTitle = response.body()
                    uiState.value = AnimeUiState.Success(animeTitle!!)
                }
                Log.d("UPLOAD_VIEW_MODEL", "Upload completed successfully")
            } catch (e: IOException) {
                Log.e("UPLOAD_VIEW_MODEL", "IOException occurred: $e")
                uiState.value = AnimeUiState.Error
            } catch (e: NullPointerException) {
                Log.e("UPLOAD_VIEW_MODEL", "NullPointerException occurred: $e")
                uiState.value = AnimeUiState.Error
            } catch (e: retrofit2.HttpException) {
                Log.e("UPLOAD_VIEW_MODEL", "HttpException occurred: $e")
                if (e.code() == 400) {
                    uiState.value = AnimeUiState.Error
                } else {
                    Log.e("UPLOAD_VIEW_MODEL", "Unknown HttpException occurred: $e")
                    uiState.value = AnimeUiState.Error
                }
            }
        }
    }
}
