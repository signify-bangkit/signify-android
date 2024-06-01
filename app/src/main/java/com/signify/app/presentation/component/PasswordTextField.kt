package com.signify.app.presentation.component

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.signify.app.R

class PasswordTextField @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs) {

    init {
        inputType =
            InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                p0: CharSequence?, p1: Int, p2: Int, p3: Int
            ) {
            }

            override fun afterTextChanged(p0: Editable?) {}

            override fun onTextChanged(
                s: CharSequence?, start: Int, before: Int, count: Int
            ) {
                if (!s.isNullOrEmpty() && s.length < 8) error =
                    context.getString(
                        R.string.error_password
                    )
            }
        })
    }
}
