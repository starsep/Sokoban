package com.starsep.sokoban.release.fragment

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.starsep.sokoban.release.R
import com.starsep.sokoban.release.res.LevelIconsAdapter
import kotlinx.android.synthetic.main.fragment_choose_level.*

class ChooseLevelFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_choose_level, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        gridView.adapter = LevelIconsAdapter(resources.getInteger(R.integer.number_of_levels))
        gridView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                super.getItemOffsets(outRect, view, parent, state)
                val margin = resources.getDimension(R.dimen.fragment_choose_level_buttons_margin).toInt()
                outRect.right = margin
                outRect.left = margin
                outRect.top = margin
                outRect.bottom = margin
            }
        })
    }
}
