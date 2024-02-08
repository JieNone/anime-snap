package ru.tyurin.animesnap.viewmodels

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import ru.tyurin.animesnap.domain.repository.AnimeTitleRepository
import ru.tyurin.animesnap.utils.AnimeUiState
import ru.tyurin.animesnap.utils.getPathFromUri
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import javax.inject.Inject

@HiltViewModel
class UploadViewModel @Inject constructor(
    private val repository: AnimeTitleRepository

) : ViewModel() {
    var uiState: AnimeUiState by mutableStateOf(AnimeUiState.Loading)
        private set

    private val uri = MutableLiveData<String>()

    fun updateUri(newUri: Uri?) {
        uri.value = newUri.toString()
    }
    fun uploadImage(image: MultipartBody.Part) {
        viewModelScope.launch {

            uiState = try {
                AnimeUiState.Success(
                    repository.getFromLocal(image)
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

    fun createMultipartBody(uri: Uri, multipartName: String): MultipartBody.Part {
        val documentImage = uri.toString()
        val file = File(uri.path!!)
        val os: OutputStream = BufferedOutputStream(FileOutputStream(file))
        documentImage.compress(Bitmap.CompressFormat.JPEG, 100, os)
        os.close()
        val requestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(name = multipartName, file.name, requestBody)
    }

}