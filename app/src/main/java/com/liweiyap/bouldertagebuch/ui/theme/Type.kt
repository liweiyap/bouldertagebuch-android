package com.liweiyap.bouldertagebuch.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.liweiyap.bouldertagebuch.R

private val chalkdusterFontFamily: FontFamily = FontFamily(
    Font(R.font.chalkduster, FontWeight.Normal)
)

val appTypography = Typography(
    titleLarge = TextStyle(
        fontFamily = chalkdusterFontFamily,
        fontSize = 22.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = chalkdusterFontFamily,
        fontSize = 17.sp,
    ),
)