package com.example.f102258.pizzaOrder

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.f102258.R
import com.example.f102258.data.Pizza
import com.example.f102258.pizzasList.PizzasListViewModel
import com.example.f102258.pizzasList.PizzasListViewModelFactory
import com.example.f102258.services.PlaySoundService

class PizzaOrderActivity : AppCompatActivity() {
    private val viewModel by viewModels<PizzaOrderViewModel> {
        PizzaOrderViewModelFactory(this)
    }

    private var pizzaTotalPriceText: TextView? = null
    private var pizzaCountText: TextView? = null
    private var addPizzaButton: Button? = null
    private var removePizzaButton: Button? = null
    private var submitOrderButton: Button? = null

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

    override fun onDestroy() {
        super.onDestroy()
        stopService()
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
        submitOrderButton = findViewById(R.id.submit_button)
        submitOrderButton?.setOnClickListener { showAlert() }
    }

    private fun showAlert() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Confirm Order")
        alertDialog.setMessage("Is your order ready?")
        alertDialog.setPositiveButton("Yes") { _, _ ->
            viewModel.addPizza()
            startService()
        }
        alertDialog.setNegativeButton("No") { _, _ -> }
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun isServiceRunning(serviceClass: Class<*>): Boolean {
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in activityManager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }

    private fun startService() {
        if (!isServiceRunning(PlaySoundService::class.java)) {
            startService(Intent(applicationContext, PlaySoundService::class.java))
        }
    }

    private fun stopService() {
        if (isServiceRunning(PlaySoundService::class.java)) {
            stopService(Intent(applicationContext, PlaySoundService::class.java))
        }
    }
}