package fr.isen.moratille.thegreatestcocktailapp.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import fr.isen.moratille.thegreatestcocktailapp.R

// On mappe les couleurs du XML vers des variables Compose réutilisables
val DarkBackground @Composable get() = colorResource(R.color.dark_background)
val CardBackground @Composable get() = colorResource(R.color.card_background)
val PrimaryOrange @Composable get() = colorResource(R.color.primary_orange)
val SecondaryOrange @Composable get() = colorResource(R.color.secondary_orange)
val AccentOrange @Composable get() = colorResource(R.color.accent_orange)
val TextGray @Composable get() = colorResource(R.color.text_gray)
val TextWhite = Color(0xFFFFFFFF)
val SurfaceGray @Composable get() = colorResource(R.color.surface_gray)

// Glassmorphism
val GlassWhite @Composable get() = colorResource(R.color.glass_white)
val GlassWhiteStrong @Composable get() = colorResource(R.color.glass_white_strong)
val GlassBorder @Composable get() = colorResource(R.color.glass_border)
