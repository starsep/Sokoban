package com.starsep.sokoban.release.res

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button

import com.starsep.sokoban.release.R
import com.starsep.sokoban.release.Sokoban
import com.starsep.sokoban.release.activity.GameActivity
import com.starsep.sokoban.release.database.DatabaseManager
import com.starsep.sokoban.release.view.SquareButton

class LevelAdapter(private val size: Int, private val context: Context) : BaseAdapter() {
    private val buttons: Array<Button> = Array(size, { _ -> SquareButton(context) })

    init {
        for (i in buttons.indices) {
            val levelNumber = i + 1
            buttons[i].setOnClickListener {
                val intent = Intent(context, GameActivity::class.java)
                intent.putExtra(Sokoban.NEW, true)
                intent.putExtra(Sokoban.LEVEL_NUMBER, levelNumber)
                context.startActivity(intent)
            }
            buttons[i].text = String.format(context.getString(R.string.level), levelNumber)
        }
        updateSolvedLevelsButton()
    }

    override fun getCount(): Int {
        return size
    }

    override fun getItem(i: Int): Any? {
        return null
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        return buttons[i]
    }

    fun updateSolvedLevelsButton() {
        DatabaseManager.instance(context).solvedLevels
                .map { it - 1 }
                .filter { it in buttons.indices }
                .forEach { buttons[it].background.setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY) }
    }
}
