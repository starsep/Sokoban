package com.starsep.sokoban.gamelogic

import android.content.Context
import android.util.Log

import com.starsep.sokoban.Sokoban
import com.starsep.sokoban.mvc.GameModel

import java.io.IOException
import java.io.InputStream
import java.util.Scanner

internal object LevelLoader {
    @Throws(IOException::class)
    fun load(context: Context, filename: String,
             model: GameModel, levelNumber: Int): Level {
        val inputStream: InputStream = context.assets.open(filename)
        val scanner = Scanner(inputStream)
        val height = scanner.nextInt()
        val width = scanner.nextInt()
        val player = Position(scanner.nextInt(), scanner.nextInt())
        val data = CharArray(width * height)
        for (i in 0 until height) {
            val line = scanner.next()
            if (line.length != width) {
                Log.e(Sokoban.TAG, "Level line: $line has bad length. Should have $width")
            }
            System.arraycopy(line.toCharArray(), 0, data, i * width, line.length)
        }
        scanner.close()
        val result = Level(data, width, player, levelNumber)
        result.gameModel = model
        if (!result.valid()) {
            Log.e(Sokoban.TAG, "Level.load: " + "Loaded level is invalid")
        }
        return result
    }
}
