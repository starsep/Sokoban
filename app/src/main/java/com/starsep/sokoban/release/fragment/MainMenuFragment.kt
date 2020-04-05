package com.starsep.sokoban.release.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.starsep.sokoban.release.R
import com.starsep.sokoban.release.database.Database
import com.starsep.sokoban.release.view.HelpDialogBuilder
import kotlinx.android.synthetic.main.fragment_main_menu.*

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
            helpDialog = HelpDialogBuilder.build(requireContext())
            helpDialog?.show()
        }
        settingsButton.setOnClickListener {
            findNavController().navigate(MainMenuFragmentDirections.actionMainSettings())
        }
        continueGameButton.setOnClickListener {
            val levelNumber = 4 // TODO
            findNavController().navigate(MainMenuFragmentDirections.actionContinueLevel(
                newGame = false,
                levelNumber = levelNumber
            ))
        }
    }

    override fun onResume() {
        super.onResume()
        Database.getCurrentGame(requireContext())?.let {
            continueGameButton.visibility = View.VISIBLE
        }
    }

    override fun onStop() {
        super.onStop()
        helpDialog?.dismiss()
    }
}
