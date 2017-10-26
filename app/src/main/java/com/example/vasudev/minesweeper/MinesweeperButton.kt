package com.example.vasudev.minesweeper

import android.content.Context
import android.widget.Button

/**
 * Created by Vasudev on 10/23/2017.
 */
class MinesweeperButton:Button {
    var rowId:Int =-1
    var colId:Int =-1
    var score:Int=0
    var isFlagged:Boolean=false
    var isMine:Boolean=false
    var isVisited:Boolean=false

    constructor(context: Context) : super(context)

    fun checkMine():Boolean{
        return (this.isMine==true) && (this.score == -1)
    }
    fun setAsMine(){
        this.score=-1
        this.isMine=true
    }
    fun markVisited(){
        this.isVisited=true
    }
    fun checkVisited():Boolean{
        return this.isVisited
    }
    fun checkFlagged():Boolean{
        return this.isFlagged
    }

}