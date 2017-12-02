package com.starsep.sokoban.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import com.starsep.sokoban.R

object HelpDialogBuilder {
    fun build(context: Context): Dialog {
        return AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.help))
                .setMessage(context.getString(R.string.help_msg))
                .setPositiveButton(context.getString(R.string.ok)) { _, _ -> }
                .create()
    }
}
