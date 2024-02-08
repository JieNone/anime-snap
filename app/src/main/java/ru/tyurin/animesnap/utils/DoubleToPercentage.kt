package ru.tyurin.animesnap.utils

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import ru.tyurin.animesnap.R
import java.text.DecimalFormat

@Composable
fun DoubleToPercentage(number: Double) {


    val decimalFormat = DecimalFormat("#.##%")
    val percentage = decimalFormat.format(number)

    Text(text = stringResource(R.string.probability, percentage), fontSize = 32.sp)
}