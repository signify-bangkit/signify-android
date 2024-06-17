package com.signify.app.utils

import android.content.Context
import android.graphics.Rect
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val timeStamp: String =
    SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(
        Date()
    )

fun Long.toFormattedDate(): String {
    val date = Date(this * 1000L) // Convert seconds to milliseconds
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return formatter.format(date)
}

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

class BottomMarginDecoration(private val bottomMargin: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val itemPosition = parent.getChildAdapterPosition(view)
        val itemCount = state.itemCount
        if (itemPosition == itemCount - 1) {
            outRect.bottom = bottomMargin
        }
    }
}
