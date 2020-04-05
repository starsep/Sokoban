package com.starsep.sokoban.release.view

import android.content.Context
import android.view.View
import com.google.android.material.button.MaterialButton
import kotlin.math.max

class SquareButton(context: Context) : MaterialButton(context) {
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val size = max(widthMeasureSpec, heightMeasureSpec)
        super.onMeasure(
                View.MeasureSpec.makeMeasureSpec(size, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(size, View.MeasureSpec.EXACTLY)
        )
    }
}
