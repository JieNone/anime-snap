package ru.tyurin.animesnap.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.State
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.tyurin.animesnap.domain.repository.AnimeTitleRepository
import ru.tyurin.animesnap.data.utils.AnimeUiState
import ru.tyurin.animesnap.data.utils.SharedPreferencesKeys
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class TitleViewModel @Inject constructor(
    private val repository: AnimeTitleRepository

) : ViewModel() {
    var uiState: AnimeUiState by mutableStateOf(AnimeUiState.Loading)
        private set

    private var titleJob : Job? = null

    private val url = MutableLiveData<TextFieldValue>()
    fun updateUrl(newUrl: TextFieldValue) {
        url.value = newUrl
    }
    fun getTitleByUrl() {
        titleJob?.cancel()
        titleJob = viewModelScope.launch {

            val fakeUrl = SharedPreferencesKeys.FAKE_IMG_URL
            uiState = AnimeUiState.Loading
            uiState = try {
                AnimeUiState.Success(
                    repository.getAnimeTitlePhoto(url.value?.text ?: fakeUrl)
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