package fr.isen.moratille.thegreatestcocktailapp.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import fr.isen.moratille.thegreatestcocktailapp.dataClasses.Drink
import fr.isen.moratille.thegreatestcocktailapp.ui.theme.GlassBorder
import fr.isen.moratille.thegreatestcocktailapp.ui.theme.GlassWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailCocktailScreen(
    drink: Drink,
    showTopBar: Boolean = true,
    isFavorite: Boolean = false,
    onBackClick: (() -> Unit)? = null,
    onFavoriteClick: (() -> Unit)? = null,
    onRefreshClick: (() -> Unit)? = null
) {
    // On rend le Scaffold transparent pour voir l'image de fond
    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            if (showTopBar) {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent, // Barre transparente
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text(drink.strDrink ?: "Cocktail Details")
                    },
                    navigationIcon = {
                        if (onBackClick != null) {
                            IconButton(onClick = onBackClick) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        }
                    },
                    actions = {
                        if (onFavoriteClick != null) {
                            IconButton(onClick = onFavoriteClick) {
                                Icon(
                                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                    contentDescription = "Favorite",
                                    tint = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        if (onRefreshClick != null) {
                            IconButton(onClick = onRefreshClick) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = "Refresh"
                                )
                            }
                        }
                    }
                )
            }
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
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    shape = MaterialTheme.shapes.extraLarge,
                    colors = CardDefaults.cardColors(containerColor = GlassWhite),
                    border = BorderStroke(1.dp, GlassBorder),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    AsyncImage(
                        model = drink.strDrinkThumb,
                        contentDescription = drink.strDrink,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            item {
                Text(
                    text = drink.strDrink ?: "",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                drink.strCategory?.let {
                    SuggestionChip(
                        onClick = { },
                        label = { Text(it) },
                        colors = SuggestionChipDefaults.suggestionChipColors(
                            containerColor = GlassWhite,
                            labelColor = Color.White
                        ),
                        border = BorderStroke(1.dp, GlassBorder)
                    )
                }
            }

            item {
                Text(
                    text = "Glass: ${drink.strGlass ?: "Standard"}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary // Utilisation du orange pour le contraste
                )
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.extraLarge,
                    colors = CardDefaults.cardColors(containerColor = GlassWhite),
                    border = BorderStroke(1.dp, GlassBorder),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Ingredients",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        drink.ingredientList().forEach { (ingredient, measure) ->
                            Text(
                                text = "• $ingredient ${if (measure.isNotEmpty()) "- $measure" else ""}",
                                color = Color.White,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.extraLarge,
                    colors = CardDefaults.cardColors(containerColor = GlassWhite),
                    border = BorderStroke(1.dp, GlassBorder),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Recipe",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = drink.strInstructions ?: "No instructions available.",
                            color = Color.White,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}
