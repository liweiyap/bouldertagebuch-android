package com.liweiyap.bouldertagebuch.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val appLightColorScheme = lightColorScheme(
    primary = AppColor.backgroundLight,
    onPrimary = AppColor.textLight,
    secondary = AppColor.systemNavigationBar,
    background = AppColor.backgroundLight,
    onBackground = AppColor.textLight,
    surface = AppColor.bubbleLight,
    onSurface = AppColor.textLight,
)

private val appDarkColorScheme = darkColorScheme(
    primary = AppColor.backgroundDark,
    onPrimary = AppColor.textDark,
    secondary = AppColor.systemNavigationBar,
    background = AppColor.backgroundDark,
    onBackground = AppColor.textDark,
    surface = AppColor.bubbleDark,
    onSurface = AppColor.textDark,
)

@Composable
fun AppTheme(
    isDarkMode: Boolean = isSystemInDarkTheme(),
    doAllowDynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val appColorScheme: ColorScheme = when {
        ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) && doAllowDynamicColor) -> {
            val context = LocalContext.current
            if (isDarkMode) {
                dynamicDarkColorScheme(context)
            }
            else {
                dynamicLightColorScheme(context)
            }
        }
        isDarkMode -> appDarkColorScheme
        else -> appLightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = appColorScheme.primary.toArgb()
            window.navigationBarColor = appColorScheme.secondary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !isDarkMode
        }
    }

    MaterialTheme(
        colorScheme = appColorScheme,
        typography = appTypography,
        content = content,
    )
}