package com.starsep.sokoban.release.res

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import androidx.navigation.findNavController
import com.starsep.sokoban.release.R
import com.starsep.sokoban.release.database.Database
import com.starsep.sokoban.release.fragment.ChooseLevelFragmentDirections
import com.starsep.sokoban.release.view.SquareButton

class LevelAdapter(private val size: Int, private val context: Context) : BaseAdapter() {
    private val buttons: Array<Button> = Array(size) { SquareButton(context) }

    init {
        for (i in buttons.indices) {
            val levelNumber = i + 1
            buttons[i].setOnClickListener {
                it.findNavController().navigate(ChooseLevelFragmentDirections.actionStartLevel(
                    newGame = true,
                    levelNumber = levelNumber
                ))
            }
            buttons[i].text = String.format(context.getString(R.string.level), levelNumber)
        }
        updateSolvedLevelsButton()
    }

    override fun getCount() = size
    override fun getItem(i: Int): Any? = null
    override fun getItemId(i: Int) = 0L
    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View = buttons[i]

    fun updateSolvedLevelsButton() {
        Database.solvedLevels()
            .map { it - 1 }
            .filter { it in buttons.indices }
            .forEach { buttons[it].setBackgroundColor(Color.rgb(0, 221, 12)) }
    }
}
