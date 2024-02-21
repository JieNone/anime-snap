package ru.tyurin.animesnap.viewmodels

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
                val response = repository.searchByImage(imageFile)
                if (response.isSuccessful) {
                    val animeTitle = response.body()
                    uiState.value = AnimeUiState.Success(animeTitle!!)
                }
            } catch (e: IOException) {

                uiState.value = AnimeUiState.Error
            } catch (e: NullPointerException) {
                uiState.value = AnimeUiState.Error
            } catch (e: retrofit2.HttpException) {
                if (e.code() == 400) {
                    uiState.value = AnimeUiState.Error
                } else {
                    uiState.value = AnimeUiState.Error
                }
            }
        }
    }
}
