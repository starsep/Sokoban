package com.starsep.sokoban.release.activity

// @RunWith(RobolectricTestRunner::class)
class MainMenuActivityTest : RobolectricTest() {
    // TODO: fix
    /*private var activity: MainActivity? = null

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
    }*/
}
