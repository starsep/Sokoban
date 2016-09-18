package com.starsep.sokoban.view;

import android.content.Context;
import android.widget.Button;

public class SquareButton extends Button {
	public SquareButton(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int size = Math.max(widthMeasureSpec, heightMeasureSpec);
		super.onMeasure(
				MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY),
				MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY)
		);
	}
}