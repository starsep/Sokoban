package com.starsep.sokoban.release.activity

import android.view.View
import android.widget.Button
import com.starsep.sokoban.release.MainActivity
import com.starsep.sokoban.release.R
import com.starsep.sokoban.release.database.Database.close
import com.starsep.sokoban.release.fragment.ChooseLevelFragment
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.shadows.ShadowAlertDialog

@RunWith(RobolectricTestRunner::class)
class MainMenuActivityTest : RobolectricTest() {
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
    fun shouldHaveMenuButtons() {
        val newGameButton =
            activity!!.findViewById<Button>(R.id.newGameButton)
        Assert.assertEquals(
            View.VISIBLE.toLong(),
            newGameButton.visibility.toLong()
        )
        val helpButton =
            activity!!.findViewById<Button>(R.id.helpButton)
        Assert.assertEquals(
            View.VISIBLE.toLong(),
            helpButton.visibility.toLong()
        )
        val settingsButton =
            activity!!.findViewById<Button>(R.id.settingsButton)
        Assert.assertEquals(
            View.VISIBLE.toLong(),
            settingsButton.visibility.toLong()
        )
    }

    @Test
    fun newGameButtonLaunchesChooseLevelActivity() {
        val newGameButton =
            activity!!.findViewById<Button>(R.id.newGameButton)
        newGameButton.performClick()
        val intent = Shadows.shadowOf(activity).peekNextStartedActivity()
        Assert.assertEquals(
            ChooseLevelFragment::class.java.canonicalName,
            intent.component!!.className
        )
    }

    @Test
    fun helpButtonShowsDialog() {
        val helpButton =
            activity!!.findViewById<Button>(R.id.helpButton)
        helpButton.performClick()
        val helpDialog =
            Shadows.shadowOf(ShadowAlertDialog.getLatestAlertDialog())
        Assert.assertEquals(activity!!.getText(R.string.help_msg), helpDialog.message)
        Assert.assertEquals(activity!!.getText(R.string.help), helpDialog.title)
    }
}
