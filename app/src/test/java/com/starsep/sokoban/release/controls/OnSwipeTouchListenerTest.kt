package com.starsep.sokoban.release.controls

import android.view.MotionEvent
import com.nhaarman.mockitokotlin2.*
import org.junit.Test

class OnSwipeTouchListenerTest {
    private fun eventMock(X: Float, Y: Float) = mock<MotionEvent> {
        on { x } doReturn X
        on { y } doReturn Y
    }
    private val emptyEventMock = eventMock(X = 0f, Y = 0f)

    private val controlListener = mock<ControlListener>()
    private val onSwipeTouchListener = OnSwipeTouchListener(mock(), controlListener)
    private val gestureListener = onSwipeTouchListener.GestureListener()

    private fun invokeGesture(x: Float, y: Float, velocityX: Float, velocityY: Float) {
        gestureListener.onFling(emptyEventMock, eventMock(x, y), velocityX, velocityY)
    }

    @Test
    fun `no swipe`() {
        invokeGesture(x = NONE, y = NONE, velocityX = NONE, velocityY = NONE)

        verifyZeroInteractions(controlListener)
    }

    @Test
    fun `fast swipe right`() {
        invokeGesture(x = LONG, y = NONE, velocityX = FAST, velocityY = NONE)

        verify(controlListener).onMoveRight()
        verifyNoMoreInteractions(controlListener)
    }

    @Test
    fun `slow, long swipe down`() {
        invokeGesture(x = NONE, y = LONG, velocityX = NONE, velocityY = SLOW)

        verifyNoMoreInteractions(controlListener)
    }

    @Test
    fun `fast, short swipe left`() {
        invokeGesture(x = -SHORT, y = NONE, velocityX = FAST, velocityY = NONE)

        verifyNoMoreInteractions(controlListener)
    }

    @Test
    fun `swipe up-right`() {
        invokeGesture(x = LONG, y = -VERY_LONG, velocityX = FAST, velocityY = FAST)

        verify(controlListener).onMoveUp()
        verifyNoMoreInteractions(controlListener)
    }

    @Test
    fun `swipe left-down`() {
        invokeGesture(x = -VERY_LONG, y = LONG, velocityX = FAST, velocityY = FAST)

        verify(controlListener).onMoveLeft()
        verifyNoMoreInteractions(controlListener)
    }

    companion object {
        const val NONE = 0f
        const val SLOW = 80f
        const val FAST = 200f
        const val SHORT = SLOW
        const val LONG = FAST
        const val VERY_LONG = LONG * 2
    }
}
