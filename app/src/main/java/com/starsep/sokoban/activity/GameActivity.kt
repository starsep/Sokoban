package com.starsep.sokoban.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.starsep.sokoban.R
import com.starsep.sokoban.Sokoban
import com.starsep.sokoban.controls.OnSwipeTouchListener
import com.starsep.sokoban.database.DatabaseManager
import com.starsep.sokoban.gamelogic.HighScore
import com.starsep.sokoban.gamelogic.Moves
import com.starsep.sokoban.model.GameModel
import kotlinx.android.synthetic.main.activity_game.*
import java.util.*

class GameActivity : SokobanActivity()
/*, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener*/ {
    // private lateinit var googleApiClient: GoogleApiClient
    private val gameModel: GameModel by lazy {
        ViewModelProviders.of(this).get(GameModel::class.java)
//        val gameplay = if (newGame) {
//            Gameplay(baseContext, levelNumberLive, this)
//        } else {
//            DatabaseManager.instance(this).getCurrentGame(baseContext)!!
//        }
//        gameplay.gameController = this
//        gameplay
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
        // onGameStart()
    }

    override fun onPause() {
        super.onPause()
        // googleApiClient.disconnect()
        // onGamePause()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

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
            gameModel.repeatLevel(it.context)
        }

        undoButton.setOnClickListener {
            gameModel.undoMove()
        }

        settingsButton.setOnClickListener {
            //TODO: implement
        }
        // googleApiClient = GoogleApiClientBuilder.build(this, gameView)
        gameModel.repeatLevel(baseContext)
        gameModel.statsLive.observe(this, Observer<HighScore> { highScore ->
            val levelNumber = gameModel.levelNumberLive.value
            val minutes = highScore!!.time / 60
            val seconds = highScore.time % 60
            val moves = highScore.moves
            val pushes = highScore.pushes
            statusTextView.text = String.format(getString(R.string.level_status),
                    levelNumber, minutes, seconds, moves, pushes)
        })
        gameModel.movesLive.observe(this, Observer<Moves> {
            DatabaseManager.instance(this).setCurrentGame(gameModel)
        })
        gameModel.wonLive.observe(this, Observer<Boolean> {
            timer?.let {
                it.cancel()
                timer = null
            }
        })
        gameModel.levelNumberLive.observe(this, Observer<Int> {
            timer = Timer()
            timer?.schedule(object : TimerTask() {
                override fun run() {
                    runOnUiThread {
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
