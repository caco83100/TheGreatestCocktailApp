package fr.isen.moratille.thegreatestcocktailapp.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun TheGreatestCocktailAppTheme(
    content: @Composable () -> Unit
) {

    val colorScheme = darkColorScheme(
        primary = PrimaryOrange,
        onPrimary = TextWhite,
        secondary = SecondaryOrange,
        background = DarkBackground,
        surface = CardBackground,
        onBackground = TextWhite,
        onSurface = TextWhite,
        surfaceVariant = SurfaceGray,
        onSurfaceVariant = TextGray
    )

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
