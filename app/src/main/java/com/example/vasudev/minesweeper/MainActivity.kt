package com.example.vasudev.minesweeper

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    final val BLANK_CELL=0
    final val NUMBER_OF_ROWS=13
    final val NUMBER_OF_MINES=25
    final val MINE_CELL=-1
    lateinit var gameCellButtons:Array<Array<MinesweeperButton>>
    lateinit var rowLayouts:Array<LinearLayout>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpBoard()
    }

    private fun setUpBoard() {
        gameContainerLinearLayout.removeAllViews()
        var rowLayout=LinearLayout(this)
        var rowParams=LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,1f)
        rowLayout.layoutParams=rowParams
        rowLayout.orientation=LinearLayout.HORIZONTAL

       // rowLayouts= Array(NUMBER_OF_ROWS,rowLayout)
    }

}
