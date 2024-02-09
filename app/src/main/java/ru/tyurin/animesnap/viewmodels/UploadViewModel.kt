package ru.tyurin.animesnap.viewmodels

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import ru.tyurin.animesnap.domain.repository.AnimeTitleRepository
import ru.tyurin.animesnap.utils.AnimeUiState
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class UploadViewModel @Inject constructor(
    private val repository: AnimeTitleRepository

) : ViewModel() {
    var uiState: AnimeUiState by mutableStateOf(AnimeUiState.Loading)
        private set

    private val uri = MutableLiveData<Uri?>()

    fun updateUri(newUri: Uri?) {
        uri.value = newUri
    }
//    fun uploadImage(image: MultipartBody.Part) {
//        viewModelScope.launch {
//
//            uiState = try {
//                AnimeUiState.Success(
//                    repository.getFromLocal(image)
//                )
//            } catch (e: IOException) {
//                AnimeUiState.Error
//            } catch (e: NullPointerException) {
//                AnimeUiState.Error
//            } catch (e: retrofit2.HttpException) {
//                if (e.code() == 400) {
//                    AnimeUiState.Error
//                } else {
//                    AnimeUiState.Error
//                }
//            }
//        }
//    }
}