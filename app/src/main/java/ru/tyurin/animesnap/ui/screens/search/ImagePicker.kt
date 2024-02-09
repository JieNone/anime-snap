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
import ru.tyurin.animesnap.R

@Composable
fun ImagePicker() {
    val context = LocalContext.current
    val pickMedia = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia(), onResult = { uri ->
        if (uri != null) {
            Log.d("PhotoPicker", "Selected URI: $uri")
            val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
            context.contentResolver.takePersistableUriPermission(uri, flag)
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

