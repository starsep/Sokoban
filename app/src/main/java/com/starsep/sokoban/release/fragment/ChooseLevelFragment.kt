package com.starsep.sokoban.release.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.starsep.sokoban.release.R
import com.starsep.sokoban.release.res.LevelAdapter
import kotlinx.android.synthetic.main.fragment_choose_level.*

class ChooseLevelFragment : Fragment() {
    private val levelAdapter: LevelAdapter by lazy {
        LevelAdapter(resources.getInteger(R.integer.number_of_levels), requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_choose_level, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        gridView.adapter = levelAdapter
    }

    override fun onResume() {
        super.onResume()
        levelAdapter.updateSolvedLevelsButton()
    }
}
