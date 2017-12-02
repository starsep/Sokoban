package com.starsep.sokoban.activity

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.annotation.CallSuper
import android.view.View
import android.widget.Button

import com.starsep.sokoban.R
import com.starsep.sokoban.Sokoban
import com.starsep.sokoban.database.DatabaseManager
import com.starsep.sokoban.mvc.FakeGameController
import com.starsep.sokoban.view.HelpDialogBuilder

class MainMenuActivity : SokobanActivity() {
    private var continueGameButton: Button? = null
    private var helpDialog: Dialog? = null

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        continueGameButton = findViewById<View>(R.id.continueGameButton) as Button
    }

    override fun onStart() {
        super.onStart()
        continueGameButton!!.visibility = if (DatabaseManager.instance(this).getCurrentGame(FakeGameController(this)) == null) View.GONE else View.VISIBLE
    }

    override fun onStop() {
        super.onStop()
        if (helpDialog != null) {
            helpDialog!!.dismiss()
        }
    }

    fun newGameButtonClicked(view: View) {
        val intent = Intent(this, ChooseLevelActivity::class.java)
        startActivity(intent)
    }

    fun helpButtonClicked(view: View) {
        helpDialog = HelpDialogBuilder.build(this)
        helpDialog!!.show()
    }

    fun settingsButtonClicked(view: View) {
        // TODO: implement
    }

    fun continueGameButtonClicked(view: View) {
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra(Sokoban.NEW, false)
        startActivity(intent)
    }
}
