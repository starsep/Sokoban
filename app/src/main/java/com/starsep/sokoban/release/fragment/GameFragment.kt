package com.starsep.sokoban.release.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.starsep.sokoban.release.R
import com.starsep.sokoban.release.controls.OnSwipeTouchListener
import com.starsep.sokoban.release.database.Database
import com.starsep.sokoban.release.gamelogic.HighScore
import com.starsep.sokoban.release.gamelogic.Moves
import com.starsep.sokoban.release.model.GameModel
import java.util.*
import kotlinx.android.synthetic.main.fragment_game.*

class GameFragment : Fragment()
/*, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener*/ {
    // private lateinit var googleApiClient: GoogleApiClient
    private val gameModel: GameModel by lazy {
        ViewModelProviders.of(this).get(GameModel::class.java)
    }
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
        super.onCreate(savedInstanceState)
        setupGameModel()
        setupUI()
        // googleApiClient = GoogleApiClientBuilder.build(this, gameView)
    }*/
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        gameView.setOnTouchListener(object : OnSwipeTouchListener(requireContext()) {
            override fun onSwipeRight() {
                gameModel.moveRight()
            }

            override fun onSwipeLeft() {
                gameModel.moveLeft()
            }

            override fun onSwipeTop() {
                gameModel.moveUp()
            }

            override fun onSwipeBottom() {
                gameModel.moveDown()
            }
        })

        resetButton.setOnClickListener {
            gameModel.resetLevel(it.context)
        }

        undoButton.setOnClickListener {
            gameModel.undoMove()
        }

        settingsButton.setOnClickListener {
            findNavController().navigate(GameFragmentDirections.actionGameSettings())
        }
    }

    private fun setupGameModel() {
        if (args.newGame) {
            gameModel.startLevel(requireContext(), args.levelNumber)
        } else {
            val gameState = Database.getCurrentGame(requireContext())
            if (gameState == null) {
                gameModel.startLevel(requireContext(), args.levelNumber)
            } else {
                gameModel.startLevel(requireContext(), gameState.levelNumber)
                gameModel.setTime(gameState.time)
                gameModel.makeMoves(gameState.movesList)
            }
        }
        gameModel.statsLive.observe(viewLifecycleOwner, Observer<HighScore> { highScore ->
            val levelNumber = gameModel.levelNumber()
            val minutes = highScore!!.time / 60
            val seconds = highScore.time % 60
            val moves = highScore.moves
            val pushes = highScore.pushes
            statusTextView.text = String.format(getString(R.string.level_status),
                    levelNumber, minutes, seconds, moves, pushes)
        })
        gameModel.movesLive.observe(viewLifecycleOwner, Observer<Moves> {
            Database.setCurrentGame(requireContext(), gameModel.gameState())
        })
        gameModel.wonLive.observe(viewLifecycleOwner, Observer<Boolean> {
            timer?.let {
                it.cancel()
                timer = null
            }
        })
        gameModel.levelNumberLive.observe(viewLifecycleOwner, Observer<Int> {
            timer = Timer()
            timer?.schedule(object : TimerTask() {
                override fun run() {
                    activity?.runOnUiThread {
                        gameModel.onSecondElapsed()
                    }
                }
            }, 0, 1000)
        })
    }

    fun isInEditMode(): Boolean {
        return gameView.isInEditMode
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
}
