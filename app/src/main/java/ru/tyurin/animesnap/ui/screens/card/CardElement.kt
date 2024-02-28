package ru.tyurin.animesnap.ui.screens.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.tyurin.animesnap.domain.models.Result
import ru.tyurin.animesnap.ui.screens.welcome.RenderResult

@Composable
fun Element(results: List<Result>, modifier: Modifier = Modifier) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Column(modifier = modifier.padding(16.dp)) {
            results.forEach { result ->
                RenderResult(result)
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
