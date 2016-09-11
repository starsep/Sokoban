package com.starsep.sokoban.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

import com.starsep.sokoban.res.LevelAdapter;
import com.starsep.sokoban.R;

public class ChooseLevelActivity extends Activity {
	private LevelAdapter levelAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_level);

		levelAdapter = new LevelAdapter(getResources().getInteger(R.integer.number_of_levels), this);

		GridView gridview = (GridView) findViewById(R.id.gridView);
		gridview.setAdapter(levelAdapter);
	}

	@Override
	protected void onStart() {
		super.onStart();
		levelAdapter.updateSolvedLevelsButton();
	}
}
