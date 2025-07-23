package com.cabovianco.todo.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.cabovianco.todo.R

val BitcountRegular = FontFamily(
    Font(R.font.bitcount_grid_double_roman_regular)
)

val Typography = Typography(
    titleLarge = TextStyle(
        fontSize = 36.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = BitcountRegular
    )
)
