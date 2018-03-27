package com.example.vasudev.minesweeper.ScoreKeeping

import com.google.firebase.firestore.DocumentSnapshot
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

    fun fetchScoreFromDatabase(){
        val firestore=FirebaseFirestore.getInstance()
        val scores=firestore.collection("scores")
                .get()
                .addOnCompleteListener { p0 ->
                    for(document:DocumentSnapshot in p0.result){
                        if(document.exists()){
                            document.getString("name")
                            document.get("score")
                        }
                    }
                }
    }

}