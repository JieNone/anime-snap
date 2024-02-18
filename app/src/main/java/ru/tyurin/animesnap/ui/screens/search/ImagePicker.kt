package ru.tyurin.animesnap.ui.screens.search

import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import ru.tyurin.animesnap.R
import ru.tyurin.animesnap.viewmodels.UploadViewModel
import java.io.File

@Composable
fun PickImage(
    viewModel: UploadViewModel,
    context: Context
) {
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

                Log.d("PICK_IMAGE", "Selected image: $file")
                viewModel.uploadImage(file)
            }
        }
    )

    Button(
        onClick = { galleryLauncher.launch("image/*") },
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
    ) {
        Text(text = stringResource(id = R.string.select_image))
    }
}
