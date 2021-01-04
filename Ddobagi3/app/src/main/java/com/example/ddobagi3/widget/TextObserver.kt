package com.example.ddobagi3.widget

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.example.ddobagi3.view.JusoActivity
import kotlinx.android.synthetic.main.activity_juso.*

class TextObserver(val jusoActivity: JusoActivity, val editText: EditText) : TextWatcher {
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        jusoActivity.getJusoByText(editText.text.toString())
    }
    override fun afterTextChanged(s: Editable?) {}
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
}