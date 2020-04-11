package com.starsep.sokoban.release.res

import android.graphics.Color
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.starsep.sokoban.release.R
import com.starsep.sokoban.release.database.Database
import com.starsep.sokoban.release.fragment.ChooseLevelFragmentDirections
import com.starsep.sokoban.release.view.SquareButton

class LevelIconsAdapter(private val size: Int) : RecyclerView.Adapter<LevelIconsAdapter.ViewHolder>() {
    class ViewHolder(val button: SquareButton) : RecyclerView.ViewHolder(button)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val button = SquareButton(parent.context)
        return ViewHolder(button)
    }

    override fun getItemCount() = size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val levelNumber = position + 1
        val button = holder.button
        button.setOnClickListener {
            it.findNavController().navigate(ChooseLevelFragmentDirections.actionStartLevel(
                newGame = true,
                levelNumber = levelNumber
            ))
        }
        button.layoutParams
        button.text = String.format(button.context.getString(R.string.level), levelNumber)
        if (levelNumber in Database.solvedLevels()) {
            button.setBackgroundColor(Color.rgb(0, 221, 12))
        }
    }
}
