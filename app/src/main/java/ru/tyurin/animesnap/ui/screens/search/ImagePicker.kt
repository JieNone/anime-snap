package ru.tyurin.animesnap.ui.screens.search

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import ru.tyurin.animesnap.R
import ru.tyurin.animesnap.viewmodels.UploadViewModel
import java.io.File


@Composable
fun PickImage(
    viewModel: UploadViewModel,
    context: Context
) {
    var imageFile by remember {
        mutableStateOf<File?>(null)
    }
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let { selectedUri ->
                val contentResolver = context.contentResolver
                val inputStream = contentResolver.openInputStream(selectedUri)
                val fileName = "image_${System.currentTimeMillis()}.jpg"
                val file = File(context.cacheDir, fileName)
                inputStream?.use { input ->
                    file.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }
                imageFile = file
            }
            viewModel.uploadImage(imageFile!!)
        }
    )
    Column {
        Button(onClick ={
            galleryLauncher.launch("image/*")

        }) {
            Text(text = stringResource(id = R.string.select_image))
        }
    }
}


