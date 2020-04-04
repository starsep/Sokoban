package com.starsep.sokoban.release.gamelogic.level

import android.content.Context
import android.util.Log

import com.starsep.sokoban.release.Sokoban
import com.starsep.sokoban.release.gamelogic.Position

import java.io.IOException
import java.io.InputStream
import java.util.Scanner

internal object LevelLoader {
    @Throws(IOException::class)
    fun load(context: Context, levelNumber: Int): ImmutableLevel {
        val filename = "levels/$levelNumber.level"
        val inputStream: InputStream = context.assets.open(filename)
        val scanner = Scanner(inputStream)
        val height = scanner.nextInt()
        val width = scanner.nextInt()
        val player = Position(scanner.nextInt(), scanner.nextInt())
        val data = CharArray(width * height)
        for (i in 0 until height) {
            val line = scanner.next()
            if (line.length != width) {
                Log.e(Sokoban.TAG, "ImmutableLevel line: $line has bad length. Should have $width")
            }
            System.arraycopy(line.toCharArray(), 0, data, i * width, line.length)
        }
        scanner.close()
        val result = ImmutableLevel(data, width, player)
        if (!result.valid()) {
            Log.e(Sokoban.TAG, "ImmutableLevel.load: " + "Loaded level is invalid")
        }
        return result
    }
}
