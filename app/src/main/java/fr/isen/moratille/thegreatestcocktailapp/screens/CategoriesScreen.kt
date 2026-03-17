package fr.isen.moratille.thegreatestcocktailapp.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.isen.moratille.thegreatestcocktailapp.ui.theme.GlassBorder
import fr.isen.moratille.thegreatestcocktailapp.ui.theme.GlassWhite
import fr.isen.moratille.thegreatestcocktailapp.ui.theme.PrimaryOrange

@Composable
fun CategoriesScreen(
    categories: List<String>,
    onCategoryClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    // On rend le Scaffold transparent pour voir le fond d'écran de l'activité
    Scaffold(
        containerColor = Color.Transparent
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 24.dp)
        ) {
            item {
                Column(modifier = Modifier.padding(bottom = 8.dp)) {
                    Text(
                        text = "Find the best",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Cocktail for you",
                        style = MaterialTheme.typography.headlineMedium,
                        color = PrimaryOrange,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            items(categories) { categoryName ->
                CategoryCard(
                    categoryName = categoryName,
                    onClick = { onCategoryClick(categoryName) }
                )
            }
        }
    }
}

@Composable
fun CategoryCard(
    categoryName: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.cardColors(containerColor = GlassWhite),
        border = BorderStroke(1.dp, GlassBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = categoryName,
                modifier = Modifier.padding(start = 24.dp),
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge
            )
            
            // Subtle orange indicator on the left
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .width(6.dp)
                    .fillMaxHeight(0.4f)
                    .background(PrimaryOrange, MaterialTheme.shapes.small)
            )
        }
    }
}
