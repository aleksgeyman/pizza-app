package com.example.f102258.pizzaOrder

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.f102258.data.Pizza
import com.example.f102258.data.PizzasRepository

class PizzaOrderViewModel(private val pizzasRepository: PizzasRepository) : ViewModel() {
    var pizza: Pizza? = null
    var orderCount = MutableLiveData<Int>(1)
    var totalPrice = MutableLiveData<Double>(0.0)

    fun addPizza() {
        if (orderCount.value!! < 10) {
            orderCount.value = orderCount!!.value!!.plus(1)
            updateTotalPrice()
        }
    }

    fun removePizza() {
        if (orderCount.value!! > 1) {
            orderCount.value = orderCount!!.value!!.minus(1)
            updateTotalPrice()
        }
    }

    fun getPizza(id: Int): Pizza? {
        pizza?.let { it ->
            return it
        }
        val fetchedPizza = pizzasRepository.getPizza(id)
        pizza = fetchedPizza
        return fetchedPizza
    }

      fun updateTotalPrice() {
         totalPrice.value = (pizza!!.price * orderCount.value!!)
    }
}

class PizzaOrderViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PizzaOrderViewModel(PizzasRepository((context))) as T
    }
}