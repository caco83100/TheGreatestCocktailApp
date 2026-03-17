package fr.isen.moratille.thegreatestcocktailapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.sharp.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import fr.isen.moratille.thegreatestcocktailapp.dataClasses.CategoryListResponse
import fr.isen.moratille.thegreatestcocktailapp.dataClasses.CocktailResponse
import fr.isen.moratille.thegreatestcocktailapp.dataClasses.Drink
import fr.isen.moratille.thegreatestcocktailapp.dataClasses.DrinkCategory
import fr.isen.moratille.thegreatestcocktailapp.managers.FavoritesManager
import fr.isen.moratille.thegreatestcocktailapp.network.ApiClient
import fr.isen.moratille.thegreatestcocktailapp.screens.CategoriesScreen
import fr.isen.moratille.thegreatestcocktailapp.screens.DetailCocktailScreen
import fr.isen.moratille.thegreatestcocktailapp.screens.FavoritesScreen
import fr.isen.moratille.thegreatestcocktailapp.ui.theme.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    private val favoritesManager = FavoritesManager()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TheGreatestCocktailAppTheme {
                val context = LocalContext.current
                var selectedTab by remember { mutableIntStateOf(1) } // 0: Random, 1: Categories, 2: Favorites
                
                var categories by remember { mutableStateOf<List<DrinkCategory>>(emptyList()) }
                var randomDrink by remember { mutableStateOf<Drink?>(null) }
                var isLoadingRandom by remember { mutableStateOf(false) }
                
                // On utilise l'ID du drink pour savoir s'il est favori de manière réactive
                var isRandomDrinkFavorite by remember { mutableStateOf(false) }
                
                // Liste des favoris pour l'écran dédié
                var favoriteDrinks by remember { mutableStateOf<List<Drink>>(emptyList()) }

                // Fonction pour charger un cocktail aléatoire
                val fetchRandomDrink = {
                    isLoadingRandom = true
                    ApiClient.service.getRandomCocktail().enqueue(object : Callback<CocktailResponse> {
                        override fun onResponse(call: Call<CocktailResponse>, response: Response<CocktailResponse>) {
                            isLoadingRandom = false
                            if (response.isSuccessful) {
                                val newDrink = response.body()?.drinks?.firstOrNull()
                                randomDrink = newDrink
                                isRandomDrinkFavorite = newDrink?.let { favoritesManager.isFavorite(it, context) } ?: false
                            }
                        }
                        override fun onFailure(call: Call<CocktailResponse>, t: Throwable) {
                            isLoadingRandom = false
                            Log.e("MainActivity", "Error fetching random cocktail", t)
                        }
                    })
                }

                // Récupération des catégories et favoris au lancement
                LaunchedEffect(selectedTab) {
                    if (selectedTab == 1 && categories.isEmpty()) {
                        ApiClient.service.getCategories().enqueue(object : Callback<CategoryListResponse> {
                            override fun onResponse(call: Call<CategoryListResponse>, response: Response<CategoryListResponse>) {
                                if (response.isSuccessful) {
                                    categories = response.body()?.drinks ?: emptyList()
                                }
                            }
                            override fun onFailure(call: Call<CategoryListResponse>, t: Throwable) {
                                Log.e("MainActivity", "Error fetching categories", t)
                            }
                        })
                    } else if (selectedTab == 2) {
                        favoriteDrinks = favoritesManager.getFavorites(context)
                    }
                }

                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = R.drawable.background),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Scaffold(
                        containerColor = Color.Transparent,
                        topBar = {
                            TopAppBar(
                                colors = TopAppBarDefaults.topAppBarColors(
                                    containerColor = Color.Transparent,
                                    titleContentColor = PrimaryOrange,
                                ),
                                title = { 
                                    Text(when(selectedTab) {
                                        0 -> "À la une"
                                        1 -> "Catégories"
                                        else -> "Favoris"
                                    }) 
                                },
                                actions = {
                                    if (selectedTab == 0 && randomDrink != null) {
                                        IconButton(onClick = { 
                                            randomDrink?.let { 
                                                favoritesManager.toggleFavorite(it, context)
                                                isRandomDrinkFavorite = favoritesManager.isFavorite(it, context)
                                            }
                                        }) {
                                            Icon(
                                                imageVector = if (isRandomDrinkFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                                contentDescription = "Favorite",
                                                tint = if (isRandomDrinkFavorite) PrimaryOrange else Color.White
                                            )
                                        }
                                        IconButton(onClick = { fetchRandomDrink() }) {
                                            Icon(Icons.Default.Refresh, contentDescription = "Refresh", tint = Color.White)
                                        }
                                    }
                                }
                            )
                        },
                        bottomBar = {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = 24.dp,
                                        end = 24.dp,
                                    )
                                    .navigationBarsPadding(),
                                contentAlignment = Alignment.TopCenter
                            ) {
                                Surface(
                                    color = GlassWhite,
                                    shape = CircleShape,
                                    border = BorderStroke(1.dp, GlassBorder),
                                    tonalElevation = 0.dp
                                ) {
                                    NavigationBar(
                                        containerColor = Color.Transparent,
                                        tonalElevation = 0.dp,
                                        modifier = Modifier.height(80.dp)
                                    ) {
                                        NavigationBarItem(
                                            selected = selectedTab == 0,
                                            onClick = { 
                                                selectedTab = 0
                                                if (randomDrink == null) fetchRandomDrink()
                                            },
                                            icon = { Icon(Icons.Default.Star, contentDescription = "Random") },
                                            label = { Text("À la une") },
                                            colors = NavigationBarItemDefaults.colors(
                                                selectedIconColor = Color.White,
                                                selectedTextColor = Color.White,
                                                unselectedIconColor = TextGray,
                                                unselectedTextColor = TextGray,
                                                indicatorColor = PrimaryOrange.copy(alpha = 0.4f)
                                            )
                                        )
                                        NavigationBarItem(
                                            selected = selectedTab == 1,
                                            onClick = { selectedTab = 1 },
                                            icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Categories") },
                                            label = { Text("Catégories") },
                                            colors = NavigationBarItemDefaults.colors(
                                                selectedIconColor = Color.White,
                                                selectedTextColor = Color.White,
                                                unselectedIconColor = TextGray,
                                                unselectedTextColor = TextGray,
                                                indicatorColor = PrimaryOrange.copy(alpha = 0.4f)
                                            )
                                        )
                                        NavigationBarItem(
                                            selected = selectedTab == 2,
                                            onClick = { selectedTab = 2 },
                                            icon = { Icon(Icons.Filled.Favorite, contentDescription = "Favorites") },
                                            label = { Text("Favoris") },
                                            colors = NavigationBarItemDefaults.colors(
                                                selectedIconColor = Color.White,
                                                selectedTextColor = Color.White,
                                                unselectedIconColor = TextGray,
                                                unselectedTextColor = TextGray,
                                                indicatorColor = PrimaryOrange.copy(alpha = 0.4f)
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    ) { innerPadding ->
                        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
                            when (selectedTab) {
                                0 -> {
                                    if (isLoadingRandom) {
                                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = PrimaryOrange)
                                    } else {
                                        randomDrink?.let {
                                            DetailCocktailScreen(drink = it, showTopBar = false)
                                        }
                                    }
                                }
                                1 -> {
                                    CategoriesScreen(
                                        categories = categories.map { it.strCategory ?: "" },
                                        onCategoryClick = { categoryName ->
                                            val intent = Intent(context, DrinksActivity::class.java)
                                            intent.putExtra("CATEGORY_NAME", categoryName)
                                            context.startActivity(intent)
                                        }
                                    )
                                }
                                2 -> {
                                    FavoritesScreen(
                                        favorites = favoriteDrinks,
                                        onDrinkClick = { drinkId ->
                                            val intent = Intent(context, DetailActivity::class.java)
                                            intent.putExtra("DRINK_ID", drinkId)
                                            context.startActivity(intent)
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
