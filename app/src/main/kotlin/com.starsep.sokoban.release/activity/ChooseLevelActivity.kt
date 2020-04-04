package com.starsep.sokoban.release.activity

import android.os.Bundle
import com.starsep.sokoban.release.R
import com.starsep.sokoban.release.res.LevelAdapter
import kotlinx.android.synthetic.main.activity_choose_level.*

class ChooseLevelActivity : SokobanActivity() {
    private val levelAdapter: LevelAdapter by lazy {
        LevelAdapter(resources.getInteger(R.integer.number_of_levels), this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_level)

        gridView.adapter = levelAdapter
    }

    override fun onResume() {
        super.onResume()
        levelAdapter.updateSolvedLevelsButton()
    }
}
