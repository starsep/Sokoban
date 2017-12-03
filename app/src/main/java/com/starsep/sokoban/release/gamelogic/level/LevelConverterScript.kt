package com.starsep.sokoban.release.gamelogic.level

import java.util.ArrayList
import java.util.Scanner

object LevelConverterScript {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = Scanner(System.`in`)
        val data = ArrayList<String>()
        while (input.hasNextLine()) {
            val line = input.nextLine()
            if (line != null) {
                data.add(line)
            }
        }
        val height = data.size
        val width = data[0].length
        val level = CharArray(height * width)
        for (i in 0 until height) {
            for (j in 0 until width) {
                level[i * width + j] = data[i][j]
            }
        }
        try {
            val l = LevelConverter.convert(level, width)
            print(l)
        } catch (e: LevelConverter.LevelConverterException) {
            e.printStackTrace()
        }

        input.close()
    }
}
