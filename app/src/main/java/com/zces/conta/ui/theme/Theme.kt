package com.zces.conta.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

private val Color4Error = androidx.compose.ui.graphics.Color(0xFFB3261E)

// V1 ships a single light theme by design: professional, clean construction aesthetic,
// light background, dark/navy text, restrained orange accents. No dark theme in scope for V1.
private val ZCesLightColorScheme = lightColorScheme(
    primary = CesOrange,
    onPrimary = CesWhite,
    primaryContainer = CesOrangeLight,
    onPrimaryContainer = CesBlack,
    secondary = CesGraphite,
    onSecondary = CesWhite,
    background = CesOffWhite,
    onBackground = NavyText,
    surface = CesWhite,
    onSurface = NavyText,
    surfaceVariant = CesLightGrey,
    onSurfaceVariant = CesGraphite,
    error = Color4Error,
    onError = CesWhite,
)

private val ZCesTypography = Typography(
    // Large, legible defaults for one-handed field use in daylight; fine-tune once
    // real screens/content are built (Phase 4+).
    bodyLarge = TextStyle(fontSize = 17.sp, lineHeight = 24.sp),
    titleLarge = TextStyle(fontSize = 22.sp, lineHeight = 28.sp),
    labelLarge = TextStyle(fontSize = 15.sp, lineHeight = 20.sp),
)

@Composable
fun ZCesContaTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = ZCesLightColorScheme,
        typography = ZCesTypography,
        content = content,
    )
}
