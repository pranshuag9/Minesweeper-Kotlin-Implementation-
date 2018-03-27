package com.example.vasudev.minesweeper.ScoreKeeping

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.vasudev.minesweeper.R
import kotlinx.android.synthetic.main.activity_scores.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class ScoresActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scores)

        //showing loading progress
        loadingRelativeLayout.visibility=View.VISIBLE
        scoreListLinearLayout.visibility=View.GONE
        loadingAnimView.playAnimation()

        //fetching score
        val scoreTransitions=ScoreTransitions()
        scoreTransitions.fetchScoreFromDatabase()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun renderScore(scoreRender: ScoreRender){

        //hiding loading progress
        loadingRelativeLayout.visibility=View.GONE
        loadingAnimView.cancelAnimation()
        scoreListLinearLayout.visibility=View.VISIBLE

        //rendering fetched score from database in view
        scoreAnimView.playAnimation()
        scoreRecyclerView.layoutManager=LinearLayoutManager(this@ScoresActivity)
        scoreRecyclerView.adapter=ScoreListAdapter(context = this@ScoresActivity,scoreList = scoreRender.arrayList)
        scoreRecyclerView.layoutManager=LinearLayoutManager(this@ScoresActivity)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this@ScoresActivity)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this@ScoresActivity)
    }
}
