package GabrielJS2005.com.github.sprint03_nutrizapp.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary            = BluePetroleum,
    onPrimary          = Color.White,
    primaryContainer   = BluePetroleumSoft,
    onPrimaryContainer = BluePetroleumDark,

    secondary            = GreenWater,
    onSecondary          = Color.White,
    secondaryContainer   = GreenWaterSoft,
    onSecondaryContainer = Color(0xFF1A4E4A),

    tertiary            = CoralSoft,
    onTertiary          = Color.White,
    tertiaryContainer   = CoralSoftBg,
    onTertiaryContainer = Color(0xFF7A2F21),

    background  = OffWhite,
    onBackground = TextDark,

    surface         = SurfaceWhite,
    onSurface       = TextDark,
    surfaceVariant  = BluePetroleumSoft,
    onSurfaceVariant = TextGrayLight,

    outline       = BorderColor,
    outlineVariant = InputBackground,

    error    = ErrorRed,
    onError  = Color.White,
    errorContainer = ErrorRedSoft,
    onErrorContainer = Color(0xFF6B1A14),

    inverseSurface = TextDark,
    inverseOnSurface = OffWhite,
    inversePrimary = Color(0xFF7EC8D8),

    scrim = Color.Black.copy(alpha = 0.32f),
)

private val DarkColorScheme = darkColorScheme(
    primary            = Color(0xFF7EC8D8),
    onPrimary          = Color(0xFF003F4E),
    primaryContainer   = Color(0xFF00566B),
    onPrimaryContainer = Color(0xFFB8E8F3),

    secondary            = Color(0xFF76D2CC),
    onSecondary          = Color(0xFF003735),
    secondaryContainer   = Color(0xFF004F4C),
    onSecondaryContainer = Color(0xFF9CEEED),

    tertiary            = Color(0xFFFFAA9A),
    onTertiary          = Color(0xFF5D1B0F),
    tertiaryContainer   = Color(0xFF7A3023),
    onTertiaryContainer = Color(0xFFFFDAD5),

    background   = DarkBackground,
    onBackground = DarkForeground,

    surface         = DarkSurface,
    onSurface       = DarkForeground,
    surfaceVariant  = DarkBorder,
    onSurfaceVariant = Color(0xFFB0CDD8),

    outline       = Color(0xFF4A6E80),
    outlineVariant = DarkBorder,

    error    = Color(0xFFFF8A82),
    onError  = Color(0xFF690005),
)

@Composable
fun SPRINT03NutrizAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            var context = view.context
            while (context is android.content.ContextWrapper) {
                if (context is Activity) break
                context = context.baseContext
            }
            val window = (context as? Activity)?.window
            if (window != null) {
                // Fundo off-white no status bar, ícones escuros
                window.statusBarColor = OffWhite.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography  = Typography,
        content     = content
    )
}
