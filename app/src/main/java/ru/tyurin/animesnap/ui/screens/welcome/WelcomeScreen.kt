@file:OptIn(ExperimentalMaterial3Api::class)

package ru.tyurin.animesnap.ui.screens.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.tyurin.animesnap.R
import ru.tyurin.animesnap.ui.theme.AnimeSnapTheme


@Composable
fun WelcomeScreen() {
    AnimeSnapTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                SearchBar()
                MostSimilarTo()
                ElementContainer()
            }
        }
    }
}
@Composable
fun SearchBar() {
    Row(
        modifier = Modifier
            .padding(16.dp),
    ) {
        CardSearch(text = stringResource(id = R.string.enter_the_url)) {

        }
        CardSearch(text = stringResource(id = R.string.select_image)) {

        }
    }
}
@Composable
fun CardSearch(text: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
        ),
        onClick = { onClick },
        shape = RoundedCornerShape(12.dp),
    ){
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp),
            text = text,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
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
fun ElementContainer() {
    Column(modifier = Modifier
        .padding(top = 12.dp, start = 16.dp)
        .fillMaxSize()) {
        Element()
        Element()
        Element()
        Element()
        Element()
    }
}

@Composable
fun Element() {
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
            Column {
                Text("Naruto")
                Row {
                    Text(text = "Эпизод 54")
                    Text(text = "Соответствие 99%")
                }
            }
            Image(
                painter = painterResource(id = R.drawable.sample_image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                alignment = Alignment.CenterEnd,
            )
        }


    }

}
