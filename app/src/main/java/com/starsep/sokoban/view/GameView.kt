package com.starsep.sokoban.view

import android.app.AlertDialog
import android.app.Dialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.support.v4.app.FragmentActivity
import android.util.AttributeSet
import android.util.Log
import android.view.View

import com.starsep.sokoban.R
import com.starsep.sokoban.Sokoban
import com.starsep.sokoban.gamelogic.HighScore
import com.starsep.sokoban.gamelogic.level.ImmutableLevel
import com.starsep.sokoban.gamelogic.Position
import com.starsep.sokoban.gamelogic.Tile
import com.starsep.sokoban.gamelogic.level.Level
import com.starsep.sokoban.model.GameModel
import com.starsep.sokoban.res.Textures

class GameView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    private val dimension: Rect
    private val textPaint: Paint = Paint().apply { color = Color.BLACK }
    private var screenDelta: Position = Position(0, 0)
    private var size: Int = 0 // size of tile
    private val activity: FragmentActivity = context as FragmentActivity
    private val gameModel: GameModel by lazy {
        ViewModelProviders.of(activity).get(GameModel::class.java)
    }
    private var winDialog: Dialog? = null

    init {
        Textures.init(context)

        size = Math.min(width, height) / 10
        dimension = Rect(0, 0, size, size)

        gameModel.levelLive.observe(activity, Observer<Level> { level ->
            level?.let {
                val newSize = Math.min(width / level.width, height / level.height())
                if (newSize != size) {
                    size = newSize
                    textPaint.textSize = size.toFloat()
                    screenDelta = Position(
                            (height - level.height() * size) / 2,
                            (width - level.width * size) / 2
                    )
                }
            }
        })
    }

    private fun setDrawingDimension(x: Int, y: Int) {
        dimension.set(screenDelta.x + x * size, screenDelta.y + y * size,
                screenDelta.x + (x + 1) * size, screenDelta.y + (y + 1) * size)
    }

    private fun drawHero(canvas: Canvas) {
        setDrawingDimension(gameModel.player().x, gameModel.player().y)
        val bitmap = gameModel.lastMove()?.heroTexture() ?: Textures.heroDown()
        canvas.drawBitmap(bitmap, null, dimension, null)
    }

    private fun drawTiles(canvas: Canvas) {
        val level = gameModel.level()
        Log.d(Sokoban.TAG, level.toString())
        for (y in 0 until level.height()) {
            for (x in 0 until level.width) {
                setDrawingDimension(x, y)
                Log.d(Sokoban.TAG, x.toString() + " asd " + y.toString())
                if (!Tile.isGrass(level.tile(Position(y, x)))) {
                    canvas.drawBitmap(level.texture(Position(y, x)), null, dimension, null)
                }
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawTiles(canvas)
        drawHero(canvas)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        winDialog?.dismiss()
    }

    fun showWinDialog(levelNumber: Int, stats: HighScore, highScore: HighScore) {
        val minutes = stats.time / 60
        val seconds = stats.time % 60
        val minutesBest = highScore.time / 60
        val secondsBest = highScore.time % 60
        val msg = String.format(resources.getString(R.string.win_msg),
                stats.moves, highScore.moves, stats.pushes, highScore.pushes,
                minutes, seconds, minutesBest, secondsBest)
        winDialog = AlertDialog.Builder(context)
                .setTitle(String.format(resources.getString(R.string.win_title), levelNumber))
                .setMessage(msg)
                .setPositiveButton(resources.getString(R.string.win_positive)) { _, _ -> gameModel.nextLevel(context) }
                .setNegativeButton(resources.getString(R.string.win_negative)) { _, _ -> gameModel.repeatLevel(context) }
                .setOnCancelListener { gameModel.nextLevel(context) }
                .setIcon(android.R.drawable.ic_dialog_info)
                .create()
        winDialog?.show()
    }
}
