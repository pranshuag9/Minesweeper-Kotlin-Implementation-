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
        return isMine==true and (score == -1)
    }
    fun setAsMine(){
        score=-1
    }
    fun markVisted(){
        isVisited=true
    }
    fun checkVisited():Boolean{
        return isVisited
    }

}