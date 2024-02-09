package ru.tyurin.animesnap.ui.screens.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.tyurin.animesnap.R
import ru.tyurin.animesnap.viewmodels.TitleViewModel

@Composable
fun SearchScreen(
    titleViewModel: TitleViewModel = hiltViewModel(),
    onNavigateToHomeScreen: () -> Unit
) {
    val urlState = remember { mutableStateOf(TextFieldValue()) }
    var isLoading by remember { mutableStateOf(false)}
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.search_by_url),
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 24.sp,
            modifier = Modifier.padding(vertical = 20.dp)
        )

        OutlinedTextField(
            value = urlState.value,
            onValueChange = { urlState.value = it },
            placeholder = { Text(stringResource(R.string.enter_the_url)) },
            modifier = Modifier.padding(horizontal = 20.dp),
            singleLine = true
        )


        Button(
            onClick = {
                val destination = urlState.value
                if (destination.text.isNotBlank() && !isLoading) {
                    isLoading = true
                    titleViewModel.updateUrl(urlState.value)
                    titleViewModel.getTitleByUrl()
                    onNavigateToHomeScreen()
                }

            },
            modifier = Modifier.padding(vertical = 20.dp),
            enabled = !isLoading
        ) {
            Text(stringResource(R.string.find_this))
        }
        ImagePicker()
    }
}