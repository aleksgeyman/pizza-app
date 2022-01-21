package com.example.f102258.pizzasList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.f102258.R
import com.example.f102258.data.Pizza
import org.w3c.dom.Text

class PizzasAdapter(private val onTap: (Pizza) -> Unit) :
    androidx.recyclerview.widget.ListAdapter<Pizza, PizzasAdapter.PizzaViewHolder>(PizzaDiffCallback) {

    class PizzaViewHolder(itemView: View, val onTap: (Pizza) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val pizzaNameTextView: TextView = itemView.findViewById(R.id.pizza_name)
        private val pizzaDescriptionTextView: TextView = itemView.findViewById(R.id.pizza_description)
        private val pizzapriceTextView: TextView = itemView.findViewById(R.id.pizza_price)
        private var currentPizza: Pizza? = null

        init {
            itemView.setOnClickListener {
                currentPizza?.let {
                    onTap(it)
                }
            }
        }

        fun bind(pizza: Pizza) {
            currentPizza = pizza

            pizzaNameTextView.text = pizza.name
            pizzaDescriptionTextView.text = pizza.description
            pizzapriceTextView.text = pizza.price.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PizzaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pizza_item, parent, false)
        return PizzaViewHolder(view, onTap)
    }

    override fun onBindViewHolder(holder: PizzaViewHolder, position: Int) {
        val flower = getItem(position)
        holder.bind(flower)

    }
}

object PizzaDiffCallback : DiffUtil.ItemCallback<Pizza>() {
    override fun areItemsTheSame(oldItem: Pizza, newItem: Pizza): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Pizza, newItem: Pizza): Boolean {
        return oldItem.id == newItem.id
    }
}