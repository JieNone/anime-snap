package ru.tyurin.animesnap.ui.screens.search

import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import ru.tyurin.animesnap.R
import ru.tyurin.animesnap.viewmodels.UploadViewModel

@Composable
fun ImagePicker(viewModel: UploadViewModel = hiltViewModel(), onNavigateToHomeScreen: () -> Unit) {
    val context = LocalContext.current
    val pickMedia = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia(), onResult = { uri ->
        if (uri != null) {
            Log.d("PhotoPicker", "Selected URI: $uri")
            val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
            context.contentResolver.takePersistableUriPermission(uri, flag)
            viewModel.updatePickedImage(uri)
            viewModel.getTitleFromLocalStorage()
            onNavigateToHomeScreen()
        } else {
            Log.d("PhotoPicker", "No image selected")
        }
    })
    Column {
        Button(onClick ={
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

        }) {
            Text(text = stringResource(id = R.string.select_image))
        }
    }
}


