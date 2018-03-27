package com.example.vasudev.minesweeper.ScoreKeeping

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.vasudev.minesweeper.R
import kotlinx.android.synthetic.main.activity_scores.*

class ScoresActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scores)
        loadingAnimView.playAnimation()
        val scoreTransitions=ScoreTransitions()
        scoreTransitions.fetchScoreFromDatabase()
        loadingAnimView.cancelAnimation()
        scoreAnimView.playAnimation()
    }
}
