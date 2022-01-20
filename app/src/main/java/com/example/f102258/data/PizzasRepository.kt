package com.example.f102258.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class PizzasRepository(private val context: Context) {

    // Returns List of Pizza objects.
    fun getPizzas(): LiveData<List<Pizza>> {
        val jsonFileString = getJsonDataFromAsset(context, "pizzas.json")
        val gson = Gson()
        val listPizzaType = object : TypeToken<List<Pizza>>() {}.type
       return MutableLiveData(gson.fromJson(jsonFileString, listPizzaType))
    }

    // Returns optional string from a json file in app assets if the file exists.
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