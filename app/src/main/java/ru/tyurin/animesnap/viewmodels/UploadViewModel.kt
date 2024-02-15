package ru.tyurin.animesnap.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
    var uiState: AnimeUiState by mutableStateOf(AnimeUiState.Loading)
        private set

    var state: MutableStateFlow<AnimeUiState> = MutableStateFlow(AnimeUiState.Loading)

    fun uploadImage(imageFile: File) {
        viewModelScope.launch {
            try {
                val response = repository.searchByImage(imageFile)
                if (response.isSuccessful) {
                    val animeTitle = response.body()
                    state.value = AnimeUiState.Success(animeTitle!!)
                }
            } catch (e: IOException) {
                Log.e("EXCEPTION", e.toString())
                AnimeUiState.Error
            } catch (e: NullPointerException) {
                Log.e("EXCEPTION", e.toString())
                AnimeUiState.Error
            } catch (e: retrofit2.HttpException) {
                Log.e("EXCEPTION", e.toString())
                if (e.code() == 400) {
                    AnimeUiState.Error
                } else {
                    Log.e("EXCEPTION", e.toString())
                    AnimeUiState.Error
                }
            }
        }
    }
}