package com.liweiyap.bouldertagebuch.ui.theme

import androidx.compose.ui.graphics.Color
import com.liweiyap.bouldertagebuch.ui.components.HistoryHeatMapQuartile

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

    private val heatMapBlankLight: Color = Color(red = 235F/255F, green = 237F/255F, blue = 240F/255F)
    private val heatMapQuartile1Light: Color = Color(red = 255F/255F, green = 238F/255F, blue = 74F/255F)
    private val heatMapQuartile2Light: Color = Color(red = 255F/255F, green = 197F/255F, blue = 1F/255F)
    private val heatMapQuartile3Light: Color = Color(red = 254F/255F, green = 150F/255F, blue = 1F/255F)
    private val heatMapQuartile4Light: Color = Color(red = 3F/255F, green = 0F/255F, blue = 28F/255F)

    private val heatMapBlankDark: Color = Color(red = 22F/255F, green = 27F/255F, blue = 34F/255F)
    private val heatMapQuartile1Dark: Color = Color(red = 99F/255F, green = 28F/255F, blue = 3F/255F)
    private val heatMapQuartile2Dark: Color = Color(red = 189F/255F, green = 86F/255F, blue = 29F/255F)
    private val heatMapQuartile3Dark: Color = Color(red = 250F/255F, green = 122F/255F, blue = 24F/255F)
    private val heatMapQuartile4Dark: Color = Color(red = 253F/255F, green = 223F/255F, blue = 104F/255F)

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

    fun getBorderColorFromRouteColorName(isSystemInDarkTheme: Boolean): Color {
        return if (isSystemInDarkTheme) routeWhiteDark else routeBlackLight
    }

    fun getHeatMapColor(quartile: HistoryHeatMapQuartile, isSystemInDarkTheme: Boolean): Color {
        return when (quartile) {
            HistoryHeatMapQuartile.NONE -> if (isSystemInDarkTheme) heatMapBlankDark else heatMapBlankLight
            HistoryHeatMapQuartile.FIRST -> if (isSystemInDarkTheme) heatMapQuartile1Dark else heatMapQuartile1Light
            HistoryHeatMapQuartile.SECOND -> if (isSystemInDarkTheme) heatMapQuartile2Dark else heatMapQuartile2Light
            HistoryHeatMapQuartile.THIRD -> if (isSystemInDarkTheme) heatMapQuartile3Dark else heatMapQuartile3Light
            HistoryHeatMapQuartile.FOURTH -> if (isSystemInDarkTheme) heatMapQuartile4Dark else heatMapQuartile4Light
        }
    }
}