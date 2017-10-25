package com.example.vasudev.minesweeper

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener, View.OnLongClickListener {
    private val NUMBER_OF_ROWS=13
    private val NUMBER_OF_MINES=25
    private val BLANK_CELL=0
    private val MINE_CELL_SCORE_ONE=1
    private val MINE_CELL_SCORE_TWO=2
    private val MINE_CELL_SCORE_THREE=3
    private val MINE_CELL_SCORE_FOUR=4
    private val MINE_CELL_SCORE_FIVE=5
    private val MINE_CELL_SCORE_SIX=6
    private val MINE_CELL_SCORE_SEVEN=7
    private val MINE_CELL_SCORE_EIGHT=8
    private var GAME_OVER_CHECKER=false
    private var NUMBER_OF_CORRECT_FLAGS=0
    private var NUMBER_OF_FLAGS=NUMBER_OF_MINES
    private var CURRENT_SCORE=0
    private val X_COORDINATE_ARRAY= intArrayOf(-1,0,1,-1,1,-1,0,1)
    private val Y_COORDINATE_ARRAY= intArrayOf(-1,-1,-1,0,0,1,1,1)
    lateinit var gameCellButtons:Array<Array<MinesweeperButton>>
    lateinit var rowLayouts:ArrayList<LinearLayout>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpBoard()
    }
    private fun setUpBoard() {
        gameContainerLinearLayout.removeAllViews()
        gameCellButtons= Array(NUMBER_OF_ROWS,{
            Array(NUMBER_OF_ROWS,{
                MinesweeperButton(this)
            })
        })
        var i=0
        var j=0
        rowLayouts= ArrayList()
        while (i<NUMBER_OF_ROWS){
            var rowLayout=LinearLayout(this)
            var rowParams=LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,1f)
            rowLayout.layoutParams=rowParams
            rowLayout.orientation=LinearLayout.HORIZONTAL
            rowLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.colorRowBackgroundGrey))
            rowLayouts.add(rowLayout)
            gameContainerLinearLayout.addView(rowLayout)
            i += 1
        }
        i=0
        while (i<NUMBER_OF_ROWS){
            while (j<NUMBER_OF_ROWS){
                val currentButton=gameCellButtons[i][j]
                currentButton.rowId=i
                currentButton.colId=j
                currentButton.isVisited=false
                val rowParams=LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.MATCH_PARENT,1f)
                rowParams.setMargins(1,1,1,1)
                currentButton.layoutParams=rowParams
                currentButton.gravity=Gravity.CENTER
                currentButton.setBackgroundResource(R.drawable.unclicked_button)
                currentButton.setOnClickListener(this)
                currentButton.setOnLongClickListener(this)
                rowLayouts[i].addView(currentButton)
                j+=1
            }
            i+=1
        }
        setMines()

    }
    private fun setMines(){
        var i=0
        while (i<NUMBER_OF_MINES){
            val random=Random()
            var mineRowId=random.nextInt(NUMBER_OF_ROWS)
            var mineColId=random.nextInt(NUMBER_OF_ROWS)
            var currentButton=gameCellButtons[mineRowId][mineColId]
            if(currentButton.checkMine()){
                continue
            }
            currentButton.setAsMine()
            var j=0
            while (j<X_COORDINATE_ARRAY.size){
                var currentRowId=mineRowId+X_COORDINATE_ARRAY[j]
                var currentColId=mineColId+Y_COORDINATE_ARRAY[j]
                if(currentColId<0 || currentColId>=NUMBER_OF_ROWS){
                    continue
                }
                if(currentRowId<0 || currentRowId>=NUMBER_OF_ROWS){
                    continue
                }
                if(gameCellButtons[currentRowId][currentColId].checkMine()){
                    continue
                }
                gameCellButtons[currentRowId][currentColId].score+=1
                j+=1
            }
            i+=1
        }
    }
    private fun onLoss(clickedMine:MinesweeperButton){
        var i=0
        var j=0
        while (i<NUMBER_OF_ROWS){
            while (j<NUMBER_OF_ROWS){
                val currentButton=gameCellButtons[i][j]
                if(currentButton.checkMine()){
                    if(currentButton.checkFlagged()){
                        j+=1
                        continue
                    }else if(currentButton==clickedMine){
                        currentButton.setBackgroundResource(R.drawable.clicked_mine)
                    }else{
                        currentButton.setBackgroundResource(R.drawable.unclicked_mine)
                    }
                }else if(currentButton.checkFlagged()){
                    if(currentButton.checkMine()){
                        currentButton.setBackgroundResource(R.drawable.flag)
                    }else{
                        currentButton.setBackgroundResource(R.drawable.incorrect_flag)
                    }
                }else{

                }
                j+=1
            }
            i+=1
        }
    }
    private fun onGameOver(){
        var i=0
        var j=0
        while (i<NUMBER_OF_ROWS){
            while (j<NUMBER_OF_ROWS){
                val currentButton=gameCellButtons[i][j]
                currentButton.isClickable=false
                j+=1
            }
            i+=1
        }
    }
    override fun onClick(p0: View?) {
        if(GAME_OVER_CHECKER){
            return
        }
        val currentButton:MinesweeperButton = p0 as MinesweeperButton
        if(currentButton.checkVisited()){
//            var neighbouringFlags=0
//            var i=0
//            while (i<X_COORDINATE_ARRAY.size){
//                var rowId=currentButton.rowId+X_COORDINATE_ARRAY[i]
//                var colId=currentButton.colId+Y_COORDINATE_ARRAY[i]
//                if (rowId>=NUMBER_OF_ROWS || rowId<0 || colId>=NUMBER_OF_ROWS || colId<0){
//                    i += 1
//                    continue
//                }
//                if(gameCellButtons[rowId][colId].checkFlagged()){
//                    neighbouringFlags+=1
//                }s
//                if(currentButton.score==neighbouringFlags){
//
//                }
//            }
            return
        }
        else if(currentButton.checkFlagged()){
            return
        }else if(currentButton.checkMine()){
            currentButton.markVisited()
            GAME_OVER_CHECKER=true
            Toast.makeText(this,"GAME OVER!",Toast.LENGTH_SHORT).show()
            onLoss(currentButton)
            onGameOver()
            return
        }else if(currentButton.score != BLANK_CELL && !currentButton.checkMine() && !currentButton.checkFlagged() && !currentButton.checkMine()){
            when {
                currentButton.score==MINE_CELL_SCORE_ONE -> {
                    currentButton.setBackgroundResource()
                    CURRENT_SCORE += MINE_CELL_SCORE_ONE
                }
                currentButton.score==MINE_CELL_SCORE_TWO -> {
                    currentButton.setBackgroundResource()
                    CURRENT_SCORE += MINE_CELL_SCORE_TWO
                }
                currentButton.score==MINE_CELL_SCORE_THREE -> {
                    currentButton.setBackgroundResource()
                    CURRENT_SCORE += MINE_CELL_SCORE_THREE
                }
                currentButton.score==MINE_CELL_SCORE_FOUR -> {
                    currentButton.setBackgroundResource()
                    CURRENT_SCORE += MINE_CELL_SCORE_FOUR
                }
                currentButton.score==MINE_CELL_SCORE_FIVE -> {
                    currentButton.setBackgroundResource()
                    CURRENT_SCORE += MINE_CELL_SCORE_FIVE
                }
                currentButton.score==MINE_CELL_SCORE_SIX -> {
                    currentButton.setBackgroundResource()
                    CURRENT_SCORE += MINE_CELL_SCORE_SIX
                }
                currentButton.score==MINE_CELL_SCORE_SEVEN -> {
                    currentButton.setBackgroundResource()
                    CURRENT_SCORE += MINE_CELL_SCORE_SEVEN
                }
                currentButton.score==MINE_CELL_SCORE_EIGHT -> {
                    currentButton.setBackgroundResource()
                    CURRENT_SCORE += MINE_CELL_SCORE_EIGHT
                }
            }
            currentButton.markVisited()
        }else if(currentButton.score==BLANK_CELL){
            currentButton.markVisited()
            var i=0
            while (i<X_COORDINATE_ARRAY.size){
                val rowId=currentButton.rowId+X_COORDINATE_ARRAY[i]
                val colId=currentButton.colId+Y_COORDINATE_ARRAY[i]
                if(rowId<0 || rowId>=NUMBER_OF_ROWS || colId<0 || colId>=NUMBER_OF_ROWS){
                    i += 1
                    continue
                }else{
                    onClick(gameCellButtons[rowId][colId])
                }
            }
        }
        if(checkWin()){
            onWin()
            onGameOver()
        }
    }
    override fun onLongClick(p0: View?): Boolean {
        if(GAME_OVER_CHECKER){
            return true
        }
        val currentButton:MinesweeperButton = p0 as MinesweeperButton
        if(currentButton.checkFlagged()){
            currentButton.isFlagged=false
            currentButton.setBackgroundResource(R.drawable.unclicked_button)
            NUMBER_OF_FLAGS += 1
            if(currentButton.checkMine()){
                NUMBER_OF_CORRECT_FLAGS-=1
            }
        }else{
            currentButton.isFlagged=true
            currentButton.setBackgroundResource(R.drawable.flag)
            NUMBER_OF_FLAGS -=1
            if(currentButton.checkMine()){
                NUMBER_OF_CORRECT_FLAGS+=1
            }
        }
        if(checkWin()){
            onWin()
            onGameOver()
        }
        return true
    }
    private fun checkWin():Boolean{
        if(NUMBER_OF_CORRECT_FLAGS==NUMBER_OF_MINES){
            return true
        }
        var i=0
        var j=0
        var checkerScore=0
        while (i<NUMBER_OF_ROWS){
            while (j<NUMBER_OF_ROWS){
                val currentButton=gameCellButtons[i][j]
                if(currentButton.score>=0){
                    checkerScore+=currentButton.score
                }
                j+=1
            }
            i+=1
        }
        if(checkerScore==CURRENT_SCORE){
            return true
        }
        return false
    }
    private fun onWin(){
        var i=0
        var j=0
        while (i<NUMBER_OF_ROWS) {
            while (j < NUMBER_OF_ROWS) {
                val currentButton = gameCellButtons[i][j]
                if (currentButton.checkMine()) {
                    if (currentButton.checkFlagged()) {
                        j += 1
                        continue
                    } else {
                        currentButton.setBackgroundResource(R.drawable.unclicked_mine)
                    }
                } else if (currentButton.checkFlagged()) {
                    if (currentButton.checkMine()) {
                        currentButton.setBackgroundResource(R.drawable.flag)
                    }
                } else {
                    when {
                        currentButton.score == MINE_CELL_SCORE_ONE -> {
                            currentButton.setBackgroundResource()
                        }
                        currentButton.score == MINE_CELL_SCORE_TWO -> {
                            currentButton.setBackgroundResource()
                        }
                        currentButton.score == MINE_CELL_SCORE_THREE -> {
                            currentButton.setBackgroundResource()
                        }
                        currentButton.score == MINE_CELL_SCORE_FOUR -> {
                            currentButton.setBackgroundResource()
                        }
                        currentButton.score == MINE_CELL_SCORE_FIVE -> {
                            currentButton.setBackgroundResource()
                        }
                        currentButton.score == MINE_CELL_SCORE_SIX -> {
                            currentButton.setBackgroundResource()

                        }
                        currentButton.score == MINE_CELL_SCORE_SEVEN -> {
                            currentButton.setBackgroundResource()
                        }
                        currentButton.score == MINE_CELL_SCORE_EIGHT -> {
                            currentButton.setBackgroundResource()
                        }
                    }
                    j += 1
                }
                i += 1
            }
        }

    }
}
