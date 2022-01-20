package com.example.f102258.pizzasList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.f102258.R
import com.example.f102258.data.Pizza

class PizzasListActivity : AppCompatActivity() {
    private val viewModel by viewModels<PizzasListViewModel> {
        PizzasListViewModelFactory(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pizzas_list)
        val pizzasAdapter = PizzasAdapter{_ -> {}}
        val recycleView = findViewById<RecyclerView>(R.id.recycler_view)
        recycleView.adapter = pizzasAdapter

        viewModel.pizzas.observe(this, {
            it?.let {
                it.forEach { p -> Log.i("pizza]", p.description)}
                pizzasAdapter.submitList(it as MutableList<Pizza>)
            }
        })
    }
}