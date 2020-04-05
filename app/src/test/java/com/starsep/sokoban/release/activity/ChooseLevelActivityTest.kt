package com.starsep.sokoban.release.activity

import android.view.View
import android.widget.Button
import android.widget.GridView
import com.starsep.sokoban.release.MainActivity
import com.starsep.sokoban.release.R
import com.starsep.sokoban.release.database.Database.close
import com.starsep.sokoban.release.fragment.GameFragment
import junit.framework.TestCase
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows

@RunWith(RobolectricTestRunner::class)
class ChooseLevelActivityTest : RobolectricTest() {
    private var activity: MainActivity? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        activity = Robolectric.buildActivity(MainActivity::class.java)
            .create()
            .resume()
            .get()
    }

    @After
    fun after() {
        close()
    }

    @Test
    fun gridViewShouldBeVisible() {
        val gridView = activity!!.findViewById<GridView>(R.id.gridView)
        Assert.assertEquals(
            View.VISIBLE.toLong(),
            gridView.visibility.toLong()
        )
    }

    @Test
    fun gridViewShouldHaveLevelButtons() {
        val gridView = activity!!.findViewById<GridView>(R.id.gridView)
        val numberOfLevels = activity!!.resources.getInteger(R.integer.number_of_levels)
        Assert.assertEquals(numberOfLevels.toLong(), gridView.count.toLong())
        for (i in 0 until numberOfLevels) {
            val view = gridView.adapter.getView(i, null, gridView)
            TestCase.assertTrue(view is Button)
        }
    }

    @Test
    fun levelButtonLaunchesGameActivity() {
        val gridView = activity!!.findViewById<GridView>(R.id.gridView)
        val levelButton =
            gridView.adapter.getView(0, null, gridView) as Button
        levelButton.performClick()
        val intent = Shadows.shadowOf(activity).peekNextStartedActivity()
        Assert.assertEquals(
            GameFragment::class.java.canonicalName,
            intent.component!!.className
        )
    }
}
