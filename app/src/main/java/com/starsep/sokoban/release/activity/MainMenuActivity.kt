package com.starsep.sokoban.release.activity

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.annotation.CallSuper
import android.view.View

import com.starsep.sokoban.release.R
import com.starsep.sokoban.release.Sokoban
import com.starsep.sokoban.release.database.Database
import com.starsep.sokoban.release.view.HelpDialogBuilder
import kotlinx.android.synthetic.main.activity_main_menu.*

class MainMenuActivity : SokobanActivity() {
    private var helpDialog: Dialog? = null

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        setContentView(R.layout.activity_main_menu)
        newGameButton.setOnClickListener {
            val intent = Intent(this, ChooseLevelActivity::class.java)
            startActivity(intent)
        }
        helpButton.setOnClickListener {
            helpDialog = HelpDialogBuilder.build(this)
            helpDialog?.show()
        }
        settingsButton.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        continueGameButton.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra(Sokoban.NEW, false)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        Database.getCurrentGame(baseContext)?.let {
            continueGameButton.visibility = View.VISIBLE
        }
    }

    override fun onStop() {
        super.onStop()
        helpDialog?.dismiss()
    }
}
