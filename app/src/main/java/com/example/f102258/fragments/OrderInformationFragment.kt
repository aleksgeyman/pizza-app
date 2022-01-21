package com.example.f102258.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.f102258.R

class OrderInformationFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_order_information, container, false)

        val cities = resources.getStringArray(R.array.cities_array)
        val spinner = rootView.findViewById<Spinner>(R.id.cities_spinner)

          val adapter =
              activity?.let { ArrayAdapter(it, R.layout.support_simple_spinner_dropdown_item, cities) }
            spinner.adapter = adapter
            return rootView
        }
    }