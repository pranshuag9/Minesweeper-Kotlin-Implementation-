package com.example.vasudev.minesweeper.GameActivity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.example.vasudev.minesweeper.R
import kotlinx.android.synthetic.main.activity_game.*
import java.util.*

class GameActivity : AppCompatActivity() {

    // Constant for number of rows in the game
    private var NUMBER_OF_ROWS=13
    private var NUMBER_OF_MINES=26

    private val ROW_INDEXES= intArrayOf(-1,0,1,-1,1,-1,0,1)
    private val COL_INDEXES= intArrayOf(1,1,1,0,0,-1,-1,-1)


    private fun getCellAtLocation(rowIndex: Int,colIndex:Int,rowLayoutList: ArrayList<LinearLayout>):Cell{
        //access current row layout from rowIndex
        val currentRowLayout=rowLayoutList[rowIndex]
        //access cell from current row layout as child hierarchy
        return currentRowLayout.getChildAt(colIndex) as Cell
    }

    private fun rowLayout(context: Context, rowIndex: Int):LinearLayout{

        // creating a single row layout
        val rowLinearLayout=LinearLayout(context)

        //setting orientation as Horizontal as cells would be added horizontally
        rowLinearLayout.orientation=LinearLayout.HORIZONTAL
        for (i in 0 until NUMBER_OF_ROWS){

            //creating single user-defined cell (extends Button)
            val cell=Cell(context)

            //setting view properties of cell like size, margin, background color
            val cellParams=LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.MATCH_PARENT,1f)
            cellParams.setMargins(1,0,1,0)
            cell.background=ContextCompat.getDrawable(context,R.drawable.unclicked_button)
            cell.layoutParams=cellParams

            //Assigning col Index
            val colIndex=i

            //setting initial cell properties
            cell.rowIndex=rowIndex
            cell.colIndex=colIndex
            cell.isFlagged=false
            cell.isFlagged=false
            cell.scoreValue=0

            //cell onClickListener
            cell.setOnClickListener { Toast.makeText(context,cell.scoreValue.toString(),Toast.LENGTH_SHORT).show() }

            // adding to cell to single row layout
            rowLinearLayout.addView(cell)

        }

        //setting view properties of cell like size and margin
        val rowParams=LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,1f)
        rowParams.setMargins(1,1,1,1)
        rowLinearLayout.layoutParams=rowParams

        return rowLinearLayout
    }

    private fun setMines(rowLayoutList:ArrayList<LinearLayout>){

        //inbuilt class object for generating random values
        val random=Random()

        //mine counter for number of mines set presently
        var mineCount=0

        while (mineCount<NUMBER_OF_MINES){

            //fetch random values between 0 and NUMBER_OF_ROWS
            val mineRow=random.nextInt(NUMBER_OF_ROWS)
            val mineCol=random.nextInt(NUMBER_OF_ROWS)

            //fetch current cell
            val currentCell=getCellAtLocation(mineRow,mineCol,rowLayoutList)

            //check if current cell is a mine or not
            if(currentCell.isMine){
                continue
            }

            //set current cell as mine with score=-1 as distinguishing point
            currentCell.scoreValue=-1

            //set scores of surrounding cells
            setScores(rowLayoutList,mineRow,mineCol)

            //increment mine counter
            mineCount += 1
        }
    }

    private fun setScores(rowLayoutList: ArrayList<LinearLayout>, mineRow: Int, mineCol: Int) {
        for (i in 0 until ROW_INDEXES.size){

            //set row and col coordinates for surrounding cell
            val currentRow=mineRow+ROW_INDEXES[i]
            val currentCol=mineCol+COL_INDEXES[i]

            //check for invalid indexing
            if(checkForInvalidIndex(currentRow,currentCol)){
                continue
            }

            val currentCell=getCellAtLocation(currentRow,currentCol,rowLayoutList)

            //check if current cell is a mine or not
            if(currentCell.isMine){
                continue
            }

            //assign score value to cell
            currentCell.scoreValue=currentCell.scoreValue+1

        }
    }

    //function for
    private fun checkForInvalidIndex(currentRow: Int, currentCol: Int): Boolean =
            ((currentRow<0) or (currentRow>=NUMBER_OF_ROWS) or (currentCol<0) or (currentCol>=NUMBER_OF_ROWS))


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        //Setting Background color programmatically for container linear Layouts
        scoreContainerLayout.setBackgroundColor(Color.LTGRAY)

        //enabling animations
        timeAnimationView.playAnimation()
        emojiAnimationView.playAnimation()

        // ArrayList of row layouts
        val rowLayoutList=ArrayList<LinearLayout>()

        //creating row layouts with cells placed inside each one of them horizontally
        for (i in 0 until NUMBER_OF_ROWS){

            //creating a single row with NUMBER OF CELLS=NUMBER OF ROWS
            val rowLinearLayout=rowLayout(this@GameActivity,i)

            //adding row layout list
            rowLayoutList.add(rowLinearLayout)

            //adding row to main game container layout
            gameContainerLayout.addView(rowLinearLayout)
        }

        //setting mines in the field
        setMines(rowLayoutList)
    }
}
