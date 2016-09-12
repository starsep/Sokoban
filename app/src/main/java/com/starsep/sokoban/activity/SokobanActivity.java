package com.starsep.sokoban.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.starsep.sokoban.R;

public abstract class SokobanActivity extends Activity {
	private static ActivityManager.TaskDescription taskDescription;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
			if (taskDescription == null) {
				Bitmap launcherIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
				taskDescription =
						new ActivityManager.TaskDescription(
								getString(R.string.app_name),
								launcherIcon,
								ContextCompat.getColor(this, R.color.colorTitle)
						);
			}
			setTaskDescription(taskDescription);
		}
	}
}
