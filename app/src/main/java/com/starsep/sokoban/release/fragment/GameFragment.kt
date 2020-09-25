package com.starsep.sokoban.release.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.starsep.sokoban.release.R
import com.starsep.sokoban.release.database.Database
import com.starsep.sokoban.release.model.GameModel
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class GameFragment : Fragment()
/*, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener*/ {
    // private lateinit var googleApiClient: GoogleApiClient
    private val gameModel by viewModel<GameModel>()
    private val args: GameFragmentArgs by navArgs()
    private var timer: Timer? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setupGameModel()
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onStart() {
        super.onStart()
        // googleApiClient.connect()
    }

    override fun onPause() {
        super.onPause()
        // googleApiClient.disconnect()
    }

    /* override fun onCreate(savedInstanceState: Bundle?) {
        // googleApiClient = GoogleApiClientBuilder.build(this, gameView)
    }*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        resetButton.setOnClickListener {
            gameModel.onResetLevel()
        }

        undoButton.setOnClickListener {
            gameModel.onUndoMove()
        }

        settingsButton.visibility = View.GONE
        settingsButton.setOnClickListener {
            findNavController().navigate(GameFragmentDirections.actionGameSettings())
        }
    }

    private fun resetTimer() {
        timer?.let {
            it.cancel()
            timer = null
        }
    }

    private fun setupGameModel() {
        if (args.newGame) {
            gameModel.startLevel(args.levelNumber)
        } else {
            lifecycleScope.launchWhenStarted {
                when (val gameState = Database.gameStateDao.getCurrentGame()) {
                    null -> gameModel.startLevel(args.levelNumber)
                    else -> {
                        gameModel.startLevel(gameState.levelNumber)
                        gameModel.setTime(gameState.time)
                        gameModel.makeMoves(gameState.movesList)
                    }
                }
            }
        }
        gameModel.statsLive.observe(
            viewLifecycleOwner,
            Observer { highScore ->
                if (highScore == null) return@Observer
                val levelNumber = gameModel.levelNumber()
                val minutes = highScore.time / 60
                val seconds = highScore.time % 60
                val moves = highScore.moves
                val pushes = highScore.pushes
                statusTextView.text = String.format(
                    getString(R.string.level_status),
                    levelNumber, minutes, seconds, moves, pushes
                )
            }
        )
        gameModel.movesLive.observe(
            viewLifecycleOwner,
            Observer {
                val gameState = gameModel.gameState()
                lifecycleScope.launch(Dispatchers.IO) {
                    Database.gameStateDao.setCurrentGame(gameState)
                }
            }
        )
        gameModel.wonLive.observe(
            viewLifecycleOwner,
            Observer {
                resetTimer()
            }
        )
        gameModel.levelNumberLive.observe(
            viewLifecycleOwner,
            Observer {
                timer = Timer()
                timer?.schedule(
                    object : TimerTask() {
                        override fun run() {
                            activity?.runOnUiThread {
                                gameModel.onSecondElapsed()
                            }
                        }
                    },
                    0, 1000
                )
            }
        )
    }

    /*override fun onConnected(bundle: Bundle?) {
        Log.d(Sokoban.TAG, "onConnected(): connected to Google APIs")
        Games.setViewForPopups(googleApiClient, window.decorView.findViewById(android.R.id.content))
    }

    override fun onConnectionSuspended(i: Int) {

    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Log.d(Sokoban.TAG, "onConnectionFailed(): attempting to resolve" + connectionResult)
        try {
            connectionResult.startResolutionForResult(this, 9143)
        } catch (e: IntentSender.SendIntentException) {
            e.printStackTrace()
        }
    }*/

    override fun onDestroy() {
        resetTimer()
        super.onDestroy()
    }
}
