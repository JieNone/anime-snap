package ru.tyurin.animesnap.ui.screens.welcome

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.tyurin.animesnap.R
import ru.tyurin.animesnap.domain.models.AnimeTitle
import ru.tyurin.animesnap.domain.models.Result
import ru.tyurin.animesnap.ui.screens.search.PickImage
import ru.tyurin.animesnap.ui.theme.AnimeSnapTheme
import ru.tyurin.animesnap.utils.AnimeUiState
import ru.tyurin.animesnap.utils.AnimeUiState.Success
import ru.tyurin.animesnap.utils.DoubleToPercentage
import ru.tyurin.animesnap.viewmodels.UploadViewModel


@Composable
fun WelcomeScreen(
    viewModel: UploadViewModel = hiltViewModel()
) {

    val uiState: AnimeUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    AnimeSnapTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                PickImage(viewModel, context)
                MostSimilarTo()
                    if (uiState is Success) {
                        TitlesGridScreen((uiState as Success).animeTitle)
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
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(0.dp),
    ) {
        Row(
            modifier = modifier
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
}

@Composable
private fun RenderResult(result: Result) {
    result.filename?.let {
        Text(
            text = it,
            modifier = Modifier.fillMaxWidth(),
            fontSize = 18.sp,
        )
    }
    result.similarity?.let { DoubleToPercentage(number = it) }
    result.imgSrc?.let { RenderImage(it) }
}

@Composable
private fun RenderImage(imgSrc: String) {
    AsyncImage(
        model = ImageRequest.Builder(context = LocalContext.current)
            .data(imgSrc)
            .crossfade(true)
            .build(),
        contentDescription = stringResource(R.string.title_photo),
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxWidth(),
        error = painterResource(id = R.drawable.ic_broken_image),
        placeholder = painterResource(id = R.drawable.loading_img)
    )
}


