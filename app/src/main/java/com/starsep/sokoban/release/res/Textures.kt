package com.starsep.sokoban.release.res

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.starsep.sokoban.release.R
import com.starsep.sokoban.release.gamelogic.Tile
import timber.log.Timber

object Textures {
    private lateinit var wall: Bitmap
    private lateinit var crate: Bitmap
    private lateinit var ground: Bitmap
    private lateinit var endpoint: Bitmap
    lateinit var heroDown: Bitmap
    lateinit var heroUp: Bitmap
    lateinit var heroLeft: Bitmap
    lateinit var heroRight: Bitmap
    private lateinit var crateOnEndpoint: Bitmap

    fun tile(tile: Char) = when (tile) {
        Tile.wall -> wall
        Tile.crate -> crate
        Tile.endpoint -> endpoint
        Tile.crateOnEndpoint -> crateOnEndpoint
        Tile.ground -> ground
        else -> {
            Timber.e("Textures.tile: Unknown tile $tile")
            ground
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
}
