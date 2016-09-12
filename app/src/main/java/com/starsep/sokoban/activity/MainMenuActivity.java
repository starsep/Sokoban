package com.starsep.sokoban.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.starsep.sokoban.Sokoban;
import com.starsep.sokoban.mvc.FakeGameController;
import com.starsep.sokoban.R;
import com.starsep.sokoban.database.DatabaseManager;
import com.starsep.sokoban.view.HelpDialogBuilder;

public class MainMenuActivity extends SokobanActivity {
	private Button continueGameButton;
	private Dialog helpDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		continueGameButton = (Button) findViewById(R.id.continueGameButton);
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (helpDialog != null) {
			helpDialog.dismiss();
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		continueGameButton.setVisibility(DatabaseManager.instance(this).getCurrentGame(new FakeGameController(this)) == null ? View.GONE : View.VISIBLE);
	}

	public void newGameButtonClicked(View view) {
		Intent intent = new Intent(this, ChooseLevelActivity.class);
		startActivity(intent);
	}

	public void helpButtonClicked(View view) {
		helpDialog = HelpDialogBuilder.build(this);
		helpDialog.show();
	}

	public void settingsButtonClicked(View view) {
		// Dialog settingsDialog = SettingsDialog.build(getApplicationContext());
		// settingsDialog.show();
	}

	public void continueGameButtonClicked(View view) {
		Intent intent = new Intent(this, GameActivity.class);
		intent.putExtra(Sokoban.NEW, false);
		startActivity(intent);
	}
}
