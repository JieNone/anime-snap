package ru.tyurin.animesnap.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchScreen() {
    // Создаем состояние для хранения URL
    val urlState = remember { mutableStateOf(TextFieldValue()) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Поиск по URL",
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 24.sp,
            modifier = Modifier.padding(vertical = 20.dp)
        )

        // Поле ввода URL
        OutlinedTextField(
            value = urlState.value,
            onValueChange = { urlState.value = it },
            placeholder = { Text("Введите URL") },
            modifier = Modifier.padding(horizontal = 20.dp),
            singleLine = true
        )

        // Кнопка "Искать!"
        Button(
            onClick = {  },
            modifier = Modifier.padding(vertical = 20.dp)
        ) {
            Text("Искать!")
        }
    }
}

@Preview
@Composable
fun PreviewComposableScreen() {
    SearchScreen()
}
