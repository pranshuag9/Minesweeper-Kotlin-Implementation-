package com.example.vasudev.minesweeper.ScoreKeeping

import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Created by Vasudev on 3/27/2018.
 */
class ScoreTransitions {

    fun addScoreToDatabase(name:String,score:String){
        val firestore=FirebaseFirestore.getInstance()
        val scoreMap=HashMap<String,String>()
        scoreMap.put("name",name)
        scoreMap.put("score",score)
        firestore.collection("scores").add(scoreMap as Map<String, String>).addOnCompleteListener{
            if (it.isComplete){
                if(it.isSuccessful){

                }else{

                }
            }
        }

    }
}