package com.starsep.sokoban.release.res

import android.graphics.Color
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.starsep.sokoban.release.R
import com.starsep.sokoban.release.database.Database
import com.starsep.sokoban.release.fragment.ChooseLevelFragmentDirections
import com.starsep.sokoban.release.view.SquareButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
            it.findNavController().navigate(
                ChooseLevelFragmentDirections.actionStartLevel(
                    newGame = true,
                    levelNumber = levelNumber
                )
            )
        }
        button.layoutParams
        button.text = String.format(button.context.getString(R.string.level), levelNumber)
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        val button = holder.button
        button.findFragment<Fragment>().lifecycleScope.launch(Dispatchers.IO) {
            val solvedLevels = Database.highScoreDao.solvedLevels()
            val levelNumber = holder.adapterPosition + 1
            if (levelNumber in solvedLevels) {
                withContext(Dispatchers.Main) {
                    button.setBackgroundColor(Color.rgb(0, 221, 12))
                }
            }
        }
    }
}
