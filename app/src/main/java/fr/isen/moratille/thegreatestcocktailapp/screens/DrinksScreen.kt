package fr.isen.moratille.thegreatestcocktailapp.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import fr.isen.moratille.thegreatestcocktailapp.dataClasses.DrinkPreview
import fr.isen.moratille.thegreatestcocktailapp.ui.theme.GlassBorder
import fr.isen.moratille.thegreatestcocktailapp.ui.theme.GlassWhite

@Composable
fun DrinksScreen(
    drinks: List<DrinkPreview>,
    onDrinkClick: (DrinkPreview) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 24.dp)
    ) {
        items(drinks) { drink ->
            DrinkCard(
                drink = drink,
                onClick = { onDrinkClick(drink) }
            )
        }
    }
}

@Composable
fun DrinkCard(
    drink: DrinkPreview,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.cardColors(containerColor = GlassWhite),
        border = BorderStroke(1.dp, GlassBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                modifier = Modifier
                    .size(120.dp)
                    .padding(8.dp),
                shape = MaterialTheme.shapes.large,
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                AsyncImage(
                    model = drink.strDrinkThumb,
                    contentDescription = drink.strDrink,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            
            Text(
                text = drink.strDrink ?: "",
                modifier = Modifier.padding(start = 12.dp, end = 16.dp),
                color = androidx.compose.ui.graphics.Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
