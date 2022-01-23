package com.example.f102258.pizzaOrder

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.f102258.R
import com.example.f102258.data.DatabaseHandler
import com.example.f102258.helpers.PizzaOrderHelper
import com.example.f102258.services.PlaySoundService

class PizzaOrderActivity : AppCompatActivity() {
    private val viewModel by viewModels<PizzaOrderViewModel> {
        PizzaOrderViewModelFactory(this)
    }

    private lateinit var pizzaTotalPriceText: TextView
    private lateinit var pizzaCountText: TextView
    private lateinit var addPizzaButton: Button
    private lateinit var removePizzaButton: Button
    private lateinit var submitOrderButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pizza_order)
        configurePizzaView()
        configureButtons()
        configureTextViews()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_add_to_fav -> {
                viewModel.addPizzaToFavorite(this)
            }
            R.id.menu_show_fav -> {
                val pizzas = DatabaseHandler(this).getPizzas()
                pizzas.forEach { it -> Log.i("Pizza", it.name) }
            }
            R.id.menu_send_feedback -> {
                PizzaOrderHelper.sendEmail(this, "", "", "")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun configurePizzaView() {
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
    }

    private fun configureTextViews() {
        pizzaCountText = findViewById(R.id.pizzas_count_tv)
        viewModel.orderCount.observe(this, {
            it?.let {
                pizzaCountText.text = it.toString()
            }
        })

        pizzaTotalPriceText = findViewById(R.id.pizza_price)
        viewModel.totalPrice.observe(this, {
            it?.let {
                pizzaTotalPriceText.text = it.toString()
            }
        })
    }

    private fun configureButtons() {
        addPizzaButton = findViewById(R.id.increment_button)
        addPizzaButton.setOnClickListener { viewModel.addPizza() }
        removePizzaButton = findViewById(R.id.decrement_button)
        removePizzaButton.setOnClickListener { viewModel.removePizza() }
        submitOrderButton = findViewById(R.id.submit_button)
        submitOrderButton.setOnClickListener { showAlert() }
    }

    private fun showAlert() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle(getString(R.string.confirm_dialog_title))
        alertDialog.setMessage(getString(R.string.confirm_dialog_message))
        alertDialog.setPositiveButton(getString(R.string.confirm_dialog_yes)) { _, _ ->
            viewModel.addPizza()
            startService()
        }
        alertDialog.setNegativeButton(getString(R.string.confirm_dialog_no)) { _, _ -> }
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