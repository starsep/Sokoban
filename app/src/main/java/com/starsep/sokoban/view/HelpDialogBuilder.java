package com.starsep.sokoban.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import com.starsep.sokoban.R;

public class HelpDialogBuilder {
	private HelpDialogBuilder() { }

	public static Dialog build(Context context) {
		return new AlertDialog.Builder(context)
				.setTitle(context.getString(R.string.help))
				.setMessage(context.getString(R.string.help_msg))
				.setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				})
				.create();
	}
}
