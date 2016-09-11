package com.starsep.sokoban.res;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.starsep.sokoban.R;
import com.starsep.sokoban.Sokoban;
import com.starsep.sokoban.activity.GameActivity;
import com.starsep.sokoban.database.DatabaseManager;
import com.starsep.sokoban.view.SquareButton;

import java.util.List;

public class LevelAdapter extends BaseAdapter {
	private final int size;
	private final Context context;
	private final Button[] buttons;

	public LevelAdapter(int size, Context context) {
		this.size = size;
		this.context = context;
		buttons = new Button[size];
		for (int i = 0; i < size; i++) {
			buttons[i] = new SquareButton(context);
			Button button = buttons[i];
			int levelNumber = i + 1;
			button.setOnClickListener(view -> {
				Intent intent = new Intent(context, GameActivity.class);
				intent.putExtra(Sokoban.NEW, true);
				intent.putExtra(Sokoban.LEVEL_NUMBER, levelNumber);
				context.startActivity(intent);
			});
			button.setText(String.format(context.getString(R.string.level), levelNumber));
		}
		updateSolvedLevelsButton();
	}
	@Override
	public int getCount() {
		return size;
	}

	@Override
	public Object getItem(int i) {
		return null;
	}

	@Override
	public long getItemId(int i) {
		return 0;
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		return buttons[i];
	}

	public void updateSolvedLevelsButton() {
		List<Integer> solved = DatabaseManager.instance(context).getSolvedLevels();
		for (int levelNumber : solved) {
			buttons[levelNumber - 1].getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
		}
	}
}
