package com.starsep.sokoban.release.controls

interface ControlListener {
    fun onMoveLeft()
    fun onMoveRight()
    fun onMoveUp()
    fun onMoveDown()
}