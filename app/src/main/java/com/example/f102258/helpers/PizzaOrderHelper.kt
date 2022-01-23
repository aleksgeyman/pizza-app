package com.example.f102258.helpers

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.example.f102258.R

object PizzaOrderHelper {
     fun sendEmail(context: Context, to: String, subject: String, message: String) {
         val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
             "mailto", to, null))
         emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
         emailIntent.putExtra(Intent.EXTRA_TEXT, message)
         startActivity(context, Intent.createChooser(emailIntent, Intent.ACTION_SENDTO), null)
    }
}