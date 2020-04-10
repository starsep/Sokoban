package com.starsep.sokoban.release.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import com.starsep.sokoban.release.R

object HelpDialogBuilder {
    fun build(context: Context): Dialog = AlertDialog.Builder(context)
        .setTitle(context.getString(R.string.help))
        .setMessage(context.getString(R.string.help_msg))
        .setPositiveButton(context.getString(R.string.ok)) { _, _ -> }
        .create()
}
