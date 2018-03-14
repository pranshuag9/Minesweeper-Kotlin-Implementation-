package com.example.vasudev.minesweeper.GameActivity
import android.content.Context
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.example.vasudev.minesweeper.R
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity() {

    // Constant for number of rows in the game
    var NUMBER_OF_ROWS=13


    private fun rowLayout(context: Context):LinearLayout{

        // creating a single row layout
        val rowLinearLayout=LinearLayout(context)

        //setting orientation as Horizontal as cells would be added horizontally
        rowLinearLayout.orientation=LinearLayout.HORIZONTAL
        for (i in 1..NUMBER_OF_ROWS){

            //creating single user-defined cell (extends Button)
            val cell=Cell(context)

            //setting view properties of cell like margin, background color
            val cellParams=LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.MATCH_PARENT,1f)
            cellParams.setMargins(1,0,1,0)
            cell.background=ContextCompat.getDrawable(context,R.drawable.unclicked_button)
            cell.layoutParams=cellParams

            //cell onClickListener
            cell.setOnClickListener { Toast.makeText(context,"Clicked",Toast.LENGTH_SHORT).show() }

            // adding to cell to single row layout
            rowLinearLayout.addView(cell)

        }

        val rowParams=LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,1f)
        rowParams.setMargins(1,1,1,1)
        rowLinearLayout.layoutParams=rowParams

        return rowLinearLayout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)


        //Setting Background color programmatically for container linear Layouts
        scoreContainerLayout.setBackgroundColor(Color.LTGRAY)

        timeAnimationView.playAnimation()
        //scoreAnimationView.playAnimation()
        emojiAnimationView.playAnimation()


        // ArrayList of row layouts
        var rowLayoutList=ArrayList<LinearLayout>()

        //creating row layouts with cells placed inside each one of them horizontally

        for (i in 1..NUMBER_OF_ROWS){

            //creating a single row with NUMBER OF CELLS=NUMBER OF ROWS
            var rowLinearLayout=rowLayout(this@GameActivity)

            //adding row layout list
            rowLayoutList.add(rowLinearLayout)

            //adding row to main game container layout
            gameContainerLayout.addView(rowLinearLayout)
        }


    }
}
