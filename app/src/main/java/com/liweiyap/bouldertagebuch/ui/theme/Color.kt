package com.liweiyap.bouldertagebuch.ui.theme

import androidx.compose.ui.graphics.Color

object AppColor {
    val backgroundLight: Color = Color(red = 246F/255F, green = 246F/255F, blue = 248F/255F)
    val backgroundDark: Color = Color(red = 1F/255F, green = 1F/255F, blue = 1F/255F)

    val bubbleLight: Color = Color(red = 252F/255F, green = 252F/255F, blue = 254F/255F)
    val bubbleDark: Color = Color(red = 23F/255F, green = 23F/255F, blue = 25F/255F)

    val buttonLight: Color = Color(red = 252F/255F, green = 240F/255F, blue = 182F/255F)
    val buttonDark: Color = Color(red = 93F/255F, green = 88F/255F, blue = 59F/255F)
    val buttonDisabledLight: Color = Color(red = 220F/255F, green = 220F/255F, blue = 220F/255F)
    val buttonDisabledDark: Color = Color(red = 57F/255F, green = 57F/255F, blue = 61F/255F)

    val textLight: Color = Color(red = 1F/255F, green = 1F/255F, blue = 1F/255F)
    val textDark: Color = Color(red = 251F/255F, green = 250F/255F, blue = 255F/255F)

    // see also: androidx.compose.material3.NavigationBarItemColors
    val systemNavigationBar: Color = Color.Black

    private val routeBlackLight: Color = Color(red = 47F/255F, green = 47F/255F, blue = 47F/255F)
    private val routeBlackDark: Color = Color(red = 45F/255F, green = 45F/255F, blue = 47F/255F)

    private val routeGreyLight: Color = Color(red = 141F/255F, green = 140F/255F, blue = 145F/255F)
    private val routeGreyDark: Color = Color(red = 154F/255F, green = 153F/255F, blue = 158F/255F)

    private val routeWhiteLight: Color = Color(red = 242F/255F, green = 242F/255F, blue = 242F/255F)
    private val routeWhiteDark: Color = textDark

    private val routeGreen: Color = Color(red = 17F/255F, green = 207F/255F, blue = 111F/255F)
    private val routePurple: Color = Color(red = 107F/255F, green = 136F/255F, blue = 254F/255F)
    private val routeBlue: Color = Color(red = 64F/255F, green = 163F/255F, blue = 254F/255F)
    private val routeOrange: Color = Color(red = 254F/255F, green = 122F/255F, blue = 60F/255F)
    private val routeRed: Color = Color(red = 254F/255F, green = 85F/255F, blue = 71F/255F)
    private val routeYellow: Color = Color(red = 246F/255F, green = 191F/255F, blue = 38F/255F)
    private val routePink: Color = Color(red = 254F/255F, green = 85F/255F, blue = 221F/255F)
    private val routeTurquoise: Color = Color(red = 156F/255F, green = 202F/255F, blue = 203F/255F)

    fun translateRouteColorName(name: String, isSystemInDarkTheme: Boolean): Color {
        return when (name) {
            "grey" -> if (isSystemInDarkTheme) routeGreyLight else routeGreyDark
            "yellow" -> routeYellow
            "green" -> routeGreen
            "purple" -> routePurple
            "pink" -> routePink
            "black" -> if (isSystemInDarkTheme) routeBlackLight else routeBlackDark
            "blue" -> routeBlue
            "red" -> routeRed
            "orange" -> routeOrange
            "white" -> if (isSystemInDarkTheme) routeWhiteLight else routeWhiteDark
            "turquoise" -> routeTurquoise
            else -> Color.Transparent
        }
    }
}