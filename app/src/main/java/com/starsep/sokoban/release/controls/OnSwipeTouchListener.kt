package com.starsep.sokoban.release.controls

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.annotation.VisibleForTesting
import kotlin.math.abs

class OnSwipeTouchListener(ctx: Context, private val listener: ControlListener) : View.OnTouchListener {
    private val gestureDetector = GestureDetector(ctx, GestureListener())
    override fun onTouch(v: View, event: MotionEvent) = gestureDetector.onTouchEvent(event)

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            val diffY = e2.y - e1.y
            val diffX = e2.x - e1.x
            if (abs(diffX) > abs(diffY) &&
                abs(diffX) > SWIPE_THRESHOLD && abs(velocityX) > SWIPE_VELOCITY_THRESHOLD
            ) {
                when {
                    diffX > 0 -> listener.onMoveRight()
                    else -> listener.onMoveLeft()
                }
            } else if (abs(diffY) > SWIPE_THRESHOLD && abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                when {
                    diffY > 0 -> listener.onMoveDown()
                    else -> listener.onMoveUp()
                }
            }
            return true
        }
        override fun onDown(e: MotionEvent) = true
    }
    companion object {
        private const val SWIPE_THRESHOLD = 100f
        private const val SWIPE_VELOCITY_THRESHOLD = 100f
    }
}
