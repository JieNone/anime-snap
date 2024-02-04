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
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import ru.tyurin.animesnap.data.models.AnimeTitle
import ru.tyurin.animesnap.data.repository.AnimeTitleRepository
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class TitleViewModel : ViewModel() {

    @Inject lateinit var animeTitleRepository: AnimeTitleRepository
    var uiState: AnimeUiState by mutableStateOf(AnimeUiState.Loading)
        private set

    private var titleJob : Job? = null

    val url = MutableLiveData<TextFieldValue>()
    fun getUrl() = url.value
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

            val fakeUrl = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.fortressofsolitude.co.za%2Fwp-content%2Fuploads%2F2021%2F06%2F5-Most-Popular-Anime-Characters-of-All-Time.jpg&f=1&nofb=1&ipt=3f8bda4ba5920ca7963c49ed4b302d87062662f2391a1e7ea59832fa222925e8&ipo=images"
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