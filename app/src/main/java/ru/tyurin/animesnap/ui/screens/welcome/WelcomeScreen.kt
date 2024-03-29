package ru.tyurin.animesnap.ui.screens.welcome

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
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
import ru.tyurin.animesnap.ui.screens.card.Element
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
    val context = LocalContext.current
    AnimeSnapTheme {
        val isVisible = rememberSaveable { mutableStateOf(true) }
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val nestedScrollConnection = remember {
            object : NestedScrollConnection {
                override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                    if (available.y < -1) {
                        isVisible.value = false
                    }
                    if (available.y > 1) {
                        isVisible.value = true
                    }
                    return Offset.Zero               }
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
        ) {paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(nestedScrollConnection),
            ) {
                when (uiState) {
                    is Success -> TitlesGridScreen((uiState as Success).animeTitle, contentPadding = paddingValues)
                    is AnimeUiState.Error -> BlankScreen()
                    is AnimeUiState.Loading -> BlankScreen()
                }
            }
        }

    }
}

@Composable
fun TitlesGridScreen(
    photos: AnimeTitle,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        contentPadding = contentPadding
    ) {
        items(photos.result) { result ->
            Element(results = listOf(result))
        }
    }
}



@Composable
fun RenderResult(result: Result) {
    result.let {
        Text(
            text = "${it.anilist.title.english} / ${it.anilist.title.native}",
//                    "  Эпизод: ${it.episode}, ${timeCodeFormatter(it.from, it.to)}",
            modifier = Modifier.fillMaxWidth(),
            fontSize = 16.sp,
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