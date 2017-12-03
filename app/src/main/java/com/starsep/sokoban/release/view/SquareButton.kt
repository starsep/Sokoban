package com.starsep.sokoban.release.view

import android.content.Context
import android.view.View
import android.widget.Button

class SquareButton(context: Context) : Button(context) {
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val size = Math.max(widthMeasureSpec, heightMeasureSpec)
        super.onMeasure(
                View.MeasureSpec.makeMeasureSpec(size, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(size, View.MeasureSpec.EXACTLY)
        )
    }
}