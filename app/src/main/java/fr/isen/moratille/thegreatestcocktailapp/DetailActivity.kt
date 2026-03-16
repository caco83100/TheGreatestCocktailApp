package fr.isen.moratille.thegreatestcocktailapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import fr.isen.moratille.thegreatestcocktailapp.dataClasses.CocktailResponse
import fr.isen.moratille.thegreatestcocktailapp.dataClasses.Drink
import fr.isen.moratille.thegreatestcocktailapp.managers.FavoritesManager
import fr.isen.moratille.thegreatestcocktailapp.network.ApiClient
import fr.isen.moratille.thegreatestcocktailapp.screens.DetailCocktailScreen
import fr.isen.moratille.thegreatestcocktailapp.ui.theme.TheGreatestCocktailAppTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : ComponentActivity() {
    private val favoritesManager = FavoritesManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val isRandom = intent.getBooleanExtra("IS_RANDOM", false)
        val drinkId = intent.getStringExtra("DRINK_ID")

        setContent {
            TheGreatestCocktailAppTheme {
                val context = LocalContext.current
                var drink by remember { mutableStateOf<Drink?>(null) }
                var isLoading by remember { mutableStateOf(true) }
                var isFavorite by remember { mutableStateOf(false) }

                fun loadCocktail() {
                    isLoading = true
                    val call = if (isRandom) {
                        ApiClient.service.getRandomCocktail()
                    } else if (drinkId != null) {
                        ApiClient.service.getDetailCocktail(drinkId)
                    } else {
                        null
                    }

                    call?.enqueue(object : Callback<CocktailResponse> {
                        override fun onResponse(call: Call<CocktailResponse>, response: Response<CocktailResponse>) {
                            isLoading = false
                            if (response.isSuccessful) {
                                val fetchedDrink = response.body()?.drinks?.firstOrNull()
                                drink = fetchedDrink
                                isFavorite = fetchedDrink?.let { favoritesManager.isFavorite(it, context) } ?: false
                            }
                        }

                        override fun onFailure(call: Call<CocktailResponse>, t: Throwable) {
                            isLoading = false
                            Log.e("DetailActivity", "Error fetching cocktail details", t)
                        }
                    })
                }

                LaunchedEffect(Unit) {
                    loadCocktail()
                }

                if (isLoading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                } else {
                    drink?.let { currentDrink ->
                        DetailCocktailScreen(
                            drink = currentDrink,
                            isFavorite = isFavorite,
                            onBackClick = { finish() },
                            onFavoriteClick = {
                                favoritesManager.toggleFavorite(currentDrink, context)
                                isFavorite = favoritesManager.isFavorite(currentDrink, context)
                            },
                            onRefreshClick = if (isRandom) { { loadCocktail() } } else null
                        )
                    }
                }
            }
        }
    }
}
