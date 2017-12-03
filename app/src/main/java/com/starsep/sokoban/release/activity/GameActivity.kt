package com.starsep.sokoban.release.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import com.starsep.sokoban.release.R
import com.starsep.sokoban.release.Sokoban
import com.starsep.sokoban.release.controls.OnSwipeTouchListener
import com.starsep.sokoban.release.database.DatabaseManager
import com.starsep.sokoban.release.gamelogic.HighScore
import com.starsep.sokoban.release.gamelogic.Moves
import com.starsep.sokoban.release.model.GameModel
import kotlinx.android.synthetic.main.activity_game.*
import java.util.*

class GameActivity : SokobanActivity()
/*, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener*/ {
    // private lateinit var googleApiClient: GoogleApiClient
    private val gameModel: GameModel by lazy {
        ViewModelProviders.of(this).get(GameModel::class.java)
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
    }

    override fun onPause() {
        super.onPause()
        // googleApiClient.disconnect()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupGameModel()
        setupUI()
        // googleApiClient = GoogleApiClientBuilder.build(this, gameView)
    }

    private fun setupUI() {
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
            gameModel.resetLevel(it.context)
        }

        undoButton.setOnClickListener {
            gameModel.undoMove()
        }

        settingsButton.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }

    private fun setupGameModel() {
        gameModel.startLevel(baseContext, levelNumber)
        gameModel.statsLive.observe(this, Observer<HighScore> { highScore ->
            val levelNumber = gameModel.levelNumber()
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
