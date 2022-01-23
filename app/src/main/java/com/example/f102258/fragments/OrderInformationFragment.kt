package com.example.f102258.fragments

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.f102258.R
import java.util.*

class OrderInformationFragment : Fragment() {
var selectDateTV: TextView? = null
    var submitOrderButton: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_order_information, container, false)
        val cities = resources.getStringArray(R.array.cities_array)
        val spinner = rootView.findViewById<Spinner>(R.id.cities_spinner)
        val adapter = activity?.let {
            ArrayAdapter(
                it,
                R.layout.support_simple_spinner_dropdown_item,
                cities
            )
        }
        spinner.adapter = adapter
        selectDateTV = rootView.findViewById(R.id.select_date_tv)
        submitOrderButton = rootView.findViewById(R.id.submit_button)
        selectDateTV?.setOnClickListener { clickDatePicker() }
        return rootView
    }

    private fun clickDatePicker() {
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(
            requireActivity(),
            { _, selYear, selMonth, dayOfMonth ->
                val selectedDate = "$dayOfMonth/${selMonth + 1}/$selYear"
                selectDateTV?.text = selectedDate
                submitOrderButton?.isEnabled = true
            },
            year,
            month,
            day
        )
        dpd.datePicker.minDate = System.currentTimeMillis()
        dpd.show()
    }
}