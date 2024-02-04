package ru.tyurin.animesnap.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.tyurin.animesnap.data.models.AnimeTitle
import ru.tyurin.animesnap.data.repository.AnimeTitleRepository
import ru.tyurin.animesnap.data.utils.SharedPreferencesKeys
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class TitleViewModel : ViewModel() {

    @Inject lateinit var animeTitleRepository: AnimeTitleRepository
    var uiState: AnimeUiState by mutableStateOf(AnimeUiState.Loading)
        private set

    private var titleJob : Job? = null

    private val url = MutableLiveData<TextFieldValue>()
    private fun getUrl() = url.value
    fun updateUrl(newUrl: TextFieldValue) {
        url.value = newUrl
    }
    sealed interface AnimeUiState {
        data class Success(val url: AnimeTitle) : AnimeUiState
        data object Error : AnimeUiState
        data object Loading : AnimeUiState
    }
    fun getTitleByUrl() {
        titleJob?.cancel()
        titleJob = viewModelScope.launch {

            val fakeUrl = SharedPreferencesKeys.FAKE_IMG_URL
            uiState = AnimeUiState.Loading
            uiState = try {
                AnimeUiState.Success(
                    animeTitleRepository.getAnimeTitlePhoto(getUrl()?.text ?: fakeUrl )
                )
            } catch (_: IOException) {
                AnimeUiState.Error
            }
        }
    }
    override fun onCleared() {
        super.onCleared()
        titleJob?.cancel()
    }
}