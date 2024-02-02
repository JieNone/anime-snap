package ru.tyurin.animesnap.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.launch
import ru.tyurin.animesnap.AnimeApp
import ru.tyurin.animesnap.data.models.AnimeTitle
import ru.tyurin.animesnap.data.repository.AnimeTitleRepository
import java.io.IOException


class TitleViewModel(private val animeTitleRepository : AnimeTitleRepository) : ViewModel() {

    var uiState: AnimeUiState by mutableStateOf(AnimeUiState.Loading)
        private set


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
        viewModelScope.launch {

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

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AnimeApp)
                val animeTitleRepository = application.container.animeTitleRepository
                TitleViewModel(animeTitleRepository = animeTitleRepository)
            }
        }
    }

}