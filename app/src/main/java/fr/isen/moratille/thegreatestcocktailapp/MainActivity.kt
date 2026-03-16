package fr.isen.moratille.thegreatestcocktailapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import fr.isen.moratille.thegreatestcocktailapp.screens.Cocktail
import fr.isen.moratille.thegreatestcocktailapp.screens.DetailCocktailScreen
import fr.isen.moratille.thegreatestcocktailapp.ui.theme.TheGreatestCocktailAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TheGreatestCocktailAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                   DetailCocktailScreen(
                       cocktail = sampleCocktail
                   )
                }
            }
        }
    }
}

//pour tester :
val sampleCocktail = Cocktail(
    name = "Mojito",
    imageRes = R.drawable.imagecocktail, // Use the drawable found in the project
    categories = listOf("Alcoholic", "Refreshing", "Summer"),
    glassType = "Highball glass",
    ingredients = listOf(
        "50ml White Rum",
        "15ml Fresh Lime Juice",
        "2 tsp Sugar",
        "6-8 Mint Leaves",
        "Soda Water"
    ),
    recipe = "Muddle mint leaves with sugar and lime juice. Add rum and top with soda water. Garnish with mint sprig and lime slice."
)