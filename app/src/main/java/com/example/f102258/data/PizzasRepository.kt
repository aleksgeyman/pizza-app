package com.example.f102258.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

// Repository associated with pizza objects.
class PizzasRepository(private val context: Context) {
    private val pizzas = fetchPizzas()

    // Returns the list of already fetched pizza objects.
    fun getPizzas(): LiveData<List<Pizza>> {
        return pizzas
    }

    // Returns a Pizza object by a given id.
    fun getPizza(id: Int): Pizza? {
        pizzas.value?.let { pizzas ->
            return pizzas.firstOrNull { it.id == id }
        }
        return null
    }

    // Converts read JSON to a usable LiveData.
    private fun fetchPizzas(): LiveData<List<Pizza>> {
        val jsonFileString = getJsonDataFromAsset(context, "pizzas.json")
        val gson = Gson()
        val listPizzaType = object : TypeToken<List<Pizza>>() {}.type
        return MutableLiveData(gson.fromJson(jsonFileString, listPizzaType))
    }

    // Reads JSON file from assets
    private fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }
}