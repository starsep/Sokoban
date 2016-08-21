package com.starsep.sokoban;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainMenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
	}

	public void startButtonClicked(View view) {
		Intent intent = new Intent(this, GameActivity.class);
		startActivity(intent);
	}
}
