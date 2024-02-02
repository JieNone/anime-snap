package ru.tyurin.animesnap.data.utils

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import java.text.DecimalFormat

@Composable
fun DoubleToPercentage(number: Double) {


    val decimalFormat = DecimalFormat("#.##%")
    val percentage = decimalFormat.format(number)

    Text(text = "Вероятность $percentage %")
}