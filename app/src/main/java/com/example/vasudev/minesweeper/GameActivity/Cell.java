package com.example.vasudev.minesweeper.GameActivity;

import android.content.Context;
import android.widget.Button;

/**
 * Created by Vasudev on 3/14/2018.
 */

public class Cell extends android.support.v7.widget.AppCompatButton {

    private int rowIndex;
    private int colIndex;
    private int scoreValue;
    private boolean isFlagged;
    private boolean isVisited;

    public Cell(Context context) {
        super(context);
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getColIndex() {
        return colIndex;
    }

    public void setColIndex(int colIndex) {
        this.colIndex = colIndex;
    }

    public int getScoreValue() {
        return scoreValue;
    }

    public void setScoreValue(int scoreValue) {
        this.scoreValue = scoreValue;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }
}
