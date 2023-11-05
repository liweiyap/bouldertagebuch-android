package com.liweiyap.bouldertagebuch.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.liweiyap.bouldertagebuch.R

private val chalkdusterFontFamily: FontFamily = FontFamily(
    Font(R.font.chalkduster, FontWeight.Normal)
)

private fun getBaseTextStyle(fontSize: TextUnit): TextStyle {
    return TextStyle(
        fontFamily = chalkdusterFontFamily,
        fontSize = fontSize,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false,
        ),
    )
}

val appTypography = Typography(
    titleLarge = getBaseTextStyle(fontSize = 22.sp),
    titleMedium = getBaseTextStyle(fontSize = 17.sp),
)