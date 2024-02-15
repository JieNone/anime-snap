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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
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
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.tyurin.animesnap.R
import ru.tyurin.animesnap.domain.models.AnimeTitle
import ru.tyurin.animesnap.domain.models.Result
import ru.tyurin.animesnap.ui.screens.search.PickImage
import ru.tyurin.animesnap.ui.theme.AnimeSnapTheme
import ru.tyurin.animesnap.utils.AnimeUiState
import ru.tyurin.animesnap.utils.DoubleToPercentage
import ru.tyurin.animesnap.viewmodels.UploadViewModel


@Composable
fun WelcomeScreen(
    viewModel: UploadViewModel = hiltViewModel()
) {
    val serverResponseState by viewModel.state.collectAsState()
    val context = LocalContext.current
    AnimeSnapTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                PickImage(viewModel, context)
                MostSimilarTo()
                when (serverResponseState) {
                    is AnimeUiState.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                    }
                    is AnimeUiState.Success -> {
                        TitlesGridScreen((serverResponseState as AnimeUiState.Success).animeTitle)
                    }
                    is AnimeUiState.Error -> {
                        Text("Ошибка при загрузке данных")
                    }
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
        items(photos.result.take(4)) { result ->
            Element(results = listOf(result))
        }
    }
}

@Composable
fun Element(results: List<Result>, modifier: Modifier = Modifier) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(0.dp),
    ) {
        Row(
            modifier = Modifier
        ) {
            Column(modifier = modifier
                .padding(top = 12.dp, start = 16.dp)
                .fillMaxSize()
            ) {
                results.forEach { result ->
                    result.filename?.let {
                        Text(
                            text = it,
                            modifier = Modifier.fillMaxWidth(),
                            fontSize = 18.sp,
                        )
                    }
                    result.similarity?.let { DoubleToPercentage(number = it) }
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
    }
}

