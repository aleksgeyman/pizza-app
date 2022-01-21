package com.example.f102258.pizzasList

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.f102258.R
import com.example.f102258.data.Pizza
import com.example.f102258.pizzaOrder.PizzaOrderActivity

class PizzasListActivity : AppCompatActivity() {
    private val viewModel by viewModels<PizzasListViewModel> {
        PizzasListViewModelFactory(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pizzas_list)
        val pizzasAdapter = PizzasAdapter{pizza -> pizzaOnTap(pizza)}
        val recycleView = findViewById<RecyclerView>(R.id.recycler_view)
        recycleView.adapter = pizzasAdapter

        viewModel.pizzas.observe(this, {
            it?.let {
                it.forEach { p -> Log.i("pizza]", p.description)}
                pizzasAdapter.submitList(it as MutableList<Pizza>)
            }
        })
    }

    private fun pizzaOnTap(pizza: Pizza) {
        val intent = Intent(this, PizzaOrderActivity::class.java)
        intent.putExtra(R.string.pizza_id.toString(), pizza.id)
        startActivity(intent)
    }
}