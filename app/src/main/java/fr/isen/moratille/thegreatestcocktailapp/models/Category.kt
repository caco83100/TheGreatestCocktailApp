package fr.isen.moratille.thegreatestcocktailapp.models

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import fr.isen.moratille.thegreatestcocktailapp.R

enum class Category {
    BEER,
    COCKTAIL,
    COCOA,
    COFFEE,
    LIQUOR,
    DRINK,
    PUNCH,
    SHAKE,
    SHOT,
    SOFT,
    ALCOHOLIC,
    NON_ALCOHOLIC,
    OTHER;

    companion object {
        fun allObjects(): List<Category> {
            return listOf(
                BEER,
                COCKTAIL,
                COCOA,
                COFFEE,
                LIQUOR,
                DRINK,
                PUNCH,
                SHAKE,
                SHOT,
                SOFT,
                OTHER
            )
        }
        fun toString(category: Category): String {
            return when(category) {
                ALCOHOLIC -> "Alcoholic"
                NON_ALCOHOLIC -> "Non alcoholic"
                OTHER -> "Other / Unknown"
                BEER -> "Beer"
                COCKTAIL -> "Cocktail"
                COCOA -> "Cocoa"
                COFFEE -> "Coffee"
                LIQUOR -> "Homemade Liquor"
                DRINK -> "Ordinary Drink"
                PUNCH -> "Punch / Party Drink"
                SHAKE -> "Shake"
                SHOT -> "Shot"
                SOFT -> "Soft Drink"
            }
        }

        @Composable
        fun colors(category: Category): List<Color> {
            return when(category) {
                ALCOHOLIC -> listOf(
                    colorResource(R.color.cocktail_orange),
                    colorResource(R.color.cocktail_dark_orange)
                )

                NON_ALCOHOLIC -> listOf(
                    colorResource(R.color.cocktail_yellow),
                    colorResource(R.color.cocktail_orange)
                )

                OTHER -> listOf(
                    colorResource(R.color.cocktail_pink),
                    colorResource(R.color.cocktail_orange)
                )
                BEER -> listOf(
                    colorResource(R.color.cocktail_yellow),
                    colorResource(R.color.cocktail_orange)
                )
                COCKTAIL -> listOf(
                    colorResource(R.color.cocktail_pink),
                    colorResource(R.color.cocktail_orange)
                )
                COCOA -> listOf(
                    colorResource(R.color.cocktail_pink),
                    colorResource(R.color.cocktail_orange)
                )
                COFFEE -> listOf(
                    colorResource(R.color.cocktail_pink),
                    colorResource(R.color.cocktail_orange)
                )
                LIQUOR -> listOf(
                    colorResource(R.color.cocktail_orange),
                    colorResource(R.color.cocktail_dark_orange)
                )
                DRINK -> listOf(
                    colorResource(R.color.cocktail_yellow),
                    colorResource(R.color.cocktail_orange)
                )
                PUNCH -> listOf(
                    colorResource(R.color.cocktail_pink),
                    colorResource(R.color.cocktail_orange)
                )
                SHAKE -> listOf(
                    colorResource(R.color.cocktail_pink),
                    colorResource(R.color.cocktail_orange)
                )
                SHOT -> listOf(
                    colorResource(R.color.cocktail_orange),
                    colorResource(R.color.cocktail_dark_orange)
                )
                SOFT -> listOf(
                    colorResource(R.color.cocktail_yellow),
                    colorResource(R.color.cocktail_orange)
                )
            }
        }
    }
}