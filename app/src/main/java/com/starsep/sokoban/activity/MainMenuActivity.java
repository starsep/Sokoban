package com.starsep.sokoban.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.starsep.sokoban.R;
import com.starsep.sokoban.Sokoban;
import com.starsep.sokoban.database.DatabaseManager;
import com.starsep.sokoban.mvc.FakeGameController;
import com.starsep.sokoban.view.HelpDialogBuilder;

public class MainMenuActivity extends SokobanActivity {
	private Button continueGameButton;
	private Dialog helpDialog;

	@Override
	@CallSuper
	protected void onCreate(@Nullable Bundle savedInstanceState) {
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

	public void newGameButtonClicked(@SuppressWarnings("UnusedParameters") View view) {
		Intent intent = new Intent(this, ChooseLevelActivity.class);
		startActivity(intent);
	}

	public void helpButtonClicked(@SuppressWarnings("UnusedParameters") View view) {
		helpDialog = HelpDialogBuilder.build(this);
		helpDialog.show();
	}

	@SuppressWarnings("EmptyMethod")
	public void settingsButtonClicked(@SuppressWarnings("UnusedParameters") View view) {
		// TODO: implement
	}

	public void continueGameButtonClicked(@SuppressWarnings("UnusedParameters") View view) {
		Intent intent = new Intent(this, GameActivity.class);
		intent.putExtra(Sokoban.NEW, false);
		startActivity(intent);
	}
}
