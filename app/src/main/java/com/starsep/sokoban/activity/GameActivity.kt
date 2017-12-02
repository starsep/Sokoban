package com.starsep.sokoban.activity

import android.content.Context
import android.os.Bundle
import com.starsep.sokoban.R
import com.starsep.sokoban.Sokoban
import com.starsep.sokoban.controls.OnSwipeTouchListener
import com.starsep.sokoban.database.DatabaseManager
import com.starsep.sokoban.gamelogic.Gameplay
import com.starsep.sokoban.mvc.GameController
import com.starsep.sokoban.mvc.GameModel
import kotlinx.android.synthetic.main.activity_game.*
import java.util.*

class GameActivity : SokobanActivity(), GameController
/*, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener*/ {
    override val ctx: Context
        get() = baseContext
    // private lateinit var googleApiClient: GoogleApiClient
    private val gameModel: GameModel by lazy {
        val gameplay = if (newGame) {
            Gameplay(levelNumber, this)
        } else {
            DatabaseManager.instance(this).getCurrentGame(ctx)!!
        }
        gameplay.gameController = this
        gameplay
    }
    private val newGame: Boolean by lazy {
        intent.extras.getBoolean(Sokoban.NEW, false)
    }
    private val levelNumber: Int by lazy {
        intent.extras.getInt(Sokoban.LEVEL_NUMBER, 1)
    }

    private var timer: Timer? = null

    override fun onStart() {
        super.onStart()
        // googleApiClient.connect()
        onGameStart()
    }

    override fun onPause() {
        super.onPause()
        // googleApiClient.disconnect()
        onGamePause()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        gameModel.gameController = this
        gameModel.viewListener = gameView

        gameView.setGameController(this)
        gameView.gameModel = gameModel
        gameView.setOnTouchListener(object : OnSwipeTouchListener(baseContext) {
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
            gameModel.repeatLevel()
        }

        undoButton.setOnClickListener {
            gameModel.undoMove()
        }

        settingsButton.setOnClickListener {
            //TODO: implement
        }
        // googleApiClient = GoogleApiClientBuilder.build(this, gameView)
    }

    override fun onStatsChanged() {
        val levelNumber = gameModel.level().number()
        val highScore = gameModel.stats()
        val minutes = highScore.time / 60
        val seconds = highScore.time % 60
        val moves = highScore.moves
        val pushes = highScore.pushes
        statusTextView.text = String.format(getString(R.string.level_status),
                levelNumber, minutes, seconds, moves, pushes)
    }

    override fun onGamePause() {
        timer?.let {
            it.cancel()
            timer = null
        }
    }

    override fun onSaveGame(game: Gameplay) {
        DatabaseManager.instance(this).setCurrentGame(game)
    }

    override fun onNewGame() {
        onGameStart()
    }

    override fun editMode(): Boolean {
        return gameView.isInEditMode
    }

    private fun onGameStart() {
        onGamePause()
        timer = Timer()
        timer?.schedule(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    gameModel.onSecondElapsed()
                }
            }
        }, 0, 1000)
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
