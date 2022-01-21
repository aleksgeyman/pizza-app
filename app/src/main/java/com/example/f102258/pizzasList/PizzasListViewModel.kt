package com.example.f102258.pizzasList

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.f102258.data.PizzasRepository

class PizzasListViewModel(private val pizzasRepository: PizzasRepository): ViewModel() {
    var pizzas = pizzasRepository.getPizzas()
}

class PizzasListViewModelFactory(private val context: Context): ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return PizzasListViewModel(PizzasRepository((context))) as T
    }
}