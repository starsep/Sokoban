package com.starsep.sokoban.release.view

import android.content.Context
import com.google.android.material.button.MaterialButton
import kotlin.math.max

class SquareButton(context: Context) : MaterialButton(context) {
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val size = max(widthMeasureSpec, heightMeasureSpec)
        super.onMeasure(
                MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY)
        )
    }
}
