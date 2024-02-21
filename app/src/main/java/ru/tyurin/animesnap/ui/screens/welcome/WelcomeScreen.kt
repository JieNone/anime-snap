package ru.tyurin.animesnap.ui.screens.welcome

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import ru.tyurin.animesnap.R
import ru.tyurin.animesnap.domain.models.AnimeTitle
import ru.tyurin.animesnap.domain.models.Result
import ru.tyurin.animesnap.ui.screens.BlankScreen
import ru.tyurin.animesnap.ui.theme.AnimeSnapTheme
import ru.tyurin.animesnap.utils.AnimeUiState
import ru.tyurin.animesnap.utils.AnimeUiState.Success
import ru.tyurin.animesnap.utils.DoubleToPercentage
import ru.tyurin.animesnap.viewmodels.UploadViewModel
import java.io.File


@Composable
fun WelcomeScreen(
    viewModel: UploadViewModel = hiltViewModel()
) {
    AnimeSnapTheme {
//        val isVisible by viewModel.isVisible.collectAsStateWithLifecycle()
        val isVisible = rememberSaveable { mutableStateOf(true) }
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val context = LocalContext.current
        val nestedScrollConnection = remember {
            object : NestedScrollConnection {
                override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                    if (available.y < -1) {
                        isVisible.value = false
                    }

                    if (available.y > 1) {
                        isVisible.value = true
                    }
                    return Offset.Zero
                }
            }
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

                    Log.d("PICK_IMAGE", "Selected image: $file")
                    viewModel.uploadImage(file)
                }
            }
        )
        Scaffold(
            floatingActionButtonPosition = FabPosition.Center,
            floatingActionButton = {
                AnimatedVisibility(
                    visible = isVisible.value,
                    enter = slideInVertically(initialOffsetY = { it * 2 }),
                    exit = slideOutVertically(targetOffsetY = { it * 2 }),
                ) {
                    ExtendedFloatingActionButton(
                        onClick = { galleryLauncher.launch("image/*") }
                    ) {
                        Text(text = stringResource(R.string.select_image))
                    }
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues)
                    .nestedScroll(nestedScrollConnection),
            ) {
                MostSimilarTo()
                when (uiState) {
                    is Success -> TitlesGridScreen((uiState as Success).animeTitle)
                    is AnimeUiState.Error -> BlankScreen()
                    is AnimeUiState.Loading -> BlankScreen()
                }
            }

        }
    }
}
@Composable
fun MostSimilarTo() {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            text = stringResource(R.string.most_similar_to),
            fontWeight = FontWeight.Black,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.titleLarge
        )
}

@Composable
fun TitlesGridScreen(
    photos: AnimeTitle,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        modifier = modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth(),
        contentPadding = contentPadding
    ) {
        items(photos.result) { result ->
            Element(results = listOf(result))
        }
    }
}

@Composable
fun Element(results: List<Result>, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        results.forEach { result ->
            RenderResult(result)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /* Handle button click */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Add To List")
        }
    }
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(top = 12.dp, start = 16.dp)
                .fillMaxSize()
        ) {
            results.forEach { result ->
                RenderResult(result)
            }
        }
    }
}

@Composable
private fun RenderResult(result: Result) {
    result.anilist.title.let {
        Text(
            text = "${it.english} / ${it.native}",
            modifier = Modifier.fillMaxWidth(),
            fontSize = 18.sp,
            fontWeight = FontWeight.Black
        )
    }
    DoubleToPercentage(number = result.similarity)
    result.imgSrc?.let { RenderImage(it) }
}

@Composable
private fun RenderImage(imgSrc: String) {
    Image(
        painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current).data(data = imgSrc).apply(block = fun ImageRequest.Builder.() {
                crossfade(true)
            }).build()
        ),
        contentDescription = stringResource(R.string.title_photo),
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(shape = RoundedCornerShape(8.dp))
    )
}

