package com.starsep.sokoban.release.res

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log

import com.starsep.sokoban.release.R
import com.starsep.sokoban.release.Sokoban
import com.starsep.sokoban.release.gamelogic.Tile

object Textures {
    private lateinit var wall: Bitmap
    private lateinit var crate: Bitmap
    private lateinit var ground: Bitmap
    private lateinit var endpoint: Bitmap
    private lateinit var heroDown: Bitmap
    private lateinit var heroUp: Bitmap
    private lateinit var heroLeft: Bitmap
    private lateinit var heroRight: Bitmap
    private lateinit var crateOnEndpoint: Bitmap

    fun tile(tile: Char): Bitmap {
        return when (tile) {
            Tile.wall -> wall
            Tile.crate -> crate
            Tile.endpoint -> endpoint
            Tile.crateOnEndpoint -> crateOnEndpoint
            Tile.ground -> ground
            else -> {
                Log.e(Sokoban.TAG, "Textures.tile: " + "Unknown tile " + tile)
                ground
            }
        }
    }

    fun init(context: Context) {
        wall = BitmapFactory.decodeResource(context.resources, R.drawable.wall)
        crate = BitmapFactory.decodeResource(context.resources, R.drawable.crate)
        ground = BitmapFactory.decodeResource(context.resources, R.drawable.ground)
        endpoint = BitmapFactory.decodeResource(context.resources, R.drawable.endpoint)
        heroDown = BitmapFactory.decodeResource(context.resources, R.drawable.hero_down)
        heroUp = BitmapFactory.decodeResource(context.resources, R.drawable.hero_up)
        heroLeft = BitmapFactory.decodeResource(context.resources, R.drawable.hero_left)
        heroRight = BitmapFactory.decodeResource(context.resources, R.drawable.hero_right)
        crateOnEndpoint = BitmapFactory.decodeResource(context.resources, R.drawable.crate_on_endpoint)
    }

    fun heroDown(): Bitmap {
        return heroDown
    }

    fun heroUp(): Bitmap {
        return heroUp
    }

    fun heroLeft(): Bitmap {
        return heroLeft
    }

    fun heroRight(): Bitmap {
        return heroRight
    }
}
