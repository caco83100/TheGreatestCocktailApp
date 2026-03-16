package fr.isen.moratille.thegreatestcocktailapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.isen.moratille.thegreatestcocktailapp.R

data class Cocktail(
    val name: String,
    val imageRes: Int,
    val categories: List<String>,
    val glassType: String,
    val ingredients: List<String>,
    val recipe: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailCocktailScreen(cocktail: Cocktail) {
    // State of cocktail
    var isFavorite by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(cocktail.name)
                },
                navigationIcon = {
                    IconButton(onClick = { /* TODO  : turn back */ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { isFavorite = !isFavorite }) {
                        Icon(
                            imageVector =   if (isFavorite) Icons.Filled.Favorite
                                            else Icons.Filled.FavoriteBorder,
                            contentDescription =    if (isFavorite) "Remove from favorites"
                                                    else "Add to favorites"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                // Image
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Image(
                        painter = painterResource(id = cocktail.imageRes),
                        contentDescription = cocktail.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            item {
                // Title
                Text(
                    text = cocktail.name,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                // Categories
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    cocktail.categories.forEach { category ->
                        SuggestionChip(
                            onClick = { },
                            label = { Text(category) }
                        )
                    }
                }
            }

            item {
                // Type of glass
                Text(
                    text = "Glass: ${cocktail.glassType}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            item {
                // Card of ingredients
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Ingredients",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        cocktail.ingredients.forEach { ingredient ->
                            Text(text = "• $ingredient", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }

            item {
                // Card of recipe
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Recipe",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = cocktail.recipe,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailCocktailScreenPreview() {
    val sampleCocktail = Cocktail(
        name = "Mojito",
        imageRes = R.drawable.imagecocktail,
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
    DetailCocktailScreen(cocktail = sampleCocktail)
}
