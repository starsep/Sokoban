package com.starsep.sokoban;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.starsep.sokoban.database.DatabaseManager;

public class MainMenuActivity extends Activity {
	private Button continueGameButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		continueGameButton = (Button) findViewById(R.id.continueGameButton);
	}

	@Override
	protected void onStart() {
		super.onStart();
		continueGameButton.setVisibility(DatabaseManager.instance(this).getCurrentGame(this) == null ? View.GONE : View.VISIBLE);
	}

	public void newGameButtonClicked(View view) {
		Intent intent = new Intent(this, GameActivity.class);
		intent.putExtra("New", true);
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

	public void continueGameButtonClicked(View view) {
		Intent intent = new Intent(this, GameActivity.class);
		intent.putExtra("New", false);
		startActivity(intent);
	}
}
