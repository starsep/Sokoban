package com.starsep.sokoban.release.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.starsep.sokoban.release.BuildConfig;
import com.starsep.sokoban.release.R;
import com.starsep.sokoban.release.activity.GameActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;

import static android.view.View.VISIBLE;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class ChooseLevelActivityTest {
    private ChooseLevelActivity activity;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity(ChooseLevelActivity.class)
                .create()
                .resume()
                .get();
    }

    @Test
    public void gridViewShouldBeVisible() {
        GridView gridView = activity.findViewById(R.id.gridView);
        assertEquals(VISIBLE, gridView.getVisibility());
    }

    @Test
    public void gridViewShouldHaveLevelButtons() {
        GridView gridView = activity.findViewById(R.id.gridView);
        final int numberOfLevels = activity.getResources().getInteger(R.integer.number_of_levels);
        assertEquals(numberOfLevels, gridView.getCount());
        for (int i = 0; i < numberOfLevels; i++) {
            View view = gridView.getAdapter().getView(i, null, gridView);
            assertTrue(view instanceof Button);
        }
    }

    @Test
    public void levelButtonLaunchesGameActivity() {
        GridView gridView = activity.findViewById(R.id.gridView);
        Button levelButton = (Button) gridView.getAdapter().getView(0, null, gridView);
        levelButton.performClick();
        Intent intent = Shadows.shadowOf(activity).peekNextStartedActivity();
        assertEquals(GameActivity.class.getCanonicalName(), intent.getComponent().getClassName());
    }
}
