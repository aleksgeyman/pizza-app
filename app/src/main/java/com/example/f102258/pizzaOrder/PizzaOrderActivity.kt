package com.example.f102258.pizzaOrder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import com.example.f102258.R
import com.example.f102258.pizzasList.PizzasListViewModel
import com.example.f102258.pizzasList.PizzasListViewModelFactory

class PizzaOrderActivity : AppCompatActivity() {

    private val viewModel by viewModels<PizzaOrderViewModel> {
        PizzaOrderViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pizza_order)
         val pizzaNameTextView: TextView = findViewById(R.id.pizza_name)
         val pizzaDescriptionTextView: TextView = findViewById(R.id.pizza_description)
         val pizzaPriceTextView: TextView = findViewById(R.id.pizza_price)
        val currentPizzaId = intent.extras?.getInt(R.string.pizza_id.toString())
        currentPizzaId?.let {
           val currentPizza = viewModel.getPizza(it)
            pizzaNameTextView.text = currentPizza?.name
            pizzaDescriptionTextView.text = currentPizza?.description
            pizzaPriceTextView.text = currentPizza?.price.toString()
        }
    }
}