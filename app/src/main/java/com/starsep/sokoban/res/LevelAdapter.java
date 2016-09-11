package com.starsep.sokoban.res;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.starsep.sokoban.R;
import com.starsep.sokoban.view.SquareButton;

public class LevelAdapter extends BaseAdapter {
	private final int size;
	private final Context context;

	public LevelAdapter(int size, Context context) {
		this.size = size;
		this.context = context;
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
		Button result = new SquareButton(context);
		int levelNumber = i + 1;
		result.setText(String.format(context.getResources().getString(R.string.level), levelNumber));
		boolean levelFinished = i % 2 == 0;
		if (levelFinished) {
			result.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
		}
		return result;
	}
}
