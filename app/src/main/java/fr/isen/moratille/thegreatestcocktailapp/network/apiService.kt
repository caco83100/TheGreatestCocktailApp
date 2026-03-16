package fr.isen.moratille.thegreatestcocktailapp.network

import fr.isen.moratille.thegreatestcocktailapp.dataClasses.CategoryListResponse
import fr.isen.moratille.thegreatestcocktailapp.dataClasses.CocktailResponse
import fr.isen.moratille.thegreatestcocktailapp.dataClasses.DrinkFilterResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("random.php")
    fun getRandomCocktail(): Call<CocktailResponse>

    @GET("list.php?c=list")
    fun getCategories(): Call<CategoryListResponse>

    @GET("filter.php")
    fun getDrinksByCategory(@Query("c") categoryName: String): Call<DrinkFilterResponse>

    @GET("lookup.php")
    fun getDetailCocktail(@Query("i") drinkID: String): Call<CocktailResponse>
}
