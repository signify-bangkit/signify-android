package com.signify.app.presentation.component

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.google.android.material.textfield.TextInputLayout
import com.signify.app.R

class PasswordTextField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) :
    AppCompatEditText(context, attrs) {

    init {
        inputType =
            InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                val parent = parent.parent
                if (parent is TextInputLayout) {
                    parent.errorIconDrawable = null
                    if (!s.isNullOrEmpty() && s.length < 8) {
                        parent.error =
                            context.getString(R.string.error_password)
                        parent.boxStrokeColor =
                            context.getColor(R.color.redOne)
                    } else {
                        parent.error = null
                        parent.boxStrokeColor =
                            context.getColor(R.color.transparent)
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
}
