package com.starsep.sokoban.release.gamelogic.level

import android.content.Context
import com.starsep.sokoban.release.gamelogic.Position
import java.io.IOException
import java.io.InputStream
import java.util.Scanner
import timber.log.Timber

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
                Timber.e("ImmutableLevel line: $line has bad length. Should have $width")
            }
            System.arraycopy(line.toCharArray(), 0, data, i * width, line.length)
        }
        scanner.close()
        val result = ImmutableLevel(data, width, player)
        if (!result.valid()) {
            Timber.e("ImmutableLevel.load: Loaded level is invalid")
        }
        return result
    }
}
