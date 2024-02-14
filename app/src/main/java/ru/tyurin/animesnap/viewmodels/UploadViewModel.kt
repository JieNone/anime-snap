package ru.tyurin.animesnap.viewmodels

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.tyurin.animesnap.domain.repository.AnimeTitleRepository
import ru.tyurin.animesnap.utils.AnimeUiState
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class UploadViewModel @Inject constructor(
    private val repository: AnimeTitleRepository

) : ViewModel() {
    private val pickedImage = MutableLiveData<Uri?>()

    var uiState: AnimeUiState by mutableStateOf(AnimeUiState.Loading)
        private set

    var state: MutableStateFlow<AnimeUiState> = MutableStateFlow(AnimeUiState.Loading)
    fun updatePickedImage(uri: Uri?) {
        pickedImage.value = uri
    }
    fun getTitleFromLocalStorage() {
        state.value = AnimeUiState.Loading
        viewModelScope.launch {
            val image = pickedImage.value
            state.value = try {
                AnimeUiState.Success(
                    repository.getDataFromServer(image!!)
                )
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