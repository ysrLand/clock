package com.ysr.learn.clock

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.GridLayout
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.layout_key.view.*
import kotlinx.android.synthetic.main.layout_keypad.view.*

class KeypadInputView : ConstraintLayout {
    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        LayoutInflater.from(context).inflate(R.layout.layout_keypad, this, true)
    }

    val inputText: String
        get() {
            return inputTextView.text.toString()
        }

    fun setContent(contents: List<String>, listener: OnClickListener, columnCount: Int) {
        for (index in contents.indices) {
            keysView.addView(
                View.inflate(context, R.layout.layout_key, null).apply {
                    keyRootLayout.let {
                        keyTextView.text = contents[index]
                        keyTextView.setOnClickListener(listener)
                        val params = GridLayout.LayoutParams(
                            GridLayout.spec(index / columnCount, 1, 1f),
                            GridLayout.spec(index % columnCount, 1, 1f)
                        )
                        layoutParams = params
                    }
                }
            )
        }
    }

    fun clearInput() {
        inputTextView.text = ""
    }
}