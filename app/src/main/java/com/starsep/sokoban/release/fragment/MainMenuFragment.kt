package com.starsep.sokoban.release.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.starsep.sokoban.release.R
import com.starsep.sokoban.release.database.Database
import kotlinx.android.synthetic.main.fragment_main_menu.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainMenuFragment : Fragment() {
    private var helpDialog: Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_main_menu, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        newGameButton.setOnClickListener {
            findNavController().navigate(MainMenuFragmentDirections.actionChooseLevel())
        }
        helpButton.setOnClickListener {
            helpDialog = MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.help))
                .setMessage(getString(R.string.help_msg))
                .setPositiveButton(getString(R.string.ok)) { _, _ -> }
                .create()
            helpDialog?.show()
        }
        settingsButton.setOnClickListener {
            findNavController().navigate(MainMenuFragmentDirections.actionMainSettings())
        }
        lifecycleScope.launch(Dispatchers.IO) {
            val gameState = Database.gameStateDao.getCurrentGame() ?: return@launch
            withContext(Dispatchers.Main) {
                continueGameButton.setOnClickListener {
                    findNavController().navigate(
                        MainMenuFragmentDirections.actionContinueLevel(
                            newGame = false,
                            levelNumber = gameState.levelNumber
                        )
                    )
                }
                continueGameButton.visibility = View.VISIBLE
            }
        }
    }

    override fun onStop() {
        super.onStop()
        helpDialog?.dismiss()
    }
}
