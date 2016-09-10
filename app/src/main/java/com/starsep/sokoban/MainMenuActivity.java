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

	public void newGameButtonClicked(View view) {
		Intent intent = new Intent(this, GameActivity.class);
		startActivity(intent);
	}

	public void helpButtonClicked(View view) {
		// Intent intent = new Intent(this, HelpActivity.class);
		// startActivity(intent);
	}

	public void settingsButtonClicked(View view) {
		// Dialog settingsDialog = SettingsDialog.build(getApplicationContext());
		// settingsDialog.show();
	}
	
	public void exitButtonClicked(View view) {
	}
}
