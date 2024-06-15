package com.signify.app.utils

import android.content.Context
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val timeStamp: String =
    SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(
        Date()
    )

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}
