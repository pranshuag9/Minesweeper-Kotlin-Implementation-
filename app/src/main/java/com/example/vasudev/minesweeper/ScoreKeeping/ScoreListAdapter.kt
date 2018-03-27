package com.example.vasudev.minesweeper.ScoreKeeping

import android.content.Context
import android.support.v7.view.menu.MenuView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vasudev.minesweeper.R
import kotlinx.android.synthetic.main.score_list_item.view.*

/**
 * Created by Vasudev on 3/27/2018.
 */
class ScoreListAdapter(var context: Context,var scoreList:ArrayList<ScoreRenderModel>):RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.score_list_item,parent,false)
        return ScoreViewHolder(view)
    }

    override fun getItemCount(): Int = scoreList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as ScoreViewHolder).bindScoreInListItme(context,scoreList[position])
    }

}
class ScoreViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
    fun bindScoreInListItme(context: Context,scoreRenderModel: ScoreRenderModel){
        itemView.scoreNameTextView.text=scoreRenderModel.name
        itemView.scoreTimeTextView.text=scoreRenderModel.time
    }
}