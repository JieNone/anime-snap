package ru.tyurin.animesnap.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.tyurin.animesnap.R
import ru.tyurin.animesnap.data.models.AnimeTitle
import ru.tyurin.animesnap.data.models.Result
import ru.tyurin.animesnap.data.utils.DoubleToPercentage
import ru.tyurin.animesnap.ui.theme.AnimeSnapTheme

@Composable
fun ErrorScreen(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(text = stringResource(id = R.string.retry))
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun HomeScreen(
    uiState: TitleViewModel.AnimeUiState,
    modifier: Modifier = Modifier,
    retryAction: () -> Unit,

) {
    when (uiState) {
        is TitleViewModel.AnimeUiState.Success ->
            TitlesGridScreen(photos = uiState.animeTitle, modifier)
        is TitleViewModel.AnimeUiState.Loading ->
            LoadingScreen(modifier = modifier.fillMaxSize())
        is TitleViewModel.AnimeUiState.Error ->
            ErrorScreen(
                retryAction,
                modifier = modifier.fillMaxSize(),
            )
    }
}

@Composable
fun AnimeTitleCard(results: List<Result>, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        results.forEach { result ->
            Text(
                text = result.filename,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 18.sp,

            )
            DoubleToPercentage(number = result.similarity)
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(result.imgSrc)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.title_photo),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth(),
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.loading_img)
            )
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
            .padding(vertical = 4.dp)
            .fillMaxWidth(),
        contentPadding = contentPadding
    ) {
        items(photos.result.take(4)) { result ->
            AnimeTitleCard(results = listOf(result))
        }
    }
}

@Composable
fun ResultScreen(photos: String, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Text(text = photos)
    }
}

@Preview(showBackground = true)
@Composable
fun ResultScreenPreview() {
    AnimeSnapTheme {
        ResultScreen(stringResource(R.string.placeholder_result))
    }
}