package com.starsep.sokoban.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

import com.starsep.sokoban.res.LevelAdapter;
import com.starsep.sokoban.R;

public class ChooseLevelActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_level);

		GridView gridview = (GridView) findViewById(R.id.gridView);
		gridview.setAdapter(new LevelAdapter(100, this));
	}
}
