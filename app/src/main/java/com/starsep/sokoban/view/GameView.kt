package com.starsep.sokoban.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View

import com.starsep.sokoban.R
import com.starsep.sokoban.gamelogic.HighScore
import com.starsep.sokoban.gamelogic.Position
import com.starsep.sokoban.gamelogic.Tile
import com.starsep.sokoban.mvc.GameController
import com.starsep.sokoban.mvc.GameModel
import com.starsep.sokoban.mvc.ViewEventsListener
import com.starsep.sokoban.res.Textures

class GameView(override val ctx: Context, attributeSet: AttributeSet) : View(ctx, attributeSet), ViewEventsListener {
    private val dimension: Rect
    private val textPaint: Paint
    private val screenDelta: Position = Position(0, 0)
    private var size: Int = 0 // size of tile
    private var gameController: GameController? = null
    internal lateinit var gameModel: GameModel
    private var winDialog: Dialog? = null

    init {
        Textures.init(context)

        val size = Math.min(width, height) / 10
        dimension = Rect(0, 0, size, size)
        textPaint = Paint()
        textPaint.color = Color.BLACK
    }

    private fun setDrawingDimension(x: Int, y: Int) {
        dimension.set(screenDelta.x + x * size, screenDelta.y + y * size,
                screenDelta.x + (x + 1) * size, screenDelta.y + (y + 1) * size)
    }

    private fun drawHero(canvas: Canvas) {
        setDrawingDimension(gameModel.player().x, gameModel.player().y)
        canvas.drawBitmap(gameModel.lastMove().heroTexture(), null, dimension, null)
    }

    private fun drawTiles(canvas: Canvas) {
        val level = gameModel.level()
        for (y in 0 until level.height()) {
            for (x in 0 until level.width()) {
                setDrawingDimension(x, y)
                if (!Tile.isGrass(level.tile(y, x))) {
                    canvas.drawBitmap(level.texture(y, x), null, dimension, null)
                }
            }
        }
    }

    private fun updateSize() {
        val level = gameModel.level()
        val newSize = Math.min(width / level.width(), height / level.height())
        if (newSize != size) {
            size = newSize
            textPaint.textSize = size.toFloat()
            screenDelta.x = (width - level.width() * size) / 2
            screenDelta.y = (height - level.height() * size) / 2
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        updateSize()
        drawTiles(canvas)
        drawHero(canvas)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        winDialog?.dismiss()
    }

    override fun onUpdate() {
        invalidate()
        gameController?.onStatsChanged()
    }

    override fun showWinDialog(levelNumber: Int, stats: HighScore, highScore: HighScore) {
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
                .setPositiveButton(resources.getString(R.string.win_positive)) { _, _ -> gameModel.nextLevel() }
                .setNegativeButton(resources.getString(R.string.win_negative)) { _, _ -> gameModel.repeatLevel() }
                .setOnCancelListener { gameModel.nextLevel() }
                .setIcon(android.R.drawable.ic_dialog_info)
                .create()
        winDialog?.show()
    }

    fun setGameController(controller: GameController) {
        gameController = controller
        onUpdate()
    }
}
