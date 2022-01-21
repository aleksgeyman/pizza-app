package com.example.f102258.pizzaOrder

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.f102258.data.Pizza
import com.example.f102258.data.PizzasRepository

class PizzaOrderViewModel(private val pizzasRepository: PizzasRepository) : ViewModel() {

    fun getPizza(id: Int): Pizza? {
        return pizzasRepository.getPizza(id)
    }
}

class PizzaOrderViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PizzaOrderViewModel(PizzasRepository((context))) as T
    }
}