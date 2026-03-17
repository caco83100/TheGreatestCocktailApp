package fr.isen.moratille.thegreatestcocktailapp.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun TheGreatestCocktailAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // On désactive les couleurs dynamiques pour garder notre style "Café/Cocktail"
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    // Définition du ColorScheme à l'intérieur de la fonction @Composable
    // pour pouvoir accéder aux couleurs liées aux ressources XML
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
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
