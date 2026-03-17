package fr.isen.moratille.thegreatestcocktailapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import fr.isen.moratille.thegreatestcocktailapp.dataClasses.DrinkFilterResponse
import fr.isen.moratille.thegreatestcocktailapp.dataClasses.DrinkPreview
import fr.isen.moratille.thegreatestcocktailapp.network.ApiClient
import fr.isen.moratille.thegreatestcocktailapp.screens.DrinksScreen
import fr.isen.moratille.thegreatestcocktailapp.ui.theme.TheGreatestCocktailAppTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DrinksActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val categoryName = intent.getStringExtra("CATEGORY_NAME") ?: ""

        setContent {
            TheGreatestCocktailAppTheme {
                val context = LocalContext.current
                var drinks by remember { mutableStateOf<List<DrinkPreview>>(emptyList()) }
                var isLoading by remember { mutableStateOf(true) }

                LaunchedEffect(categoryName) {
                    ApiClient.service.getDrinksByCategory(categoryName).enqueue(object : Callback<DrinkFilterResponse> {
                        override fun onResponse(call: Call<DrinkFilterResponse>, response: Response<DrinkFilterResponse>) {
                            isLoading = false
                            if (response.isSuccessful) {
                                drinks = response.body()?.drinks ?: emptyList()
                            }
                        }

                        override fun onFailure(call: Call<DrinkFilterResponse>, t: Throwable) {
                            isLoading = false
                            Log.e("DrinksActivity", "Error fetching drinks", t)
                        }
                    })
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
                                    titleContentColor = MaterialTheme.colorScheme.primary,
                                ),
                                title = { Text(categoryName) },
                                navigationIcon = {
                                    IconButton(onClick = { finish() }) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                            contentDescription = "Back"
                                        )
                                    }
                                }
                            )
                        }
                    ) { innerPadding ->
                        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
                            if (isLoading) {
                                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                            } else {
                                DrinksScreen(
                                    drinks = drinks,
                                    onDrinkClick = { drinkPreview ->
                                        val intent = Intent(context, DetailActivity::class.java)
                                        intent.putExtra("DRINK_ID", drinkPreview.idDrink)
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
