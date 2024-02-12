package ru.tyurin.animesnap.viewmodels

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
import ru.tyurin.animesnap.domain.repository.AnimeTitleRepository
import ru.tyurin.animesnap.utils.AnimeUiState
import ru.tyurin.animesnap.utils.SharedPreferencesKeys
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
        uiState = AnimeUiState.Loading
        titleJob = viewModelScope.launch {
            val fakeUrl = SharedPreferencesKeys.FAKE_IMG_URL
            val url = url.value?.text ?: fakeUrl

            uiState = try {
                AnimeUiState.Success(
                    repository.getByUrl(url)
                )
            } catch (e: IOException) {
                AnimeUiState.Error
            } catch (e: NullPointerException) {
                AnimeUiState.Error
            } catch (e: retrofit2.HttpException) {
                if (e.code() == 400) {
                    AnimeUiState.Error
                } else {
                    AnimeUiState.Error
                }
            }
        }
    }
    override fun onCleared() {
        super.onCleared()
        titleJob?.cancel()
    }

}