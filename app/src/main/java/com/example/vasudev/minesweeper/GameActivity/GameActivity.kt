package com.example.vasudev.minesweeper.GameActivity

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import com.example.vasudev.minesweeper.R
import kotlinx.android.synthetic.main.activity_game.*
import java.util.*

class GameActivity : AppCompatActivity() {

    //constant for number of rows in the game
    private var NUMBER_OF_ROWS = 13
    private var NUMBER_OF_MINES = 26

    //index arrays for surrounding cells
    private val ROW_INDEXES = intArrayOf(-1, 0, 1, -1, 1, -1, 0, 1)
    private val COL_INDEXES = intArrayOf(1, 1, 1, 0, 0, -1, -1, -1)

    //arrayList of row layouts
    val rowLayoutList = ArrayList<LinearLayout>()

    //score variables for the game
    private var CURRENT_USER_SCORE=0
    private var GAME_MAX_SCORE=0
    private var NUMBER_OF_FLAGS_USED=0
    private var CHRONOMETER_TIME_RECORD_CHECKER=false


    private fun getCellAtLocation(rowIndex: Int, colIndex: Int, rowLayoutList: ArrayList<LinearLayout>): Cell {
        //access current row layout from rowIndex
        val currentRowLayout = rowLayoutList[rowIndex]
        //access cell from current row layout as child hierarchy
        return currentRowLayout.getChildAt(colIndex) as Cell
    }



    private fun rowLayout(context: Context, rowIndex: Int): LinearLayout {

        //creating a single row layout
        val rowLinearLayout = LinearLayout(context)

        //setting orientation as Horizontal as cells would be added horizontally
        rowLinearLayout.orientation = LinearLayout.HORIZONTAL
        for (i in 0 until NUMBER_OF_ROWS) {

            //creating single user-defined cell (extends Button)
            val cell = Cell(context)

            //setting view properties of cell like size, margin, background color
            val cellParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f)
            cellParams.setMargins(1, 0, 1, 0)
            cell.background = ContextCompat.getDrawable(context, R.drawable.unclicked_button)
            cell.layoutParams = cellParams

            //Assigning col Index
            val colIndex = i

            //setting initial cell properties
            cell.rowIndex = rowIndex
            cell.colIndex = colIndex
            cell.isFlagged = false
            cell.isFlagged = false
            cell.scoreValue = 0

            //cell onLongClickListener
            cell.setOnLongClickListener {

                if(!CHRONOMETER_TIME_RECORD_CHECKER){
                    timeKeeperChronometer.start()
                    CHRONOMETER_TIME_RECORD_CHECKER=true
                }

                if(cell.isVisited){
                    //no need to anything as nothing is required
                }

                if(cell.isFlagged){
                    cell.isFlagged=false
                    cell.background=ContextCompat.getDrawable(context,R.drawable.unclicked_button)
                    NUMBER_OF_FLAGS_USED -= 1
                }else{
                    cell.isFlagged=true
                    cell.background=ContextCompat.getDrawable(context,R.drawable.flag)
                    NUMBER_OF_FLAGS_USED += 1
                }

                true }

            //cell onClickListener
            cell.setOnClickListener {

                //start recording time
                if(!CHRONOMETER_TIME_RECORD_CHECKER){
                    timeKeeperChronometer.start()
                    CHRONOMETER_TIME_RECORD_CHECKER=true
                }

                //check cell is flagged or visited
                if(cell.isFlagged or cell.isVisited){
                    //no need to anything as nothing is required
                }

                //check if cell is mine
                else if (cell.isMine){

                    //game over,
                    cell.background=ContextCompat.getDrawable(context,R.drawable.clicked_mine)

                    //mark cell as visited
                    cell.isVisited=true

                    //perform tasks when user losses
                    onLoss(cell.rowIndex,cell.colIndex)
                }

                else{

                    //mark cell as visited
                    cell.isVisited=true

                    when {
                        cell.scoreValue==0 -> {

                            //found score 0 cell
                            cell.setBackgroundColor(Color.LTGRAY)

                            //call recursive function on surrounding cells
                            scoreZeroCellClicked(cell.rowIndex,cell.colIndex)
                        }
                        cell.scoreValue==1 -> {

                            //found score 1 cell
                            cell.background=ContextCompat.getDrawable(context,R.drawable.score_one_resource)
                        }
                        cell.scoreValue==2 -> {

                            //found score 2 cell
                            cell.background=ContextCompat.getDrawable(context,R.drawable.score_two_resource)
                        }
                        cell.scoreValue==3 -> {

                            //found score 3 cell
                            cell.background=ContextCompat.getDrawable(context,R.drawable.score_three_resource)
                        }
                        cell.scoreValue==4 -> {

                            //found score 4 cell
                            cell.background=ContextCompat.getDrawable(context,R.drawable.score_four_resource)
                        }
                        cell.scoreValue==5 -> {

                            //found score 5 cell
                            cell.background=ContextCompat.getDrawable(context,R.drawable.score_five_resource)
                        }
                        cell.scoreValue==6 -> {

                            //found score 6 cell
                            cell.background=ContextCompat.getDrawable(context,R.drawable.score_six_resource)
                        }
                        cell.scoreValue==7 -> {

                            //found score 7 cell
                            cell.background=ContextCompat.getDrawable(context,R.drawable.score_seven_resource)
                        }
                        cell.scoreValue==8 -> {

                            //found score 8 cell
                            cell.background=ContextCompat.getDrawable(context,R.drawable.score_eight_resource)
                        }
                    }

                    //update player score
                    CURRENT_USER_SCORE += cell.scoreValue

                    //check if user has won
                    checkWin()
                }

            }

            //adding to cell to single row layout
            rowLinearLayout.addView(cell)

        }

        //setting view properties of cell like size and margin
        val rowParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f)
        rowParams.setMargins(1, 1, 1, 1)
        rowLinearLayout.layoutParams = rowParams

        return rowLinearLayout
    }

    private fun handleGameOver(){
        timeKeeperChronometer.stop()
        for (i in 0 until NUMBER_OF_ROWS){
            for (j in 0 until NUMBER_OF_ROWS){
                val currentCell=getCellAtLocation(i,j,rowLayoutList)
                currentCell.isClickable=false
                if(!currentCell.isFlagged and currentCell.isMine){
                    currentCell.background=ContextCompat.getDrawable(this@GameActivity,R.drawable.unclicked_mine)
                }
                if(!currentCell.isMine and currentCell.isFlagged){
                    currentCell.background=ContextCompat.getDrawable(this@GameActivity,R.drawable.incorrect_flag)
                }
            }
        }
    }

    private fun checkWin() {

        if(CURRENT_USER_SCORE==GAME_MAX_SCORE){
            handleGameOver()
            Toast.makeText(this@GameActivity,"You won",Toast.LENGTH_SHORT).show()
        }
    }

    private fun scoreZeroCellClicked(rowIndex: Int, colIndex: Int) {

        for (i in 0 until ROW_INDEXES.size) {

            //set row and col coordinates for surrounding cell
            val currentRow = rowIndex + ROW_INDEXES[i]
            val currentCol = colIndex + COL_INDEXES[i]

            //check for invalid indexing
            if (checkForInvalidIndex(currentRow, currentCol)) {
                continue
            }

            val currentCell = getCellAtLocation(currentRow, currentCol, rowLayoutList)

            //check if current cell is a mine or not
            if (currentCell.isMine) {
                continue
            }

            //check if current cell is already clicked or not
            if (currentCell.isVisited) {
                continue
            }

            //perform onClick on concerned button
            currentCell.callOnClick()

        }
    }

    private fun onLoss(rowIndex: Int, colIndex: Int) {
        handleGameOver()
        val clickedMineCell=getCellAtLocation(rowIndex,colIndex,rowLayoutList)
        clickedMineCell.background=ContextCompat.getDrawable(this@GameActivity,R.drawable.clicked_mine)

        Handler().postDelayed({

            //build loss dialog
            val lossDialogBuilder=AlertDialog.Builder(this@GameActivity)

            //attach view xml to dialog
            val dialogView=layoutInflater.inflate(R.layout.loss_dialog,null)
            lossDialogBuilder.setView(dialogView)
            val lossDialog=lossDialogBuilder.create()

            //play animation
            val lossDialogAnimationView=dialogView.findViewById<LottieAnimationView>(R.id.lossAnimationView)
            lossDialogAnimationView.playAnimation()

            //handle clicks on option buttons
            val lossDialogPlayAgainButton=dialogView.findViewById<Button>(R.id.lossPlayAgainButton)
            lossDialogPlayAgainButton.setOnClickListener {

                lossDialog.dismiss()
                resetGameField() }

            val lossDialogQuitButton=dialogView.findViewById<Button>(R.id.lossQuitButton)
            lossDialogQuitButton.setOnClickListener {
                lossDialog.dismiss()
                finish()
            }

            //show dialog on screen
            lossDialog.show()

        },1000)

    }

    private fun setMines(rowLayoutList: ArrayList<LinearLayout>) {

        //inbuilt class object for generating random values
        val random = Random()

        //mine counter for number of mines set presently
        var mineCount = 0

        while (mineCount < NUMBER_OF_MINES) {

            //fetch random values between 0 and NUMBER_OF_ROWS
            val mineRow = random.nextInt(NUMBER_OF_ROWS)
            val mineCol = random.nextInt(NUMBER_OF_ROWS)

            //fetch current cell
            val currentCell = getCellAtLocation(mineRow, mineCol, rowLayoutList)

            //check if current cell is a mine or not
            if (currentCell.isMine) {
                continue
            }

            //set current cell as mine with score=-1 as distinguishing point
            currentCell.scoreValue = -1

            //set scores of surrounding cells
            setScores(rowLayoutList, mineRow, mineCol)

            //increment mine counter
            mineCount += 1
        }
    }

    private fun setScores(rowLayoutList: ArrayList<LinearLayout>, mineRow: Int, mineCol: Int) {
        for (i in 0 until ROW_INDEXES.size) {

            //set row and col coordinates for surrounding cell
            val currentRow = mineRow + ROW_INDEXES[i]
            val currentCol = mineCol + COL_INDEXES[i]

            //check for invalid indexing
            if (checkForInvalidIndex(currentRow, currentCol)) {
                continue
            }

            val currentCell = getCellAtLocation(currentRow, currentCol, rowLayoutList)

            //check if current cell is a mine or not
            if (currentCell.isMine) {
                continue
            }

            //assign score value to cell
            currentCell.scoreValue = currentCell.scoreValue + 1
            GAME_MAX_SCORE=GAME_MAX_SCORE+1
        }
    }

    //function for checking index validity
    private fun checkForInvalidIndex(currentRow: Int, currentCol: Int): Boolean =
            ((currentRow < 0) or (currentRow >= NUMBER_OF_ROWS) or (currentCol < 0) or (currentCol >= NUMBER_OF_ROWS))


    private fun initializeGameField(){

        gameContainerLayout.removeAllViews()
        //initialize scores and time
        GAME_MAX_SCORE=0
        CURRENT_USER_SCORE=0
        NUMBER_OF_FLAGS_USED=0
        timeKeeperChronometer.base= SystemClock.elapsedRealtime()
        CHRONOMETER_TIME_RECORD_CHECKER=false


        //creating row layouts with cells placed inside each one of them horizontally
        for (i in 0 until NUMBER_OF_ROWS) {

            //creating a single row with NUMBER OF CELLS=NUMBER OF ROWS
            val rowLinearLayout = rowLayout(this@GameActivity, i)

            //adding row layout list
            rowLayoutList.add(rowLinearLayout)

            //adding row to main game container layout
            gameContainerLayout.addView(rowLinearLayout)
        }

        //setting mines in the field
        setMines(rowLayoutList)
    }

    private fun resetGameField(){

        //reset properties of cell as initial ones instead of making new cells
        for (i in 0 until NUMBER_OF_ROWS){
            for (j in 0 until NUMBER_OF_ROWS){
                val currentCell=getCellAtLocation(i,j,rowLayoutList)
                currentCell.background=ContextCompat.getDrawable(this@GameActivity,R.drawable.unclicked_button)
                currentCell.isClickable=true
                currentCell.rowIndex=i
                currentCell.colIndex=j
                currentCell.scoreValue=0
                currentCell.isVisited=false
                currentCell.isFlagged=false
            }
        }

        //initialize scores and time
        GAME_MAX_SCORE=0
        CURRENT_USER_SCORE=0
        NUMBER_OF_FLAGS_USED=0
        timeKeeperChronometer.base=SystemClock.elapsedRealtime()
        CHRONOMETER_TIME_RECORD_CHECKER=false

        //set mines again
        setMines(rowLayoutList)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        //Setting Background color programmatically for container linear Layouts
        scoreContainerLayout.setBackgroundColor(Color.LTGRAY)

        //enabling animations
        timeAnimationView.playAnimation()
        emojiAnimationView.playAnimation()

        //setup game field
        initializeGameField()

        //handle mid game restart
        emojiAnimationView.setOnClickListener {
            handleGameOver()
            resetGameField()
        }
    }
}