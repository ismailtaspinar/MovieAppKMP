package com.ismailtaspinar.movieAppKmp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Modern Color Palette
object AppColors {
    // Primary Colors
    val primary = Color(0xFF00D4AA)
    val primaryVariant = Color(0xFF00B894)
    val primaryDark = Color(0xFF00A085)

    // Secondary Colors
    val secondary = Color(0xFF74B9FF)
    val secondaryVariant = Color(0xFF0984E3)
    val accent = Color(0xFFE17055)
    val accentVariant = Color(0xFFD63031)

    // Background Colors
    val background = Color(0xFF0B0D17)
    val backgroundVariant = Color(0xFF0F1219)
    val surface = Color(0xFF1A1D2E)
    val surfaceVariant = Color(0xFF252A3F)
    val surfaceElevated = Color(0xFF2D3348)
    val surfaceHighest = Color(0xFF363B52)

    // Text Colors
    val onPrimary = Color(0xFF000000)
    val onSecondary = Color(0xFFFFFFFF)
    val onBackground = Color(0xFFE8E8E8)
    val onSurface = Color(0xFFE0E0E0)
    val onSurfaceVariant = Color(0xFFB8B8B8)
    val onSurfaceSecondary = Color(0xFF888888)
    val onSurfaceTertiary = Color(0xFF666666)

    // Status Colors
    val error = Color(0xFFFF6B6B)
    val errorVariant = Color(0xFFE55656)
    val success = Color(0xFF51CF66)
    val successVariant = Color(0xFF40C057)
    val warning = Color(0xFFFFD43B)
    val warningVariant = Color(0xFFFAB005)
    val info = Color(0xFF339AF0)
    val infoVariant = Color(0xFF228BE6)

    // Gradient Colors
    val gradientStart = Color(0xFF0B0D17)
    val gradientMiddle = Color(0xFF1A1D2E)
    val gradientEnd = Color(0xFF252A3F)

    // Special Colors
    val shimmer = Color(0xFF2A2D42)
    val overlay = Color(0x80000000)
    val cardBorder = Color(0xFF2A2D42)
    val divider = Color(0xFF2A2D42)
}

// KMP uyumlu Typography - system fontları kullanıyoruz
val AppTypography = Typography(
    // Display Styles
    displayLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp,
        color = AppColors.onSurface
    ),
    displayMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp,
        color = AppColors.onSurface
    ),
    displaySmall = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp,
        color = AppColors.onSurface
    ),

    // Headline Styles
    headlineLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp,
        color = AppColors.onSurface
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp,
        color = AppColors.onSurface
    ),
    headlineSmall = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp,
        color = AppColors.onSurface
    ),

    // Title Styles
    titleLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
        color = AppColors.onSurface
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.1.sp,
        color = AppColors.onSurface
    ),
    titleSmall = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
        color = AppColors.onSurface
    ),

    // Body Styles
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = AppColors.onSurface
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp,
        color = AppColors.onSurfaceVariant
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp,
        color = AppColors.onSurfaceSecondary
    ),

    // Label Styles
    labelLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
        color = AppColors.onSurface
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
        color = AppColors.onSurfaceVariant
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.5.sp,
        color = AppColors.onSurfaceSecondary
    )
)

// Custom Text Styles for specific use cases
object CustomTextStyles {
    val movieTitle = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.sp,
        color = AppColors.onSurface
    )

    val movieSubtitle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.1.sp,
        color = AppColors.onSurfaceVariant
    )

    val cardTitle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp,
        color = AppColors.onSurface
    )

    val badge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.3.sp,
        color = AppColors.onPrimary
    )

    val caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.4.sp,
        color = AppColors.onSurfaceTertiary
    )

    val overline = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 10.sp,
        lineHeight = 12.sp,
        letterSpacing = 1.5.sp,
        color = AppColors.onSurfaceVariant
    )

    val buttonText = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.1.sp,
        color = AppColors.onPrimary
    )
}

// Gradient Definitions
object AppGradients {
    val primaryGradient = listOf(
        AppColors.primary,
        AppColors.primaryVariant
    )

    val secondaryGradient = listOf(
        AppColors.secondary,
        AppColors.secondaryVariant
    )

    val backgroundGradient = listOf(
        AppColors.gradientStart,
        AppColors.gradientMiddle,
        AppColors.gradientEnd
    )

    val surfaceGradient = listOf(
        AppColors.surface,
        AppColors.surfaceVariant
    )

    val overlayGradient = listOf(
        Color.Transparent,
        AppColors.overlay
    )
}

// Material 3 Dark Color Scheme for consistency
val DarkColorScheme = androidx.compose.material3.darkColorScheme(
    primary = AppColors.primary,
    onPrimary = AppColors.onPrimary,
    primaryContainer = AppColors.primaryVariant,
    onPrimaryContainer = AppColors.onPrimary,

    secondary = AppColors.secondary,
    onSecondary = AppColors.onSecondary,
    secondaryContainer = AppColors.secondaryVariant,
    onSecondaryContainer = AppColors.onSecondary,

    tertiary = AppColors.accent,
    onTertiary = AppColors.onSecondary,
    tertiaryContainer = AppColors.accentVariant,
    onTertiaryContainer = AppColors.onSecondary,

    error = AppColors.error,
    onError = AppColors.onSecondary,
    errorContainer = AppColors.errorVariant,
    onErrorContainer = AppColors.onSecondary,

    background = AppColors.background,
    onBackground = AppColors.onBackground,
    surface = AppColors.surface,
    onSurface = AppColors.onSurface,
    surfaceVariant = AppColors.surfaceVariant,
    onSurfaceVariant = AppColors.onSurfaceVariant,

    outline = AppColors.cardBorder,
    outlineVariant = AppColors.divider,

    scrim = AppColors.overlay
)