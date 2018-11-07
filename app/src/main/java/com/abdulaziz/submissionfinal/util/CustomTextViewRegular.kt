package com.abdulaziz.submissionfinal.util

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet


class CustomTextViewRegular : android.support.v7.widget.AppCompatTextView {

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context) : super(context) {
        init()
    }

    private fun init() {
        if (!isInEditMode) {
            val tf = Typeface.createFromAsset(context.assets, "fonts/FredokaOne-Regular.ttf")
            typeface = tf
        }
    }

}