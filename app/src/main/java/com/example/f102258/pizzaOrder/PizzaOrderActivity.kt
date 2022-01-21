package com.example.f102258.pizzaOrder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import com.example.f102258.R
import com.example.f102258.data.Pizza
import com.example.f102258.pizzasList.PizzasListViewModel
import com.example.f102258.pizzasList.PizzasListViewModelFactory

class PizzaOrderActivity : AppCompatActivity() {
    private val viewModel by viewModels<PizzaOrderViewModel> {
        PizzaOrderViewModelFactory(this)
    }

    private var pizzaTotalPriceText: TextView? = null
    private var pizzaCountText: TextView? = null
    private var addPizzaButton: Button? = null
    private var removePizzaButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pizza_order)
         val pizzaNameTextView: TextView = findViewById(R.id.pizza_name)
         val pizzaDescriptionTextView: TextView = findViewById(R.id.pizza_description)
         val pizzaPriceTextView: TextView = findViewById(R.id.pizza_price)
        val currentPizzaId = intent.extras?.getInt(R.string.pizza_id.toString())
        currentPizzaId?.let {
           val currentPizza = viewModel.getPizza(it)
            viewModel.updateTotalPrice()
            pizzaNameTextView.text = currentPizza?.name
            pizzaDescriptionTextView.text = currentPizza?.description
            pizzaPriceTextView.text = currentPizza?.price.toString()
        }
        configureButtons()
        configureTextViews()
    }

    private fun configureTextViews() {
        pizzaCountText = findViewById(R.id.pizzas_count_tv)
        viewModel.orderCount.observe(this, {
            it?.let {
                pizzaCountText?.text = it.toString()
            }
        })

        pizzaTotalPriceText = findViewById(R.id.pizza_price)
        viewModel.totalPrice.observe(this, {
            it?.let {
                pizzaTotalPriceText?.text = it.toString()
            }
        })
    }

    private fun configureButtons() {
        addPizzaButton = findViewById(R.id.increment_button)
        addPizzaButton?.setOnClickListener { viewModel.addPizza() }
        removePizzaButton = findViewById(R.id.decrement_button)
        removePizzaButton?.setOnClickListener { viewModel.removePizza() }
    }
}