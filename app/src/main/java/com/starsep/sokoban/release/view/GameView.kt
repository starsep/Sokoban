package com.starsep.sokoban.release.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.starsep.sokoban.release.R
import com.starsep.sokoban.release.gamelogic.HighScore
import com.starsep.sokoban.release.gamelogic.Position
import com.starsep.sokoban.release.gamelogic.Tile
import com.starsep.sokoban.release.gamelogic.level.Level
import com.starsep.sokoban.release.model.GameModel
import com.starsep.sokoban.release.res.Textures

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

        gameModel.levelLive.observe(activity, Observer<Level> { level -> updateLevel(level) })
        updateLevel(gameModel.level())
        gameModel.wonLive.observe(activity, Observer<Boolean> {
            if (it == true) {
                showWinDialog(gameModel.highScore(context))
                gameModel.sendHighScore(context)
            }
        })
    }

    private fun updateLevel(level: Level?) {
        level?.let {
            size = Math.min(width / level.width, height / level.height())
            textPaint.textSize = size.toFloat()
            screenDelta = Position(
                    (height - level.height() * size) / 2,
                    (width - level.width * size) / 2
            )
            invalidate()
        }
    }

    private fun setDrawingDimension(pos: Position) {
        val (y, x) = pos
        dimension.set(screenDelta.x + x * size, screenDelta.y + y * size,
                screenDelta.x + (x + 1) * size, screenDelta.y + (y + 1) * size)
    }

    private fun drawHero(canvas: Canvas) {
        setDrawingDimension(gameModel.player())
        val bitmap = gameModel.lastMove()?.heroTexture() ?: Textures.heroDown()
        canvas.drawBitmap(bitmap, null, dimension, null)
    }

    private fun drawTiles(canvas: Canvas) {
        val level = gameModel.level()
        for (pos in level.positions()) {
            setDrawingDimension(pos)
            if (!Tile.isGrass(level.tile(pos))) {
                canvas.drawBitmap(level.texture(pos), null, dimension, null)
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawTiles(canvas)
        drawHero(canvas)
        // TODO: remove that
        updateLevel(gameModel.level())
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        winDialog?.dismiss()
    }

    private fun showWinDialog(_highScore: HighScore?) {
        val stats = gameModel.stats()
        val highScore: HighScore = _highScore ?: stats
        val minutes = stats.time / 60
        val seconds = stats.time % 60
        val minutesBest = highScore.time / 60
        val secondsBest = highScore.time % 60
        val msg = String.format(resources.getString(R.string.win_msg),
                stats.moves, highScore.moves, stats.pushes, highScore.pushes,
                minutes, seconds, minutesBest, secondsBest)
        winDialog = AlertDialog.Builder(context)
                .setTitle(String.format(resources.getString(R.string.win_title), gameModel.levelNumber()))
                .setMessage(msg)
                .setPositiveButton(resources.getString(R.string.win_positive)) { _, _ -> gameModel.nextLevel(context) }
                .setNegativeButton(resources.getString(R.string.win_negative)) { _, _ -> gameModel.resetLevel(context) }
                .setOnCancelListener { gameModel.nextLevel(context) }
                .setIcon(android.R.drawable.ic_dialog_info)
                .create()
        winDialog?.show()
    }
}
