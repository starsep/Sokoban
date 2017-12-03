package com.starsep.sokoban.release.activity;

import android.content.Intent;
import android.widget.Button;

import com.starsep.sokoban.release.BuildConfig;
import com.starsep.sokoban.release.R;
import com.starsep.sokoban.release.activity.MainMenuActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowAlertDialog;

import static android.view.View.VISIBLE;
import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainMenuActivityTest {
    private MainMenuActivity activity;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity(MainMenuActivity.class)
                .create()
                .resume()
                .get();
    }

    @Test
    public void shouldHaveMenuButtons() {
        Button newGameButton = activity.findViewById(R.id.newGameButton);
        assertEquals(VISIBLE, newGameButton.getVisibility());
        Button helpButton = activity.findViewById(R.id.helpButton);
        assertEquals(VISIBLE, helpButton.getVisibility());
        Button settingsButton = activity.findViewById(R.id.settingsButton);
        assertEquals(VISIBLE, settingsButton.getVisibility());
    }

    @Test
    public void newGameButtonLaunchesChooseLevelActivity() {
        Button newGameButton = activity.findViewById(R.id.newGameButton);
        newGameButton.performClick();
        Intent intent = shadowOf(activity).peekNextStartedActivity();
        assertEquals(ChooseLevelActivity.class.getCanonicalName(), intent.getComponent().getClassName());
    }

    @Test
    public void helpButtonShowsDialog() {
        Button helpButton = activity.findViewById(R.id.helpButton);
        helpButton.performClick();
        ShadowAlertDialog helpDialog = shadowOf(ShadowAlertDialog.getLatestAlertDialog());
        assertEquals(activity.getText(R.string.help_msg), helpDialog.getMessage());
        assertEquals(activity.getText(R.string.help), helpDialog.getTitle());
    }
}
